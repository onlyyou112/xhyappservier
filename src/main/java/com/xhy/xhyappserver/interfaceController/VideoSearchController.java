package com.xhy.xhyappserver.interfaceController;

import com.xhy.xhyappserver.util.ResJson;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("/search")
    public ResJson<List,String> getVideoList(String word,Integer page){

        return new ResJson<>();
    }


}
