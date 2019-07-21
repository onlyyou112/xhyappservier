package com.xhy.xhyappservier.util;

import com.xhy.xhyappservier.entries.PropertiesName;
import com.xhy.xhyappservier.service.GetUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

/**
 * @program: xhyappservier
 * @description: 获取本地存储的url
 * @author: Mr.Wang
 * @create: 2019-06-30 14:23
 *
 **/

@Component
public class GetCacheLocalUrl {
    @Autowired
    private GetUrlService getUrlService;

    private String url="";
    //初始化构造器时获取一次url；
   public  GetCacheLocalUrl(){
       try {
           File file = ResourceUtils.getFile("classpath:url.properties");
           Properties properties=new Properties();
           properties.load(new FileReader(file));
           String url_pro_name = properties.getProperty(PropertiesName.URL_PRO_NAME);
          url=url_pro_name;
       }catch(Exception e){
           url="http://www.caca019.com";
           e.printStackTrace();
       }

    }


    /**
     * 获取本地文件的属性的url，将其配入属性；
     */
    public  void getLocalPropertiesUrl(){
        try {
            File file = ResourceUtils.getFile("classpath:url.properties");
            Properties properties=new Properties();
            properties.load(new FileReader(file));
            String url_pro_name = properties.getProperty(PropertiesName.URL_PRO_NAME);
          url=url_pro_name;
        }catch(Exception e){
            e.printStackTrace();
            getUrlService.getUrl();
        }
    }
    public void saveToProperties(String name, String value) {
        try {
            File file = ResourceUtils.getFile("classpath:url.properties");
          FileReader fileReader=  new FileReader(file);
            Properties properties=new Properties();
            properties.load(fileReader);
            fileReader.close();
           properties.setProperty(name,value);
               properties.store(new FileWriter(file),null);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

   public String  getUrl(){
        return url;
    }

}
