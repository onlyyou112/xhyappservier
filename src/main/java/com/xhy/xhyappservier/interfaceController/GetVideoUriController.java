package com.xhy.xhyappservier.interfaceController;

import com.xhy.xhyappservier.responseUtil.GetCacheLocalUrl;
import com.xhy.xhyappservier.responseUtil.MyAnnotain;
import com.xhy.xhyappservier.responseUtil.ResJson;
import com.xhy.xhyappservier.service.CleanService;
import com.xhy.xhyappservier.service.GetUrlService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
private GetUrlService getUrlService;
    @Autowired
    private CleanService cleanService;
    @Autowired
    private GetCacheLocalUrl getCacheLocalUrl;


    @RequestMapping("/all")
    @Deprecated
 public ResJson<List,String> getVideoUri(String url){
        String result =getUrlService.getResult(url);
        if(StringUtils.isEmpty(result)){
            return null;
        }
        Document parse = Jsoup.parse(result);
        Elements elementsByClass =  parse.getElementsByClass("list-videos");
        System.out.println("得到的个数为"+elementsByClass.size());
        Element element = elementsByClass.get(1);
        List<HashMap<String, String>> itemsList = getItems(element);
        return new ResJson<List,String>(itemsList,null);
    }


    @RequestMapping("/getvideo")
    @Cacheable(key = "#videourl",unless = "#result.status eq 'fail'")
    public ResJson<List,String> getRealVideoUrl(String videourl){
        String result = getUrlService.getResult(videourl);
        if(StringUtils.isEmpty(result)){
            ResJson<List,String> objectResJson = new ResJson<>();
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
        return new ResJson<List,String>().setData(finalvideourl);
    }
    @RequestMapping("/page/{pageNum}")
    @Cacheable(key = "'pagecache'+#pageNum",unless = "#result== null || #result.nowList.size()==0")
    public ResJson<List,String> pageGet(@PathVariable String pageNum){
        String videoServerUrl = getCacheLocalUrl.getUrl();
        String result = getUrlService.getResult(videoServerUrl+"/?from=" + pageNum);
        if(StringUtils.isEmpty(result)){
            getUrlService.getUrl();
        return null;
        }

        Document parse = Jsoup.parse(result);
        Element pageAll=parse.getElementById("list_videos_most_recent_videos_items");
        Element nowWatch= parse.getElementById("list_videos_videos_watched_right_now_items");
        List<HashMap<String, String>> pageList = getItems(pageAll);
        List<HashMap<String, String>> nowWatchList = getItems(nowWatch);
        ResJson<List,String> resJson=new ResJson<>(pageList,nowWatchList);
        return resJson;

    }
    public List<HashMap<String,String>> getItems(Element element){
        List<HashMap<String,String>> videoList=new ArrayList<>();

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
        return videoList;
    }
   @RequestMapping("/test")
    public void test(){
    getUrlService.getUrl();
    }


}
