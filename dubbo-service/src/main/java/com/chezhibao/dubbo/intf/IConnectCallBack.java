/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: IConnectCallBack.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description: 连接建立回调接口
 */
package com.chezhibao.dubbo.intf;

/**
 * 连接建立回调接口
 *
 * @author Arshle
 */
public interface IConnectCallBack {
    /**
     * 连接Icomet成功
     */
    void onSuccess();
    /**
     * 连接Icomet失败
     * @param msg 失败信息
     */
    void onFail(String msg);
    /**
     * 断开Icomet连接
     */
    void onDisconnect();
    /**
     * 断开Icomet连接
     */
    void onStop();
    /**
     * 重新建立Icomet连接
     * @param times 重试次数
     * @return 是否成功
     */
    boolean onReconnect(int times);
    /**
     * 重连成功
     * @param times 次数
     */
    void onReconnectSuccess(int times);
}
