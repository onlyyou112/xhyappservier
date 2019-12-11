package com.xhy.xhyappserver.util;

/**
 * @program: xhyappserver
 * @description:
 * @author: Mr.Wang
 * @create: 2019-06-23 14:38
 **/


public class ResJson<E,V> {
    private String  status= "success";
    public E pageList;
    public E nowList;
    public V data;
    /**
     * 该变量用于存储统计视频的数量
     */
    public int count;

    public int getCount() {
        return count;
    }

    public ResJson setCount(int count) {
        this.count = count;
        return this;
    }

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
