/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: MessageInputStream.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description: 消息流
 */
package com.chezhibao.dubbo.entity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 消息流
 * @author Arshle
 */
public class MessageInputStream {
    /**
     * 消息流
     */
    private InputStream input;
    /**
     * 流读取类
     */
    private BufferedReader reader;

    public MessageInputStream(InputStream input){
        try {
            this.input = input;
            this.reader = new BufferedReader(new InputStreamReader(this.input,"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 读取消息内容
     * @return 消息实体
     */
    public Message readMessage(){
        Message message = null;
        try {
            Gson gson = new Gson();
            String jsonData = this.read();
            if(jsonData != null){
                message = gson.fromJson(jsonData,Message.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
    /**
     * 按行读取
     * @return 每行内容
     * @throws Exception IO异常
     */
    private String read() throws Exception{
        return this.reader.readLine();
    }
}