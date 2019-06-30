package com.xhy.xhyappservier.responseUtil;

/**
 * @program: xhyappservier
 * @description:
 * @author: Mr.Wang
 * @create: 2019-06-23 14:38
 **/


public class ResJson<E> {
    private String  status= "success";
    public E data;

    public ResJson() {

    }
    public ResJson(E data){this.data=data; }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus(){return status; }
    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
