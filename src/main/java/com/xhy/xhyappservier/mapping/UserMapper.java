package com.xhy.xhyappservier.mapping;

import com.xhy.xhyappservier.entries.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: xhyappservier
 * @description: 用户mapper
 * @author: Mr.Wang
 * @create: 2019-07-20 21:57
 **/

@Mapper
public interface UserMapper {
    int addUser(User user);
    User findUser(String userName);
}
