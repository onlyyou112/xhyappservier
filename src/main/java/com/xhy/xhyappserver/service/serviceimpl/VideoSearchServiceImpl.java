package com.xhy.xhyappserver.service.serviceimpl;

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
        Map<String,String> header=new HashMap<>();
        header.put("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
        header.put("x-requested-with","XMLHttpRequest");
        String contentStr = getUrlService.postResult("https://www.qsptv.com/index.php?s=search-index-wd-"+word+"-sid-1-p-"+page, null, header,null);
        if(StringUtils.isEmpty(contentStr)){
            return new ResJson<List,String>().setStatus("fail").setData("对不起，服务器数据处理失败，稍后再试，如多次错误，请尽快通知管理员处理。");
        }
        Document parse = Jsoup.parse(contentStr);
        Element content = parse.getElementById("content");
        Elements elementsByClass = content.getElementsByClass("video-pic");
        List<HashMap<String,String>> videoList=new ArrayList<>();
        for (Element ele :elementsByClass) {
            HashMap<String,String> videoMap=new HashMap<>();
            String title = ele.attr("title");
            String imgPath = ele.attr("data-original");
            String href = ele.attr("href");
            videoMap.put("title",title);
            videoMap.put("videourl",href);
            videoMap.put("picuri",imgPath);
            videoMap.put("duration","后续提供");
            videoMap.put("viewscount","后续提供");
            videoMap.put("addTime","后续提供");
            videoList.add(videoMap);
            System.out.println(title+href+imgPath);
            videoList.add(videoMap);
        }
        return new ResJson<List,String>().setPageList(videoList);


    }
}
