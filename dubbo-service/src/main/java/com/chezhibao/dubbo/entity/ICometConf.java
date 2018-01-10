/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ICometConf.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description: IComet配置实体
 */
package com.chezhibao.dubbo.entity;

import com.chezhibao.dubbo.intf.ChannelAllocator;
import com.chezhibao.dubbo.intf.ICometCallBack;
import com.chezhibao.dubbo.intf.IConnectCallBack;

/**
 * IComet配置实体
 *
 * @author Arshle
 */
public class ICometConf {
    /**
     * icomet服务器IP
     */
    private String host;
    /**
     * icomet服务器端口号
     */
    private String port;
    /**
     * icomet连接名称
     */
    private String url;
    /**
     * 通道名称
     */
    private String cname;
    /**
     * token
     */
    private String token;
    /**
     * content
     */
    private String content;
    /**
     * 通道建立接口
     */
    private ChannelAllocator channelAllocator;
    /**
     * 连接回调接口
     */
    private IConnectCallBack iConnectCallBack;
    /**
     * icomet回调接口
     */
    private ICometCallBack iCometCallBack;
    /**
     * Getters、Setters
     */
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ChannelAllocator getChannelAllocator() {
        return channelAllocator;
    }

    public void setChannelAllocator(ChannelAllocator channelAllocator) {
        this.channelAllocator = channelAllocator;
    }

    public IConnectCallBack getiConnectCallBack() {
        return iConnectCallBack;
    }

    public void setiConnectCallBack(IConnectCallBack iConnectCallBack) {
        this.iConnectCallBack = iConnectCallBack;
    }

    public ICometCallBack getiCometCallBack() {
        return iCometCallBack;
    }

    public void setiCometCallBack(ICometCallBack iCometCallBack) {
        this.iCometCallBack = iCometCallBack;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
