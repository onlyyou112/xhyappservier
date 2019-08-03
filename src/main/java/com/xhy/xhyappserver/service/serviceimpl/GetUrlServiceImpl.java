package com.xhy.xhyappserver.service.serviceimpl;

import com.xhy.xhyappserver.entries.PropertiesName;
import com.xhy.xhyappserver.util.GetCacheLocalUrl;
import com.xhy.xhyappserver.service.GetUrlService;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

/**
 * @program: xhyappserver
 * @description:
 * @author: Mr.Wang
 * @create: 2019-06-30 14:22
 **/

@Service
public class GetUrlServiceImpl implements GetUrlService {
   private String getServerUrl="http://www.ebay.com/usr/ke-6383";
   @Autowired
   private GetCacheLocalUrl getCacheLocalUrl;
    @Override
    /*获取url，放入properties文件中*/
    public void getUrl() {
        String result = getResult(getServerUrl);
        if(!StringUtil.isBlank(result)){
            Document parse = Jsoup.parse(result);
            Elements content = parse.getElementsByClass("inline_value");
            String text=content.text();
            int removeIndex = text.indexOf("最新地址：");
            int reindex=removeIndex+5;
            int firstUrlend = text.indexOf(".com")+4;
            String firstUrl = text.substring(reindex, firstUrlend).trim();
            int secondEndIndex = text.indexOf(".com", firstUrlend)+4;
            String secondUrl = text.substring(firstUrlend, secondEndIndex).trim();
            firstUrl= addHttp(firstUrl);
            secondUrl=addHttp(secondUrl);
            System.out.println("第一个url："+firstUrl);
            System.out.println("第二个url："+secondUrl);
            getCacheLocalUrl.saveToProperties(PropertiesName.URL_PRO_NAME,firstUrl);
            getCacheLocalUrl.saveToProperties(PropertiesName.SECOND_URL_NAME,secondUrl);
            //更改值；
            getCacheLocalUrl.getLocalPropertiesUrl();
        }
    }

    private String addHttp(String firstUrl) {
        if(!firstUrl.startsWith("http://")){
        return "http://"+firstUrl;
        }
        return firstUrl;
    }


    @Override
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
                //处理一下url异常
                getUrl();
                //处理其他异常；
            }
            e.printStackTrace();
            return "";
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