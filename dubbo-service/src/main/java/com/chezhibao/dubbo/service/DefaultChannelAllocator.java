/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: DefaultChannelAllocator.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description: 普通通道建立服务
 */
package com.chezhibao.dubbo.service;

import com.chezhibao.dubbo.entity.Channel;
import com.chezhibao.dubbo.intf.ChannelAllocator;

/**
 * 普通通道建立服务
 *
 * @author Arshle
 */
public class DefaultChannelAllocator implements ChannelAllocator {

    @Override
    public Channel allocator(String cname,String token) {
        Channel channel = new Channel();
        channel.setCname(cname);
        channel.setSeq(0);
        channel.setToken(token);
        return channel;
    }
}