/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Type.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description: 消息类型
 */
package com.chezhibao.dubbo.constants;

/**
 * 消息类型
 *
 * @author Arshle
 */
public class Type {
    /**
     * 数据
     */
    public static final String TYPE_DATA = "data";
    /**
     * Noop
     */
    public static final String TYPE_NOOP = "noop";
    /**
     * 通道或订阅者过多
     */
    public static final String TYPE_429 = "429";
    /**
     * token错误
     */
    public static final String TYPE_401 = "401";
}
