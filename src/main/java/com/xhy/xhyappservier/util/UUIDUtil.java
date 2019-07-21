package com.xhy.xhyappservier.util;

import java.util.UUID;

/**
 * @program: xhyappservier
 * @description: UUID工具
 * @author: Mr.Wang
 * @create: 2019-07-21 13:34
 **/


public class UUIDUtil {
    public static String getUUID(){
       return  UUID.randomUUID().toString().replaceAll("-","");
    }
}
