package com.xhy.xhyappserver.service.serviceimpl;

import com.xhy.xhyappserver.entries.PropertiesName;
import com.xhy.xhyappserver.util.GetCacheLocalUrl;
import com.xhy.xhyappserver.service.GetUrlService;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.*;

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
    //这段代码需要同步加锁，以后需要处理
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
            if(e instanceof UnknownHostException || e instanceof HttpHostConnectException){
                //处理一下url异常
                System.err.println("系统可能尚未初始化完成！");
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

    /**
     *
     * @param url 请求的url
     * @param cookies 请求的cookie
     * @param headers 请求头
     * @param params  请求参数
     * @return
     */
    @Override
    public String postResult(String url, Map<String,String> cookies, Map<String,String> headers,Map<String,String> params){
        try {
        HttpPost httppost=new HttpPost(url); //建立HttpPost对象
        if(params!=null&&params.size()>0) {
            List<NameValuePair> paramsList=new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> param : params.entrySet()) {
                paramsList.add(new BasicNameValuePair(param.getKey(),param.getValue()));
            }
            httppost.setEntity(new UrlEncodedFormEntity(paramsList, HTTP.UTF_8));
        }
        if(headers!=null&& headers.size()>0){
            for(Map.Entry<String,String> header:headers.entrySet()){
                httppost.addHeader(new BasicHeader(header.getKey(),header.getValue()));
            }
        }

        if(cookies!=null&&cookies.size()>0){
        StringBuffer cookiesStr=new StringBuffer();
            for (Map.Entry<String,String> cookie:cookies.entrySet()) {
                cookiesStr.append(cookie.getKey()+"="+cookie.getValue()+";");
            }
            httppost.addHeader(new BasicHeader("cookie",cookiesStr.toString()));
        }

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(httppost);
            if(response.getStatusLine().getStatusCode()==200){
                HttpEntity httpEntity = response.getEntity();
                String content = EntityUtils.toString(httpEntity);
                return content;
            }else{
                return "";
            }



        }catch (Exception e){
            System.err.println("出现异常");
            e.printStackTrace();
            return "";
        }
//发送Post,并返回一个HttpResponse对象
    }


}
