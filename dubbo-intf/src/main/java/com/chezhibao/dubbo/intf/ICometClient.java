/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ICometClient.java
 * Author:   zhangdanji
 * Date:     2018年01月02日
 * Description: IComet客户端操作类
 */
package com.chezhibao.dubbo.intf;

import com.chezhibao.dubbo.entity.Message;

/**
 * IComet客户端操作类
 *
 * @author zhangdanji
 */
public interface ICometClient {

    void push(Message message,String token);
}
