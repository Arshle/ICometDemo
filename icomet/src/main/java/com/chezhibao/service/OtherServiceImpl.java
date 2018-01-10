/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: OtherServiceImpl.java
 * Author:   Arshle
 * Date:     2018年01月03日
 * Description:
 */
package com.chezhibao.service;

import com.chezhibao.intf.OtherService;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Arshle
 */
@Service("otherService")
public class OtherServiceImpl implements OtherService {
    @Override
    public String doQuery() {
        try {
            Random random = new Random();
            int[] array = new int[6];
            Arrays.fill(array,random.nextInt(50));
            StringBuilder sb = new StringBuilder();
            for(int s : array){
                sb.append(s).append(",");
            }
            sb = sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
