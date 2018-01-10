/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ChannelAllocator.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description: 通道建立接口
 */
package com.chezhibao.dubbo.intf;

import com.chezhibao.dubbo.entity.Channel;

/**
 * 通道建立接口
 *
 * @author Arshle
 */
public interface ChannelAllocator {
    /**
     * 建立通道
     * 不可返回null
     * @return 通道
     */
    Channel allocator(String cname,String token);
}
