package com.ifeng.pgc.services.impl;

import com.ifeng.pgc.beans.EIPInfo;
import com.ifeng.pgc.core.BeanLoader;
import com.ifeng.pgc.core.Context;
import com.ifeng.pgc.dao.EIPDao;
import com.ifeng.pgc.services.EIPService;
import com.ifeng.pgc.utils.CommonUtil;
import com.ifeng.pgc.utils.Commons;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import com.ksc.iam.util.AwsSignerV4Util;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URI;
import java.util.*;

import static com.ifeng.pgc.utils.Commons.getQueryString;

/**
 * Created by zhanglr on 2016/9/5.
 */
@Service(value = "eipServiceImpl")
public class EIPServiceImpl implements EIPService {
    private static final String service = "eip";
    private static final String region = "cn-beijing-6";
    private static URI uri;

    private static String ak = "AKLT9i-B3nc0S-e6fT7-5rznPQ";//AK
    private static String sk = "OC3qHdL07+PPpNYrIfp6JK0ou0D/tsgL4Tw+ScV7oFgPLlGw2YH5Y2XmVXDQgCHy0Q==";//SK

    private Logger log = Logger.getLogger(EIPServiceImpl.class);
    @Autowired
    private EIPDao eipDao;
    public EIPServiceImpl(){
        RestAssured.baseURI = "http://eip.cn-beijing-6.api.ksyun.com";
        uri = URI.create(RestAssured.baseURI);
    }
    private String buidRequest(String action,Map<String,String> param) {
        try {
            Map<String, List<String>> parameters = new HashMap<>();
            parameters.put("Action", Arrays.asList(action));
            parameters.put("Version", Arrays.asList(Commons.API_VERSION));

            if (param != null) {
                for (Map.Entry<String, String> item : param.entrySet()) {
                    parameters.put(item.getKey(), Arrays.asList(item.getValue()));
                }
            }
            List<com.ksc.iam.util.Header> headers2 = AwsSignerV4Util.getAuthHeaderForGet(uri, parameters, null, service, region, ak, sk);
            List<Header> headers = CommonUtil.transform(headers2);
            Response rs = RestAssured.given().//
                    contentType(ContentType.JSON).//
                    headers(new Headers(headers)).//
                    when().//
                    get("/?" + getQueryString(parameters));

            return rs.getBody().asString();

        }catch (Exception e){
            return null;
        }
    }

    private Map<String,Object> getLines() {
        Map<String,Object> res = new HashedMap();
        try {
            String str = buidRequest("GetLines", null);

            Document doc = DocumentHelper.parseText(str);

            List<Element> list = doc.getRootElement().element("LineSet").elements();
            String requestId = doc.getRootElement().elementText("RequestId");
            res.put("RequestId",requestId);
            if (list != null){
                for (int i = 0 ;i < list.size();i++){
                    Element el = list.get(i);
                    String name = el.elementText("LineName");
                    String lineId = el.elementText("LineId");
                    if ("电信".equals(name)){
                        res.put("LineName",name);
                        res.put("LineId",lineId);
                        break;
                    }
                }
            }

            return res;
        } catch (Exception e) {
        }
        return null;
    }

    private Map<String,Object> allocateAddress(String lineId) {
        Map<String,Object> res = new HashedMap();
        try {
            Map<String, String> param = new HashMap<>();
            param.put("LineId", lineId);
            param.put("BandWidth", "20");
            param.put("ChargeType", "Peak");
            String str = buidRequest("AllocateAddress", param);

            Document doc = DocumentHelper.parseText(str);
            String requestId = doc.getRootElement().elementText("RequestId");
            String allocationId = doc.getRootElement().elementText("AllocationId");
            String eip = doc.getRootElement().elementText("PublicIp");

            res.put("RequestId",requestId);
            res.put("AllocationId",allocationId);
            res.put("EIP",eip);
            return res;
        }catch (Exception e){
            log.error(e);
        }
        return null;
    }

    private boolean releaseAddress(String allocationId) {
        boolean res = false;
        try {
            Map<String, String> param = new HashMap<>();
            param.put("AllocationId", allocationId);
            String str = buidRequest("ReleaseAddress", param);
            Document doc = DocumentHelper.parseText(str);
            res = Boolean.parseBoolean(doc.getRootElement().elementText("Return"));
        }catch (Exception e){
            log.error(e);
        }
        return res;
    }

