package com.xhy.xhyappserver;

import com.xhy.xhyappserver.entries.MovieEntry;
import com.xhy.xhyappserver.entries.MoviePlayListEntry;
import com.xhy.xhyappserver.mapping.TelplayMapper;
import com.xhy.xhyappserver.service.GetUrlService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XhyappservierApplicationTests {
@Autowired
    GetUrlService getUrlService;
    @Test
    public void contextLoads() {
            String result = getUrlService.getResult("https://www.qsptv.net/play/73555-0-12.html");
            Document parse = Jsoup.parse(result);
            Element script=parse.select("#zanpiancms_player>.embed-responsive>script").first();
            String jsStr="function getUrl(){"+script.html()+"return zanpiancms_player['url'];}";
            try {
                ScriptEngineManager scriptEngineManager=new ScriptEngineManager();
              ScriptEngine se = scriptEngineManager.getEngineByName("js");

                    se.eval(jsStr);
                    Invocable inv2 = (Invocable) se;
                    String res=(String)inv2.invokeFunction("getUrl");
                    System.out.println(res);
            }catch (Exception e){
                e.printStackTrace();
            }


    }
    /*
    因爲maper没有手动编写实现类，所以springboot找不到，使用resource就可以了
     */
    @Resource
    TelplayMapper telplayMapper;
    @Test
    public void databaseTest(){
        List<MovieEntry> movieEntries = telplayMapper.selectWithPage(1, 10);
        for(MovieEntry movieEntry:movieEntries){
            List<MoviePlayListEntry> moviePlayListEntries = movieEntry.getMoviePlayListEntries();
            String title="";
            HashMap<String,List<MoviePlayListEntry>> hashMap=new HashMap<String,List<MoviePlayListEntry>>();
            for(MoviePlayListEntry moviePlayListEntry:moviePlayListEntries){
                List<MoviePlayListEntry> o = hashMap.get(moviePlayListEntry.getVideoSource());
                if(o==null){
                    o=new ArrayList<>();
                    o.add(moviePlayListEntry);
                    hashMap.put(moviePlayListEntry.getVideoSource(),o);
                }else{
                    o.add(moviePlayListEntry);
                }
            }
            System.out.println("hello");
        }
        System.out.println("hello!");
    }



}
