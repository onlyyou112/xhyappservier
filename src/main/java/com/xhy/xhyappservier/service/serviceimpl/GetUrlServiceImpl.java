package com.xhy.xhyappservier.service.serviceimpl;

import com.xhy.xhyappservier.responseUtil.GetCacheLocalUrl;
import com.xhy.xhyappservier.service.GetUrlService;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

/**
 * @program: xhyappservier
 * @description:
 * @author: Mr.Wang
 * @create: 2019-06-30 14:22
 **/

@Service
public class GetUrlServiceImpl implements GetUrlService {
    @Override
    public String getUrl() {

        return null;
    }
    public String getResult(String url ){
        String result = "";

        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建get方式请求对象
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-type", "application/json");
        // 通过请求对象获取响应对象
        CloseableHttpResponse response=null;
        try{
            response = httpClient.execute(httpGet);

            // 获取结果实体
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }}catch(Exception e){
            if(e instanceof UnknownHostException){
                getUrl();
                //处理其他异常；
            }
            e.printStackTrace();
            return null;
        }finally{
            try {
                if(response!=null){response.close();}
            }catch(Exception e1){
                e1.printStackTrace();
            }
        }
        // 释放链接
        return result;
    }



}
