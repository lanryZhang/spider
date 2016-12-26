package com.ifeng.pgc.quartzJobs.eipjobs;

import com.ifeng.pgc.beans.EIPInfo;
import com.ifeng.pgc.core.BeanLoader;
import com.ifeng.pgc.core.Context;
import com.ifeng.pgc.core.monitor.spider.SpiderMonitor;
import com.ifeng.pgc.services.EIPService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by zhanglr on 2016/9/5.
 */
public class EIPProcessJob {
    private Logger log = Logger.getLogger(SpiderMonitor.class);
    public void execute() throws Exception {
        try {
            EIPService eipService = BeanLoader.getBean("eipServiceImpl");

            Context context = new Context();
            List<String> list = eipService.getAllHostIp();

            list.forEach(r->{
                context.put("hostIp",r);
                eipService.execute(context);
            });

        }catch (Exception e){
            log.error(e);
        }
    }
}
