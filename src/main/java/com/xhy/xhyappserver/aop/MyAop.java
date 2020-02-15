package com.xhy.xhyappserver.aop;

import com.xhy.xhyappserver.util.ResJson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @program: xhyappserver
 * @description:
 * @author: Mr.Wang
 * @create: 2019-06-30 11:18
 **/

@Aspect
@Component
public class MyAop {
    /*暂时没用，准备后用*/
    @Around("@annotation(com.xhy.xhyappserver.util.MyAnnotain)")
    public Object addAdivce(ProceedingJoinPoint joinPoint){
        System.out.println("注解。。。");
        ResJson resJson=new ResJson();
        return resJson;
    }
}
