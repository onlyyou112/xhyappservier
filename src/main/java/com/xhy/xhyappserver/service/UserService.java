package com.xhy.xhyappserver.service;


import com.xhy.xhyappserver.entries.User;
import com.xhy.xhyappserver.util.ResJson;

import java.util.List;

public interface UserService {
    ResJson<List, String> addUser(User user);
   User findUser(String userName);
}
