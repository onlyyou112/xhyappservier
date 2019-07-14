package com.xhy.xhyappservier.interfaceController;

import com.xhy.xhyappservier.responseUtil.ResJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestMethodArgumentResolver;

import java.util.List;

/**
 * @program: xhyappservier
 * @description: 登录控制器
 * @author: Mr.Wang
 * @create: 2019-07-14 17:42
 **/

@Controller
@RequestMapping("/")
public class LoginController {
    @RequestMapping("clogin")
    @ResponseBody
    public ResJson<List,String> login(String username,String password){
        ResJson<List, String> listStringResJson = new ResJson<>();
        listStringResJson.setData("ok");
        return listStringResJson;
    }
}