    private boolean associateAddress(String allocationId, String instanceId, String networkInterfaceId) {
        boolean res = false;
        try {
            Map<String, String> param = new HashMap<>();
            param.put("AllocationId", allocationId);
            param.put("InstanceType", "Ipfwd");
            param.put("InstanceId", instanceId);
            param.put("NetworkInterfaceId", networkInterfaceId);
            String str = buidRequest("AssociateAddress", param);
            Document doc = DocumentHelper.parseText(str);
            res = Boolean.parseBoolean(doc.getRootElement().elementText("Return"));
        }catch (Exception e){
            log.error(e);
        }
        return res;
    }

    private boolean disassociateAddress(String allocationId) {
        boolean res = false;
        try {
        Map<String,String> param = new HashMap<>();
        param.put("AllocationId",allocationId);
        String str = buidRequest("DisassociateAddress",param);
        Document doc = DocumentHelper.parseText(str);
        res = Boolean.parseBoolean(doc.getRootElement().elementText("Return"));
        }catch (Exception e){
            log.error(e);
        }
        return res;
    }

    private Map<String,Object> describeAddresses(String instanceId) {
        Map<String,Object> res = new HashedMap();
        try {
            Map<String, String> param = new HashMap<>();
            param.put("Filter.1.Name", "instance-type");
            param.put("Filter.1.Value.1", "Ipfwd");
            String str = buidRequest("DescribeAddresses", param);

            Document doc = DocumentHelper.parseText(str);
            List<Element> list = doc.getRootElement().element("AddressesSet").elements();
            if (list != null) {
                list.forEach((Element el)->{
                    if (el.elementText("InstanceId").equals(instanceId)){
                        res.put("PublicIp",el.elementText("PublicIp"));
                        res.put("NetworkInterfaceId",el.elementText("NetworkInterfaceId"));
                        res.put("BandWidth",el.elementText("BandWidth"));
                        res.put("InstanceId",el.elementText("InstanceId"));
                        res.put("LineId",el.elementText("LineId"));
                        res.put("InstanceType",el.elementText("InstanceType"));
                        res.put("AllocationId",el.elementText("AllocationId"));
                        res.put("CreateTime",el.elementText("CreateTime"));
                    }
                });

            }
        }catch (Exception e){
            log.error(e);
        }
        return res;
    }


    @Override
    public void execute(Context context) {
        try {

            Object hostIpObj = context.get("hostIp");
            if (hostIpObj == null) return;

            String hostIp = hostIpObj.toString();

            Map<String, Object> res = getLines();
            EIPInfo entity = eipDao.getEIpInfo(hostIp);

            if (entity == null) return;

            log.info("old EIP entity info :" + entity.toString());

            String oldAllocationId;
            String newAllocationId = "";
            String instanceId ;
            String networkInterfaceId = "";

            String lineId = res.get("LineId").toString();
            String lineName = res.get("LineName").toString();
            if (null != lineId && !"".equals(lineId)) {
                res = allocateAddress(lineId);
                log.info("allocateAddress new :" + res.get("EIP"));
                newAllocationId = res.get("AllocationId").toString();
            }

            oldAllocationId = entity.getAllocationId();
            instanceId = entity.getInstanceId();
            networkInterfaceId = entity.getNetworkInterfaceId();
            if (!"".equals(oldAllocationId)){
                disassociateAddress(oldAllocationId);//解绑弹性IP

                if (associateAddress(newAllocationId,instanceId,networkInterfaceId)){//绑定新IP

                    releaseAddress(oldAllocationId);//删除旧EIP

                    res = describeAddresses(instanceId);//获取新EIP描述信息
                    entity = new EIPInfo();
                    entity.setLineId(lineId);
                    entity.setLineName(lineName);

                    entity.setAllocationId(newAllocationId);
                    entity.setBandWidth(Integer.valueOf(res.get("BandWidth").toString()));
                    entity.setChargeType("Peak");
                    entity.setCreateTime(res.get("CreateTime").toString());
                    entity.setHostIp(hostIp);
                    entity.setInstanceId(res.get("InstanceId").toString());
                    entity.setInstanceType(res.get("InstanceType").toString());
                    entity.setNetworkInterfaceId(res.get("NetworkInterfaceId").toString());
                    entity.setPublicIp(res.get("PublicIp").toString());
                    entity.setVersion("2016-03-04");
                    log.info(entity.toString());
                    eipDao.insert(entity);
                }
            }
        }catch (Exception e){
            log.error(e);
        }
    }

    @Override
    public EIPInfo getEIpInfo(String hostIp) throws Exception {
        return eipDao.getEIpInfo(hostIp);
    }

    @Override
    public List<String> getAllHostIp() throws Exception {
        return eipDao.getAllHostIp();
    }
}
