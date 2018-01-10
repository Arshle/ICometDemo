/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Message.java
 * Author:   zhangdanji
 * Date:     2018年01月02日
 * Description: 消息实体类
 */
package com.chezhibao.dubbo.entity;

import java.io.Serializable;

/**
 * 消息实体类
 *
 * @author zhangdanji
 */
public class Message implements Serializable{

    private static final long serialVersionUID = -3806234767719732715L;
    /**
     * 消息类型
     */
    private String type;
    /**
     * 通道唯一标识名称
     */
    private String cname;
    /**
     * 消息下标序号
     */
    private String seq;
    /**
     * 消息内容
     */
    private String content;
    /**
     * Getters、Setters
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message[type=" + this.type + ",cname=" + this.cname + ",seq=" +
                this.seq + ",content=" + this.content + "]";
    }
    /**
     * 消息具体内容
     */
    public static class Content implements Serializable{

        private static final long serialVersionUID = 149037516166629656L;
        /**
         * 时间
         */
        private String time;
        /**
         * 来源
         */
        private String from;
        /**
         * 消息内容
         */
        private String text;
        /**
         * 消息ID
         */
        private String id;
        /**
         * Getters、Setters
         */
        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
        @Override
        public String toString() {
            return "Content[time=" + this.time + ",from=" + this.from +
                    ",text=" + this.text + ",id=" + this.id + "]";
        }
    }
}