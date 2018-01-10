/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: State.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description: 消息状态
 */
package com.chezhibao.dubbo.constants;

/**
 * 消息状态
 *
 * @author Arshle
 */
public class State {
    /**
     * 刚建立连接
     */
    public static final int STATE_NEW = 0;
    /**
     * 准备好连接
     */
    public static final int STATE_READY = 1;
    /**
     * 已建立连接
     */
    public static final int STATE_CONNCTED = 2;
    /**
     * 正在接受或发送消息
     */
    public static final int STATE_COMET = 3;
    /**
     * 手动停止连接
     */
    public static final int STATE_STOP = 4;
    /**
     * 停止连接
     */
    public static final int STATE_STOP_PENDING = 5;
    /**
     * 异常停止连接
     */
    public static final int STATE_DISCONNECT = 6;
}
