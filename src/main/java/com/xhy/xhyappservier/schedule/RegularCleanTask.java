package com.xhy.xhyappservier.schedule;

import com.xhy.xhyappservier.service.CleanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: xhyappservier
 * @description: 定时清理缓存
 * @author: Mr.Wang
 * @create: 2019-07-06 21:51
 **/

@Component
public class RegularCleanTask {
 private  final  int second = 1000;
 private  final int min=second*60;
 private  final int hour = 60*min;
 @Autowired
 private CleanService cleanService;
    @Scheduled(fixedRate = 2 *hour )
    public void clean(){
        System.out.println("清理中！");
        cleanService.clean();
        System.out.println("清理完成！");
    }

}
