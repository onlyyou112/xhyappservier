package com.xhy.xhyappservier.service.serviceimpl;

import com.xhy.xhyappservier.entries.User;
import com.xhy.xhyappservier.mapping.UserMapper;
import com.xhy.xhyappservier.util.ResJson;
import com.xhy.xhyappservier.service.UserService;
import com.xhy.xhyappservier.util.UUIDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: xhyappservier
 * @description: 用户服务
 * @author: Mr.Wang
 * @create: 2019-07-20 21:56
 **/

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Override
    public ResJson<List, String> addUser(User user) {
        ResJson<List,String> resJson=new ResJson<>();
        User user1 = userMapper.findUser(user.getUserName());
        if(user1!=null){
            resJson.setStatus("fail");
            resJson.setData("对不起，当前用户名已存在");
        }
        user.setId(UUIDUtil.getUUID());
        int i = userMapper.addUser(user);
        if(i>0){
            return resJson;
        }
        resJson.setStatus("FAIL");
            return resJson;

    }

    @Override
    public User findUser(String userName) {
        return   userMapper.findUser(userName);

    }
}
