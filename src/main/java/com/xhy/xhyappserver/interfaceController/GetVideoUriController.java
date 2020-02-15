package com.xhy.xhyappserver.interfaceController;

import com.xhy.xhyappserver.util.GetCacheLocalUrl;
import com.xhy.xhyappserver.util.ResJson;
import com.xhy.xhyappserver.service.CleanService;
import com.xhy.xhyappserver.service.GetUrlService;
import com.xhy.xhyappserver.util.Retjson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @program: xhyappserver
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
 public ResJson<List,String> getVideoUri(@RequestParam String url, String versionStatus){
        if(StringUtils.isEmpty(versionStatus)){
           return  oldVersionReturn();
        }



        String result =getUrlService.getResult(url);
        if(StringUtils.isEmpty(result)){
            return oldVersionReturn();
        }
        Document parse = Jsoup.parse(result);
        Elements elementsByClass =  parse.getElementsByClass("list-videos");
        System.out.println("得到的个数为"+elementsByClass.size());
        Element element = elementsByClass.get(1);
        List<HashMap<String, String>> itemsList = getItems(element);
      ResJson<List,String> oldList=oldVersionReturn();
      //将老版本作为第一个item，提示版本过老，因为此接口已废弃！
        List pageList = oldList.getPageList();
        pageList.addAll(itemsList);

        return new ResJson<List,String>(pageList,null);
    }


    @RequestMapping("/getvideo")
    @Cacheable(key = "#videourl",unless = "#result.status eq \"fail\"")
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
    @Cacheable(key = "'pagecache'+'-'+#pageNum+'-'+#versionStatus",unless = "#result== null || #result.pageList==null||#result.pageList.size()==0")
    public ResJson<List,String> pageGet(@PathVariable String pageNum,String versionStatus){
        if(StringUtils.isEmpty(versionStatus)){
            return oldVersionReturn();
        }

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

    private ResJson<List,String> oldVersionReturn(){
        List videoList=new ArrayList();
        HashMap<String,String> videoMap=new HashMap<>();
        videoMap.put("title","版本过老，尽快升级");
        videoMap.put("videourl","https://qncdnct.inter.71edge.com/videos/v1/20151003/59/97/b6ff1ccad408bc20ab15851faea78fe9.mp4?key=07c7d0fc707e52d4611f68858ddb07612&dis_k=244d59a184e33ba31f5b116a114295f3&dis_t=1564971167&dis_dz=CT-HeNan_ZhengZhou&dis_st=40&src=iqiyi.com&uuid=ab08de3d-5d47909f-157&m=v&qd_uri=dash&qd_ip=ab08de3d&qd_k=5ef92776df6c2824638a17e4491ef2d7&qd_p=ab08de3d&qd_uid=&dfp=&ssl=1&qd_src=02020031010000000000&qd_vip=0&dis_src=vrs&pv=0.2&qd_tm=1564971133672&qdv=1&z=qiniucdn_ct&abs_speed=2904");
        videoMap.put("picuri","http://m.iqiyipic.com/u6/image/20151003/26/eb/uv_4007788398_m_601_480_270.jpg");
        videoMap.put("duration","4:46");
        videoMap.put("viewscount","看的人太多，记不清了");
        videoMap.put("addTime","0.0.0.0");
        videoList.add(videoMap);
        return new ResJson<>(videoList,null);
    }
}
