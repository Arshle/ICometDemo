/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: workBench.js
 * Author:   zhangdanji
 * Date:     2018年01月02日
 * Description:   
 */
var icomet = new iComet({
    channel : '02465',
    signUrl : 'http://172.16.10.102:8000/sign',
    subUrl : 'http://172.16.10.102:8100/sub',
    pubUrl : 'icomet/push.htm',
    callback : onMessage
});

$(function(){
    icomet.start();
    icomet.pushTimer = setTimeout(function(){
        icomet.pub('update',function(data){})
    },500);
});
/**
 * 推送消息
 */
function push(){
    icomet.pub('update',function(data){});
}
/**
 * 错误消息
 */
function onMessageError(msg){
    //TODO deal errors
}
/**
 * 接受消息
 * @param msg
 */
function onMessage(msg){
    var nums = msg.content.split(',');
    if(nums && nums.length === 6){
        $('#wait_follow').html(nums[0]);
        $('#total_follow').html(nums[1]);
        $('#emerge_deal').html(nums[2]);
        $('#wait_deal').html(nums[3]);
        $('#wait_auction').html(nums[4]);
        $('#is_auction').html(nums[5]);
    }
}