package com.xhy.xhyappserver.service.serviceimpl;

import com.xhy.xhyappserver.entries.MovieEntry;
import com.xhy.xhyappserver.mapping.TelplayMapper;
import com.xhy.xhyappserver.service.ClassifyService;
import com.xhy.xhyappserver.service.GetUrlService;
import com.xhy.xhyappserver.util.Retjson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class ClassifyServiceImpl implements ClassifyService {
    @Autowired
    GetUrlService getUrlService;
    @Resource
    TelplayMapper telplayMapper;
    @Override
    public Retjson getTeleplay(Integer page) {
        try {
            List<MovieEntry> movieEntries = telplayMapper.selectWithPage(page, 12);
            return new Retjson().setData(movieEntries);

        }catch (Exception e){
            e.printStackTrace();
            return Retjson.fail("服务器数据处理失败");
        }
    }
}
