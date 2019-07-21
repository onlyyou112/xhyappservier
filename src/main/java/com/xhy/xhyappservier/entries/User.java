package com.xhy.xhyappservier.entries;

/**
 * @program: xhyappservier
 * @description: 用户
 * @author: Mr.Wang
 * @create: 2019-07-20 21:55
 **/


public class User {
    private String userName;
    private String passWord;
private String id;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
