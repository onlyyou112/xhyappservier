package com.xhy.xhyappservier.interfaceController;

import com.xhy.xhyappservier.entries.User;
import com.xhy.xhyappservier.util.ResJson;
import com.xhy.xhyappservier.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    UserService userService;
    @RequestMapping("clogin")
    @ResponseBody
    public ResJson<List,String> login(String username,String password){
        ResJson<List, String> listStringResJson = new ResJson<>();
        listStringResJson.setData("ok");
        return listStringResJson;
    }
    @RequestMapping("cregister")
    @ResponseBody
    public ResJson<List,String> register(User user){
        if((!StringUtils.isEmpty(user.getUserName()))&&(!StringUtils.isEmpty(user.getUserName()))){
            ResJson<List, String> listStringResJson=  userService.addUser(user);
            return listStringResJson;
        }else{
            ResJson<List,String> resJson=new ResJson<>();
            resJson.setStatus("fail");
            resJson.setData("用户名或密码为空");
            return resJson;
        }



    }
}
