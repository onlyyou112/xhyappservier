package com.xhy.xhyappservier.aop;

import com.xhy.xhyappservier.responseUtil.ResJson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @program: xhyappservier
 * @description:
 * @author: Mr.Wang
 * @create: 2019-06-30 11:18
 **/

@Aspect
@Component
public class MyAop {
    /*暂时没用，准备后用*/
    @Around("@annotation(com.xhy.xhyappservier.responseUtil.MyAnnotain)")
    public Object addAdivce(ProceedingJoinPoint joinPoint){
        System.out.println("注解。。。");
        ResJson resJson=new ResJson();
        resJson.setData("how are you?");
        return resJson;
    }
}
