/*
* ArticlListJob.java 
* Created on  202016/12/8 16:39 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.pgc.quartzJobs.articlejobs;

import com.ifeng.pgc.beans.ArticlInfo;
import com.ifeng.pgc.core.AbsHtmlJob;
import com.ifeng.pgc.core.Context;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class ArticlListJob extends AbsHtmlJob {
    public ArticlListJob() {
        super(ArticlInfo.class);
    }

    @Override
    public void doExecute(Context context) throws Exception {

    }
}
