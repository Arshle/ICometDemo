/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: MyCometCallBack.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description: 测试Icomet回调实现类
 */
package com.chezhibao.dubbo.service;

import com.chezhibao.dubbo.entity.Message;
import com.chezhibao.dubbo.intf.ICometCallBack;

/**
 * 测试Icomet回调实现类
 * @author Arshle
 */
public class MyCometCallBack implements ICometCallBack {

    @Override
    public void onDataMsgArrived(Message.Content content) {
        System.out.println("data msg arrived: " + content);
    }

    @Override
    public void onMsgArrived(Message msg) {
        System.out.println("msg arrived: " + msg);
    }

    @Override
    public void onErrorMsgArrived(Message msg) {
        System.err.println("error message arrived with type: " + msg.getType());
    }

    @Override
    public void onMsgFormatError() {
        System.err.println("message format error");
    }
}
