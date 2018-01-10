/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ICometController.java
 * Author:   Arshle
 * Date:     2018年01月02日
 * Description:
 */
package com.chezhibao.controller;

import com.chezhibao.dubbo.entity.Message;
import com.chezhibao.dubbo.intf.ICometClient;
import com.chezhibao.intf.OtherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

/**
 * @author Arshle
 */
@Controller("iCometController")
@RequestMapping("/icomet")
public class ICometController {

    @Resource
    private ICometClient iCometClient;

    @Resource
    private OtherService otherService;


    /**
     * 推送消息
     * @param cname 通道名称
     * @param content 消息内容
     * @param token 验证token
     * @return 连接
     */
    @RequestMapping("/push")
    @ResponseBody
    public String push(String cname,String content,String token){
        try {
            if("update".equals(content)){
                Message message = new Message();
                message.setCname(cname);
                String result = otherService.doQuery();
                message.setContent(result);
                iCometClient.push(message,token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

}
