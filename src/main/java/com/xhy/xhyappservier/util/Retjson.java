package com.xhy.xhyappservier.util;

public class Retjson<E> {
 private E data;
 private String status= "success";
 private String msg;
    public static Retjson fail(String msg){
        return new Retjson().setStatus("fail").setMsg(msg);
    }
    public E getData() {
        return data;
    }

    public Retjson setData(E data) {
        this.data = data;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Retjson setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Retjson setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
