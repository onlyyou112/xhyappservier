package com.xhy.xhyappservier.service.serviceimpl;

import com.xhy.xhyappservier.encrypt.EncryptUtill;
import com.xhy.xhyappservier.entries.User;
import com.xhy.xhyappservier.mapping.UserMapper;
import com.xhy.xhyappservier.util.ResJson;
import com.xhy.xhyappservier.service.UserService;
import com.xhy.xhyappservier.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        try {
            user.setId(UUIDUtil.getUUID());
            if(StringUtils.isEmpty(user.getPassWord())){
         throw new Exception("密码为空");
            }
            user.setPassWord(EncryptUtill.encrypt(user.getPassWord()));
        }catch(Exception e){
            resJson.setStatus("FAIL");
            resJson.setData("密码格式不正确！");
            return resJson;
        }
            int i = userMapper.addUser(user);
            if (i > 0) {
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
