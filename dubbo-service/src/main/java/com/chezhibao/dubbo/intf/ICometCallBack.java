/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: IcometCallBack.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description: Icomet服务器推送回调接口
 */
package com.chezhibao.dubbo.intf;

import com.chezhibao.dubbo.entity.Message;

/**
 * Icomet服务器推送回调接口
 *
 * @author Arshle
 */
public interface ICometCallBack {
    /**
     * 接受数据消息
     * @param content 数据内容
     */
    void onDataMsgArrived(Message.Content content);
    /**
     * 接受消息
     * @param msg 消息内容
     */
    void onMsgArrived(Message msg);
    /**
     * 接受错误消息
     * @param msg 消息内容
     */
    void onErrorMsgArrived(Message msg);
    /**
     * 消息format错误，无法解析json
     */
    void onMsgFormatError();
}
