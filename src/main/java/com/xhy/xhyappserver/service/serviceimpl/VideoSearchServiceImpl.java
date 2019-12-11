package com.xhy.xhyappserver.service.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xhy.xhyappserver.service.GetUrlService;
import com.xhy.xhyappserver.service.VideoSearchService;
import com.xhy.xhyappserver.util.ResJson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @program: xhyappservier
 * @description: 搜索服务
 * @author: Mr.Wang
 * @create: 2019-08-13 15:53
 **/

@Service
public class VideoSearchServiceImpl implements VideoSearchService {
    @Autowired
    GetUrlService getUrlService;
    @Override
    public ResJson<List, String> searchVideo(String word, Integer page) {
        int countNumber=0;
        Map<String,String> header=new HashMap<>();
        header.put("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
        header.put("x-requested-with","XMLHttpRequest");
        String contentStr=getUrlService.postResult("http://www.qsptv.net/index.php?s=search-index-wd-"+word+"-sid-1-p-"+page,null,header,null);
        if(StringUtils.isEmpty(contentStr)){
            return new ResJson<List,String>().setStatus("fail").setData("对不起，服务器数据处理失败，稍后再试，如多次错误，请尽快通知管理员处理。");
        }
        Document parse = Jsoup.parse(contentStr);
        Element count = parse.getElementById("count");
        String span = count.getElementsByTag("span").get(0).text();
        if(!StringUtils.isEmpty(span)){
            span=span.replaceAll("“","").replaceAll("”","");
            try {
                countNumber = Integer.parseInt(span);
            }catch(Exception e){
                e.printStackTrace();
                countNumber=999;
            }

        }
        Element content = parse.getElementById("content");
        Elements elementsByClass = content.getElementsByClass("video-pic");
        List<HashMap<String,Object>> videoList=new ArrayList<>();
        for (Element ele :elementsByClass) {
            HashMap<String,Object> videoMap=new HashMap<>();
            String title = ele.attr("title");
            String imgPath = ele.attr("data-original");
            String href = ele.attr("href");
           /* String videopageContent = getUrlService.postResult("https://www.qsptv.com" + href, null, header, null);*/
            videoMap.put("title",title);
            videoMap.put("videourl",href);
            videoMap.put("picuri",imgPath);
            videoMap.put("duration","后续提供");
            videoMap.put("viewscount","后续提供");
            videoMap.put("addTime","后续提供");
            videoList.add(videoMap);
            System.out.println(title+href+imgPath);
        }
        return new ResJson<List,String>().setPageList(videoList).setCount(countNumber);


    }

    /**
     * 获取视频的所有剧集
     * @param videoHref
     * @return
     */
    @Override
    public HashMap<String, HashMap<String, String>> getVideoList(String videoHref) {
        String videopageContent= getUrlService.getResult("https://www.qsptv.com" + videoHref);
        Document parse1 = Jsoup.parse(videopageContent);
        //全部播放列表，.playlist>.clearfix是播放列表的集合
        Elements playlists = parse1.select(".playlist>.clearfix");

        //播放源的集合
        HashMap<String,HashMap<String,String>> sourceMap=new HashMap<>();
        int i=1;
        for (Element source:playlists) {
            //每个播放源包含的播放剧集map
            HashMap<String,String> playMap=new HashMap<>();
            //source是每个播放源的列表集合，里面包含的每个a标签都是一个视频
            Elements aeles = source.select("a");
            for (Element videoTag:aeles) {
                String hrefs = videoTag.attr("href");
                String text = videoTag.text();
                //视频的剧集号
                playMap.put(text,hrefs);
            }
            //添加播放源
            sourceMap.put("播放源"+i,playMap);
            //需要递增，表示下一个播放源
            i++;
        }
        return sourceMap;
    }

    /**
     * 获取视频播放页面链接
     * @param videoHref
     * @return
     */
    @Override
    public String getVideoPlayUrl(String videoHref) {
        String result = getUrlService.getResult("https://www.qsptv.com" +videoHref);
        Document parse = Jsoup.parse(result);
        Elements scriptParent = parse.select("#zanpiancms_player>.embed-responsive");
        Elements script = scriptParent.select("script");
        Element element = script.get(0);
        String scriptText = element.childNode(0).attr("data");
        System.out.println(scriptText);
        String json_str = scriptText.substring(24, scriptText.length()-1);
        JSONObject parse1 = (JSONObject) JSON.parse(json_str);
        String apiurl = (String)parse1.get("apiurl");
        if((!StringUtils.isEmpty(apiurl))&&apiurl.startsWith("//")){
            apiurl="https:"+apiurl;
        }


        String url = (String)parse1.get("url");
        url=apiurl+url;
        return url;
    }
}
