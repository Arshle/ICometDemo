/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: NoneAuthChannelAllocator.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description: 无token通道建立类
 */
package com.chezhibao.dubbo.service;

import com.chezhibao.dubbo.entity.Channel;
import com.chezhibao.dubbo.intf.ChannelAllocator;

/**
 * 无token通道建立类
 *
 * @author Arshle
 */
public class NoneAuthChannelAllocator implements ChannelAllocator {
    /**
     * 建立通道连接
     * @return 通道
     */
    @Override
    public Channel allocator(String cname,String token) {
        Channel channel = new Channel();
        channel.setCname(cname);
        channel.setToken(token);
        channel.setSeq(0);
        return channel;
    }
}
