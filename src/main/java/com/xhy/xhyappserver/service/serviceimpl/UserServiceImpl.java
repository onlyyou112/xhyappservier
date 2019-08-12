package com.xhy.xhyappserver.service.serviceimpl;

import com.xhy.xhyappserver.encrypt.EncryptUtill;
import com.xhy.xhyappserver.entries.User;
import com.xhy.xhyappserver.mapping.UserMapper;
import com.xhy.xhyappserver.util.ResJson;
import com.xhy.xhyappserver.service.UserService;
import com.xhy.xhyappserver.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @program: xhyappserver
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
        }else {
            try {
                if (StringUtils.isEmpty(user.getPassWord())) {
                    throw new Exception("密码为空");
                }
                user.setId(UUIDUtil.getUUID());
                user.setPassWord(EncryptUtill.encrypt(user.getPassWord()));
                try {
                    //注册用户
                    int i = userMapper.addUser(user);
                }catch(Exception e1){
                    resJson.setStatus("fail");
                    resJson.setData("注册失败，可能是系统故障！");
                }
            } catch (Exception e) {
                resJson.setStatus("FAIL");
                resJson.setData("密码格式不正确！");
            }

        }
        //不出任何意外，就是成功，出现意外在上面已经进行操作，此处返回即可;
            return resJson;

    }

    @Override
    public User findUser(String userName) {
        return   userMapper.findUser(userName);

    }
}
