/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: comet.js
 * Author:   zhangdanji
 * Date:     2018年01月02日
 * Description:   
 */
/**
 * IComet工具类
 * @param config 配置信息
 */
function iComet(config){
    var self = this;
    //当页面存在多个长连接时用来标识不同的通道
    if(iComet.indexId === undefined){
        iComet.indexId = 0;
    }
    self.pubUrl = config.pubUrl;
    //通道名称
    self.cname = config.channel;
    //接受消息的回调函数
    self.onMessage = function(msg){
        var callback = config.callback;
        if(callback){
            callback(msg);
        }
    };
    //建立连接超时时间
    self.connectTimeout = config.connectTimeout;
    //长连接序号
    self.id = iComet.indexId ++;
    //回调函数名称
    self.callback = 'icomet_' + self.id;
    //计时器
    self.timer = null;
    //建立通道计时器
    self.signTimer = null;
    //推送消息计时器
    self.pushTimer = null;
    //是否已暂停
    self.stopped = true;
    //最后建立通道时间
    self.lastSubTime = 0;
    //是否需要快速重连
    self.needFastReconnect = true;
    //ICometToken
    self.token = '';
    //data类型消息的序号
    self.dataSeq = 0;
    //心跳类型消息序号
    self.noopSeq = 0;
    //建立通道回调
    self.signCallBack = null;
    //拼接创建频道的连接地址
    if(config.signUrl){
        if(config.signUrl.indexOf('?') === -1){
            self.signUrl = config.signUrl + '?';
        }else{
            self.signUrl = config.signUrl + '&';
        }
        self.signUrl += 'cb=' + self.callback + '&cname=' + self.cname;
    }
    //拼接订阅连接地址
    if(config.subUrl.indexOf('?') === -1){
        self.subUrl = config.subUrl + '?';
    }else{
        self.subUrl = config.subUrl + '&';
    }
    self.subUrl += 'cb=' + self.callback;
    //IComet消息推送接受回调函数
    window[self.callback] = function(msg,in_batch){
        //如果是多条消息
        if(msg instanceof Array){
            for(var i = 0; i < msg.length; i++){
                if(!msg){
                    continue;
                }
                if(i === msg.length - 1){
                    window[self.callback](msg[i]);
                }else{
                    window[self.callback](msg[i], true);
                }
            }
            return;
        }
        if(self.stopped){
            return;
        }
        if(!msg){
            return;
        }
        //如果是404未找到
        if(msg.type === '404'){
            //调用js方法
            onMessageError('no channel found.');
            return;
        }
        //token错误
        if(msg.type === '401'){
            //调用消息错误方法
            onMessageError('error token.');
            return;
        }
        //连接太多
        if(msg.type === '429'){
            //5秒后重新连接
            setTimeout(subChannel, 5000);
            return;
        }
        //创建通道成功
        if(msg.type === 'sign'){
            if(self.signCallBack){
                self.signCallBack(msg);
            }
            return;
        }
        //心跳数据
        if(msg.type === 'noop'){
            self.lastSubTime = (new Date()).getTime();
            if(msg.seq === self.noopSeq){
                //心跳最大值
                if(self.noopSeq === 2147483647){
                    self.noopSeq = -2147483648;
                }else{
                    self.noopSeq ++;
                }
                //过几秒重连
                setTimeout(subChannel, 1000 + Math.random() * 2000);
            }
            return;
        }
        //订阅消息的序号错误
        if(msg.type === 'next_seq'){
            self.dataSeq = msg.seq;
            subChannel();
        }
        //广播消息
        if(msg.type === 'broadcast'){
            self.lastSubTime = (new Date()).getTime();
            self.onMessage(msg);
            //重新连接
            subChannel();
        }
        //数据消息
        if(msg.type === 'data'){
            self.lastSubTime = (new Date()).getTime();
            if(msg.seq !== self.dataSeq){
                if(msg.seq === 0 || msg.seq === 1){
                    self.onMessage(msg);
                }else if(msg.seq < self.data_seq){
                    onMessageError('overtime message');
                }else{
                    self.onMessage(msg);
                }
                //记录当前的消息序号
                self.dataSeq = msg.seq;
                //序号最大值
                if(self.dataSeq === 2147483647){
                    self.dataSeq = -2147483648;
                }else{
                    self.dataSeq ++;
                }
                if(!in_batch){
                    //快速重新连接
                    var now = new Date().getTime();
                    if(self.needFastReconnect || now - self.lastSubTime > 3 * 1000){
                        self.needFastReconnect = false;
                        subChannel();
                    }
                }
            }else{
                if(self.dataSeq === 2147483647){
                    self.dataSeq = -2147483648;
                }else{
                    self.dataSeq ++;
                }
                self.onMessage(msg);
                if(!in_batch){
                    subChannel();
                }
            }
            return;
        }
    };
    //创建通道
    self.sign = function(callback){
        self.signCallBack = callback;
        var url = self.signUrl;
        $.ajax({
            url: url,
            dataType: "jsonp",
            jsonpCallback: "cb"
        });
    };
    //订阅通道
    var subChannel = function(){
        //状态设为工作中
        self.stopped = false;
        //最后订阅时间
        self.lastSubTime = (new Date()).getTime();
        //停止当前的回调函数
        $('script.' + self.callback).remove();
        //拼接订阅的连接地址
        var url = self.subUrl
            + '&cname=' + self.cname
            + '&seq=' + self.dataSeq
            + '&noop=' + self.noopSeq
            + '&token=' + self.token;
        //jsonP请求IComet
        $.ajax({
            url : url,
            dataType : "jsonp",
            jsonpCallback : "cb"
        });
    };
    //开始建立连接
    self.start = function(){
        //置为工作状态
        self.stopped = false;
        //清除计时器
        if(self.timer){
            clearTimeout(self.timer);
            self.timer = null;
        }
        //创建通道
        if(self.signUrl){
            //循环创建通道
            if(!self.signTimer){
                self.signTimer = setInterval(self.start, 3000 + Math.random() * 2000);
            }
            //创建通道
            self.sign(function(msg){
                //建立通道成功清除定时器
                if(self.signTimer){
                    clearTimeout(self.signTimer);
                    self.signTimer = null;
                }else{
                    return;
                }
                //如果正在工作
                if(!self.stopped){
                    self.cname = msg.cname;
                    self.token = msg.token;
                    try{
                        var a = parseInt(msg.sub_timeout) || 0;
                        self.connectTimeout = (a * 1.2) * 1000;
                    }catch(e){}
                    //检查连接时长
                    self.startTimeoutCheck();
                    subChannel();
                }
            });
        }else{
            //检查连接时长
            self.startTimeoutCheck();
            subChannel();
        }
    };
    //停止长连接
    self.stop = function(){
        //置为停止状态
        self.stopped = true;
        self.lastSubTime = 0;
        self.needFastReconnect = true;
        if(self.timer){
            clearTimeout(self.timer);
            self.timer = null;
        }
        if(self.signTimer){
            clearTimeout(self.signTimer);
            self.signTimer = null;
        }
    };
    //检查连接时长
    self.startTimeoutCheck = function(){
        if(self.timer){
            clearTimeout(self.timer);
        }
        self.timer = setInterval(function(){
            var now = (new Date()).getTime();
            if(now - self.lastSubTime > self.connectTimeout){
                //连接超时，重新连接
                self.stop();
                self.start();
            }
        }, 1000);
    };
    //发送消息
    self.pub = function(content, callback){
        if(typeof(content) !== 'string' || !self.pubUrl){
            return false;
        }
        if(self.pushTimer){
            clearTimeout(self.pushTimer);
        }
        if(!self.token){
            self.pushTimer = setTimeout(self.pub(content,callback),1000);
            return false;
        }
        if(callback === undefined){
            callback = function(){};
        }
        var data = {};
        data.cname = self.cname;
        data.token = self.token;
        data.content = content;
        $.getJSON(self.pubUrl, data, callback);
    };
}