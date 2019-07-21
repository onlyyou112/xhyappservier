package com.xhy.xhyappservier.service;


import com.xhy.xhyappservier.entries.User;
import com.xhy.xhyappservier.util.ResJson;

import java.util.List;

public interface UserService {
    ResJson<List, String> addUser(User user);
   User findUser(String userName);
}
