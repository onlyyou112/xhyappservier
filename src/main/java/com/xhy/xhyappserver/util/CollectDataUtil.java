package com.xhy.xhyappserver.util;

import com.xhy.xhyappserver.entries.MovieEntry;
import com.xhy.xhyappserver.mapping.TelplayMapper;
import com.xhy.xhyappserver.service.GetUrlService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Component
public class CollectDataUtil {
    @Autowired
    GetUrlService getUrlService;
    @Resource
    TelplayMapper telplayMapper;
    public void snatchData() {
        for(int i=0;i<230;i++) {
            String result = getUrlService.getResult("https://www.qsptv.net/index.php?s=vod-type-id-17-mcid--area--year--letter--order-addtime-picm-1-p-" + i);
            Document parse = Jsoup.parse(result);
            Elements select = parse.select("div.box-video-list>.item>.clearfix");
            Element movieUl = select.get(0);
            Elements lis = movieUl.select("ul>li");
            List<MovieEntry> movieEntries = new ArrayList<>();
            for (Element li : lis) {
                Element a = li.select(".video-pic.loading").get(0);
                String picHref = a.attr("data-original");
                String movieDetailHref = a.attr("href");
                String title = a.attr("title");
                String note = a.select(".note").get(0).text();
                String actorPerson = li.select(".subtitle").get(0).text();
                String scope = a.select(".score").get(0).text();
                MovieEntry movieEntry = new MovieEntry(picHref, movieDetailHref, title, note, actorPerson, scope);
                System.out.println(movieEntry.toString());
                movieEntries.add(movieEntry);
            }
        }
    }
}
