package com.ifeng.pgc.core.distribute.message;

import com.ifeng.pgc.beans.UrlEntity;

import java.io.Serializable;

/**
 * Created by zhanglr on 2016/8/29.
 */
public class TaskMessage implements Serializable{
    private String taskId;
    private String from;
    private UrlEntity entity;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public UrlEntity getEntity() {
        return entity;
    }

    public void setEntity(UrlEntity entity) {
        this.entity = entity;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
