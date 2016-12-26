package com.ifeng.pgc.beans;

import com.ifeng.pgc.annotations.JsonProperties;
import com.ifeng.pgc.core.mongo.DataLoader;
import com.ifeng.pgc.core.mongo.EntryCodec;

/**
 * Created by zhanglr on 2016/9/2.
 */
public class EIPInfo extends EntryCodec {
    private String requestId ;
    private String hostIp;
    @JsonProperties(Name = "PublicIp")
    private String publicIp;
    @JsonProperties(Name = "Version")
    private String version;
    @JsonProperties(Name = "LineId")
    private String lineId;
    @JsonProperties(Name = "LineName")
    private String lineName;
    private int bandWidth;
    private String chargeType;

    /**
     * 弹性IP的ID
     */
    @JsonProperties(Name = "AllocationId")
    private String allocationId;
    /**
     * Ipfwd为主机，Slb为负载均衡
     */
    @JsonProperties(Name = "InstanceType")
    private String instanceType;

    /**
     * 绑定的IP映射的信息。绑定类型为Ipfwd时，InstanceId为主机的ID。绑定类型为Slb时，InstanceId为负载均衡的ID
     */
    @JsonProperties(Name = "InstanceId")
    private String instanceId;

    /**
     * 网络接口的标识，当InstanceType为Ipfwd时，不可缺省。当InstanceType为Slb时，可缺省。
     */
    @JsonProperties(Name = "NetworkInterfaceId")
    private String networkInterfaceId;

    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public int getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(int bandWidth) {
        this.bandWidth = bandWidth;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(String allocationId) {
        this.allocationId = allocationId;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getNetworkInterfaceId() {
        return networkInterfaceId;
    }

    public void setNetworkInterfaceId(String networkInterfaceId) {
        this.networkInterfaceId = networkInterfaceId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public void decode(DataLoader loader) {
        this.allocationId = loader.getString("allocationId");
        this.bandWidth = loader.getInt("bandWidth");
        this.chargeType = loader.getString("chargeType");
        this.publicIp = loader.getString("publicIp");
        this.hostIp = loader.getString("hostIp");
        this.instanceId = loader.getString("instanceId");
        this.instanceType = loader.getString("instanceType");
        this.lineId = loader.getString("lineId");
        this.networkInterfaceId = loader.getString("networkInterfaceId");
        this.version = loader.getString("version");
        this.lineName = loader.getString("lineName");
        this.requestId = loader.getString("requestId");
    }

    @Override
    public String toString(){
        return new StringBuilder().append("allocationId:").append(allocationId)
                .append("bandWidth: ").append(bandWidth)
                .append("chargeType: ").append(chargeType)
                .append("publicIp: ").append(publicIp)
                .append("hostIp: ").append(hostIp)
                .append("instanceId: ").append(instanceId)
                .append("instanceType: ").append(instanceType)
                .append("lineId: ").append(lineId)
                .append("networkInterfaceId: ").append(networkInterfaceId)
                .append("lineName: ").append(lineName).toString();
    }
}
