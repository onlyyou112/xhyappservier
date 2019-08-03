package com.xhy.xhyappserver.util;

import java.util.UUID;

/**
 * @program: xhyappserver
 * @description: UUID工具
 * @author: Mr.Wang
 * @create: 2019-07-21 13:34
 **/


public class UUIDUtil {
    public static String getUUID(){
       return  UUID.randomUUID().toString().replaceAll("-","");
    }
}
