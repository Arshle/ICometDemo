/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Runner.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description: 运行
 */
package com.chezhibao.dubbo.utils;

import com.chezhibao.dubbo.client.ICometClient;
import com.chezhibao.dubbo.entity.ICometConf;
import com.chezhibao.dubbo.entity.Message;
import com.chezhibao.dubbo.intf.IConnectCallBack;
import com.chezhibao.dubbo.service.MyCometCallBack;
import com.chezhibao.dubbo.service.NoneAuthChannelAllocator;

/**
 * 运行
 *
 * @author Arshle
 */
public class Runner {
    private static ICometClient client;

    public static void start(Message message,String token){
        client = ICometClient.getInstance();
        ICometConf conf = new ICometConf();
        conf.setHost("172.16.10.102");
        conf.setPort("8000");
        conf.setUrl("push");
        conf.setCname(message.getCname());
        conf.setToken(token);
        conf.setContent(message.getContent());
        conf.setChannelAllocator(new NoneAuthChannelAllocator());
        conf.setiConnectCallBack(new MyConnectCallBack());
        conf.setiCometCallBack(new MyCometCallBack());
        client.prepare(conf);
        client.connect();
    }

    public static class MyConnectCallBack implements IConnectCallBack{

        @Override
        public void onSuccess() {
            System.out.println("connection ok");
            client.comet();
        }

        @Override
        public void onFail(String msg) {
            System.out.println("connection fail");
            System.err.println(msg);
        }

        @Override
        public void onDisconnect() {
            System.err.println("connection has been cut off");
        }

        @Override
        public void onStop() {
            System.out.println("client has been stopped");
        }

        @Override
        public boolean onReconnect(int times) {
            System.err.println("This is the " + times + "st times.");
            return times >= 3;
        }

        @Override
        public void onReconnectSuccess(int times) {
            System.out.println("onReconnectSuccess at " + times + "st time");
            client.comet();
        }
    }

    public static void main(String[] args) {
        client = ICometClient.getInstance();
        ICometConf conf = new ICometConf();
        conf.setHost("172.16.10.102");
        conf.setPort("8000");
        conf.setUrl("push");
        conf.setCname("02465");
        conf.setToken("");
        conf.setContent("emerge_deal:48,total_follow:23,wait_deal:25,wait_follow:14,wait_auction:9,is_auction:33");
        conf.setChannelAllocator(new NoneAuthChannelAllocator());
        conf.setiConnectCallBack(new MyConnectCallBack());
        conf.setiCometCallBack(new MyCometCallBack());
        client.prepare(conf);
        client.connect();
    }
}
