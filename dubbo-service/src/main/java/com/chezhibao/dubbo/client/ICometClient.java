/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ICometClient.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description:
 */
package com.chezhibao.dubbo.client;

import com.chezhibao.dubbo.constants.State;
import com.chezhibao.dubbo.constants.Type;
import com.chezhibao.dubbo.entity.Channel;
import com.chezhibao.dubbo.entity.ICometConf;
import com.chezhibao.dubbo.entity.Message;
import com.chezhibao.dubbo.entity.MessageInputStream;
import com.chezhibao.dubbo.intf.ICometCallBack;
import com.chezhibao.dubbo.intf.IConnectCallBack;
import com.chezhibao.dubbo.service.DefaultChannelAllocator;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Arshle
 */
public class ICometClient {
    /**
     * 重新连接的间隔时间
     * 大于600秒的按照600秒处理
     * 不建议太长时间
     */
    private static final int[] DELAY = {30,120,600};
    /**
     * 最终连接Icomet的地址
     */
    private String finalUrl;
    /**
     * 连接通道
     */
    private Channel channel;
    /**
     * 客户端
     */
    private static ICometClient client = new ICometClient();
    /**
     * 建立连接回调接口
     */
    private IConnectCallBack iConnectCallBack;
    /**
     * Icomet消息推送回调接口
     */
    private ICometCallBack iCometCallBack;
    /**
     * http连接客户端
     */
    private HttpURLConnection httpConnection;
    /**
     * 消息流
     */
    private MessageInputStream messageInputStream;
    /**
     * Icomet配置文件
     */
    private ICometConf ICometConf;
    /**
     * 记录重连的次数
     */
    private int reConnectTimes = 0;
    /**
     * 当前连接状态
     */
    private int connectState = State.STATE_NEW;

    private ICometClient(){}
    /**
     * 获取客户端连接实例
     * @return 客户端
     */
    public static ICometClient getInstance(){
        if(client == null){
            client = new ICometClient();
        }
        return client;
    }
    /**
     * 准备客户端连接
     * @param conf icomet配置
     */
    public void prepare(ICometConf conf){
        //通道建立接口
        if(conf.getChannelAllocator() == null){
            conf.setChannelAllocator(new DefaultChannelAllocator());
        }
        this.ICometConf = conf;
        //若没重连过则新建通道
        if(reConnectTimes == 0){
            this.channel = conf.getChannelAllocator().allocator(conf.getCname(),conf.getToken());
        }
        this.finalUrl = buildUrl(conf.getUrl());
        System.out.println(finalUrl);
        this.iConnectCallBack = conf.getiConnectCallBack();
        this.iCometCallBack = conf.getiCometCallBack();
        this.connectState = State.STATE_READY;
    }
    /**
     * 连接Icomet服务器
     * 建议在一个子线程中调用
     */
    public void connect(){
        if(this.connectState != State.STATE_READY){
            return;
        }
        try {
            //发送http请求
            this.httpConnection = (HttpURLConnection) new URL(this.finalUrl).openConnection();
            //请求方法
            this.httpConnection.setRequestMethod("GET");
            //超时时间
            this.httpConnection.setConnectTimeout(3 * 60 * 1000);
            //是否输入流
            this.httpConnection.setDoInput(true);
            this.messageInputStream = new MessageInputStream(this.httpConnection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            if(this.httpConnection != null){
                httpConnection.disconnect();
            }
            if(this.iConnectCallBack != null){
                this.iConnectCallBack.onFail(e.getMessage());
            }
            reConnect();
            return;
        }
        //更新为已连接的状态
        this.connectState = State.STATE_CONNCTED;
        //调用连接回调接口
        if(iCometCallBack != null){
            if(reConnectTimes == 0){
                //连接成功
                iConnectCallBack.onSuccess();
            }else{
                //重试连接成功
                iConnectCallBack.onReconnectSuccess(this.reConnectTimes);
                this.reConnectTimes = 0;
            }
        }
    }
    /**
     * 开始接受和推送服务器的消息
     */
    public void comet(){
        if(this.connectState != State.STATE_CONNCTED){
            return;
        }
        this.connectState = State.STATE_COMET;
        new Thread(new CometThread()).start();
    }
    /**
     * 停止接受和推送消息
     */
    public void stopComet(){
        this.connectState = State.STATE_STOP_PENDING;
    }
    /**
     * 停止长连接
     */
    public void stopConnect(){
        if(this.httpConnection != null){
            this.httpConnection.disconnect();
            this.httpConnection = null;
        }
    }
    /**
     * 查看客户端当前状态
     * @return 连接状态
     */
    public int currentState(){
        return this.connectState;
    }
    /**
     * 重新连接
     */
    private void reConnect(){
        if(iConnectCallBack == null){
            return;
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                reConnectTimes ++;
                if(!iConnectCallBack.onReconnect(reConnectTimes)){
                    if(connectState != State.STATE_READY){
                        prepare(ICometConf);
                    }
                    connect();
                }
            }
        },DELAY[reConnectTimes > 2 ? 2 : reConnectTimes] * 1000);
    }
    /**
     * 生成icomet连接地址
     * @param url 连接地址
     * @return 最终地址
     */
    private String buildUrl(String url){
        StringBuilder finalUrl = new StringBuilder();
        if(!this.ICometConf.getHost().startsWith("http://")){
            finalUrl.append("http://");
        }
        finalUrl.append(this.ICometConf.getHost());
        if(!isEmpty(this.ICometConf.getPort())){
            finalUrl.append(":").append(this.ICometConf.getPort());
        }
        if(!isEmpty(this.ICometConf.getUrl())){
            finalUrl.append("/").append(url);
        }
        if(this.channel == null){
            return finalUrl.toString();
        }
        finalUrl.append("?cname=").append(this.channel.getCname())
                .append("&token=").append(this.channel.getToken())
                .append("&content=").append(this.ICometConf.getContent());
        return finalUrl.toString();
    }
    /**
     * 字符串是否为空
     * @param value 字符串
     * @return 是否满足
     */
    private boolean isEmpty(String value){
        return value == null || value.length() < 1;
    }
    /**
     * 接受和推送消息线程
     */
    private class CometThread implements Runnable{
        /**
         * Gson工具
         */
        private Gson gson = new Gson();

        @Override
        public void run() {
            if(iCometCallBack == null){
                throw new IllegalArgumentException("ICometCallBack is null.");
            }
            try {
                //循环读取
                while(connectState == State.STATE_COMET){
                    Message message = messageInputStream.readMessage();
                    if(message != null){
                        iCometCallBack.onMsgArrived(message);
                        if (Type.TYPE_DATA.equals(message.getType()) && message.getContent() != null && message.getContent().length() > 0) {
                            String content = message.getContent();
                            Message.Content content1 = new Message.Content();
                            content1.setFrom("crm");
                            content1.setId("12344");
                            content1.setTime("now");
                            content1.setText(content);
                            channel.setSeq(channel.getSeq() + 1);
                            iCometCallBack.onDataMsgArrived(content1);
                        }else if(Type.TYPE_NOOP.equals(message.getType())){
                            //TODO 处理心跳消息
                        }else{
                            iCometCallBack.onErrorMsgArrived(message);
                        }
                    }else{
                        //TODO 处理失败消息
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                iConnectCallBack.onDisconnect();
                connectState = State.STATE_DISCONNECT;
                reConnect();
                return;
            }

            connectState = State.STATE_STOP;
            if(iConnectCallBack != null){
                iConnectCallBack.onStop();
            }
        }
    }
}
