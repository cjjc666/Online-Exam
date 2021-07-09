package com.cjjc.exam.utils;

import java.util.Random;

/**
 * @author: cj
 * @date: 2020/11/13 9:34
 * @description:
 */
public class Stringutils {
    public static String getRandomstring(int length){
        String baes="qwertyuiopasdfghjklzxcvbnm1230456789";
        Random random=new Random();
        StringBuffer stringBuffer=new StringBuffer();
        for (int i=0;i<length;i++){
            int num=random.nextInt(baes.length());
            stringBuffer.append(baes.charAt(num));
        }
        return stringBuffer.toString();
        //生成指定长度的随机字符串
    }
}
