package com.xhy.xhyappservier.util;

/**
 * @program: xhyappservier
 * @description:
 * @author: Mr.Wang
 * @create: 2019-06-23 14:38
 **/


public class ResJson<E,V> {
    private String  status= "success";
    public E pageList;
    public E nowList;
    public V data;

    public ResJson() {

    }

    public V getData() {
        return data;
    }

    public ResJson setData(V data) {
        this.data = data;
        return this;
    }

    public ResJson(E pageList, E nowList){this.pageList=pageList;this.nowList=nowList; }

    public ResJson setStatus(String status) {
        this.status = status;
        return this;
    }


    public String getStatus(){return status; }

    public E getPageList() {
        return pageList;
    }

    public ResJson setPageList(E pageList) {
        this.pageList = pageList;
        return this;
    }

    public E getNowList() {
        return nowList;
    }

    public ResJson setNowList(E nowList) {
        this.nowList = nowList;
        return this;
    }
}
