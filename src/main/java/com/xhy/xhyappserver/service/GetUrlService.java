package com.xhy.xhyappserver.service;

import java.util.Map;

/**
 * @program: xhyappserver
 * @description:
 * @author: Mr.Wang
 * @create: 2019-06-30 14:21
 **/


public interface GetUrlService {
   public void  getUrl();

   public String getResult(String url);
   String postResult(String url, Map<String,String> cookies, Map<String,String> headers, Map<String,String> params);
}
