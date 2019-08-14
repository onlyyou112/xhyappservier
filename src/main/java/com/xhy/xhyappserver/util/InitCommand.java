package com.xhy.xhyappserver.util;

import com.xhy.xhyappserver.service.GetUrlService;
import com.xhy.xhyappserver.service.serviceimpl.GetNewUrlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @program: xhyappservier
 * @description: 该类会在bean初始化完成后执行，自动调用run方法
 * 可以适用@Order（Value=1,2,3.。。）进行执行顺序设定
 * @author: Mr.Wang
 * @create: 2019-08-04 14:15
 **/



@Component
public class InitCommand implements CommandLineRunner {
    @Autowired
    GetUrlService getUrlService;
    @Override
    public void run(String... args) {
        try {
            getUrlService.getUrl();
            System.out.println("获取服务器url成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
