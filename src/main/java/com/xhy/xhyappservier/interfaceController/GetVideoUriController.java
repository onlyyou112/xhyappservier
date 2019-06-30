package com.xhy.xhyappservier.interfaceController;

import com.xhy.xhyappservier.responseUtil.MyAnnotain;
import com.xhy.xhyappservier.responseUtil.ResJson;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: xhyappservier
 * @description:
 * @author: Mr.Wang
 * @create: 2019-06-23 14:36
 **/

@RestController
@RequestMapping("/getvideoData")
@CacheConfig(cacheNames = "urlcache")
public class GetVideoUriController {
    @RequestMapping("/all")
    @Cacheable(key = "listvideo")
 public ResJson<List> getVideoUri(String url){
        List<HashMap<String,String>> videoList= new ArrayList<>(40);
        String result =getResult(url);
        if(StringUtils.isEmpty(result)){

            return null;
        }
        Document parse = Jsoup.parse(result);
        Elements elementsByClass =  parse.getElementsByClass("list-videos");
        System.out.println("得到的个数为"+elementsByClass.size());
        Element element = elementsByClass.get(1);
        Elements items = element.getElementsByClass("item");
        for (Element item:items) {
            //以下获取到的是单个a标签，a标签的属性是视频页面链接，
            // 里面有时长，观看次数，上传时间等
            Element aele = item.getElementsByTag("a").get(0);
            String title = aele.attr("title");
            String href = aele.attr("href");
            String imgPath = aele.getElementsByTag("img").get(0).attr("data-original");
           String duration =aele.getElementsByClass("duration").get(0).text();
           String addTime=aele.getElementsByClass("added").get(0).getElementsByTag("em")
                   .get(0).text();
           String viewscount =aele.getElementsByClass("views").get(0).text();
           StringBuffer buffer=new StringBuffer();
            HashMap<String,String> videoMap=new HashMap<>();
            videoMap.put("title",title);
            videoMap.put("videourl",href);
            videoMap.put("picuri",imgPath);
            videoMap.put("duration",duration);
            videoMap.put("viewscount",viewscount);
            videoMap.put("addTime",addTime);
           videoList.add(videoMap);
        }

        return new ResJson<List>(videoList);
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
    @RequestMapping("/getvideo")
    @Cacheable(key = "#videourl")
    public ResJson<String> getRealVideoUrl(String videourl){
        String result = getResult(videourl);
        if(StringUtils.isEmpty(result)){
            ResJson<String> objectResJson = new ResJson<>();
            objectResJson.setStatus("fail");
            return objectResJson;
        }
        Document parse = Jsoup.parse(result);

        Elements blockvideos = parse.getElementsByClass("block-video");
        Element playerHolder = blockvideos.get(0).getElementsByClass("player-holder").get(0);

        Elements scriptsElement = playerHolder.getElementsByTag("script");
        String finalvideourl="";
        for (Element script:scriptsElement) {
            if(script.attr("src")==null ||script.attr("src").equals("") ){
                String scripttext = script.html();
                int i = scripttext.indexOf("video_url: 'http:");
                if(i>=0){
                    int i1 = scripttext.indexOf("/?br");
                    if(i1>=0){
                       finalvideourl = scripttext.substring(i + 12, i1);
                        System.out.println(finalvideourl);
                    }
                }
            }
        }

        String response="http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";

        return new ResJson<>(finalvideourl);
    }

}
