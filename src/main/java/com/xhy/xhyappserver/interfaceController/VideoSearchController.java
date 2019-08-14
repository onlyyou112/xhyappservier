package com.xhy.xhyappserver.interfaceController;

import com.xhy.xhyappserver.service.GetUrlService;
import com.xhy.xhyappserver.service.VideoSearchService;
import com.xhy.xhyappserver.util.ResJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: xhyappservier
 * @description: 视频搜索接口
 * @author: Mr.Wang
 * @create: 2019-08-13 15:37
 **/

@RestController
public class VideoSearchController {
    @Autowired
    VideoSearchService videoSearchService;
    @RequestMapping("/search")
    public ResJson<List,String> getVideoList(String word, Integer page){
        if(StringUtils.isEmpty(word)||page==null){
            return new ResJson<>().setStatus("fail").setData("对不起，参数不合法！");
        }
        try {
            ResJson<List, String> listStringResJson = videoSearchService.searchVideo(word, page);
            return listStringResJson;
        }catch (Exception e){
            return new ResJson<List,String>().setStatus("fail").setData("内部出错，请稍后再试！");
        }
    }


}
