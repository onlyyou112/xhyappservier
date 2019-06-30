package com.xhy.xhyappservier.responseUtil;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceEditor;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

/**
 * @program: xhyappservier
 * @description: 获取本地存储的url
 * @author: Mr.Wang
 * @create: 2019-06-30 14:23
 *
 **/


public class GetCacheLocalUrl {
    public static final String URL_PRO_NAME="videoserviceurl";
    public static String getLocalPropertiesUrl(){
        try {
            File file = ResourceUtils.getFile("classpath:url.properties");
            Properties properties=new Properties();
            properties.load(new FileReader(file));
            String url_pro_name = properties.getProperty(URL_PRO_NAME);
            return url_pro_name;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
