package com.xhy.xhyappserver.mapping;

import com.xhy.xhyappserver.entries.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: xhyappserver
 * @description: 用户mapper
 * @author: Mr.Wang
 * @create: 2019-07-20 21:57
 **/

@Mapper
public interface UserMapper {
    int addUser(User user);
    User findUser(String userName);
}
