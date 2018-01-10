/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Channel.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description: 通道实体类
 */
package com.chezhibao.dubbo.entity;

/**
 * 通道实体类
 *
 * @author Arshle
 */
public class Channel {
    /**
     * 通道唯一标识名称
     */
    private String cname;
    /**
     * token名称
     */
    private String token;
    /**
     * 消息序号
     */
    private int seq;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}