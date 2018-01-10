/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ICometClientImpl.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description: IComet客户端实现
 */
package com.chezhibao.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.chezhibao.dubbo.entity.Message;
import com.chezhibao.dubbo.intf.ICometClient;
import com.chezhibao.dubbo.utils.Runner;

/**
 * IComet客户端实现
 *
 * @author Arshle
 */
@Service
public class ICometClientImpl implements ICometClient {

    @Override
    public void push(Message message, String token) {
        Runner.start(message,token);
    }
}
