package com.xhy.xhyappserver.interfaceController;

import com.xhy.xhyappserver.service.ClassifyService;
import com.xhy.xhyappserver.util.Retjson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classifymovie")
public class ClassifyMovieController {
    @Autowired
    ClassifyService classifyService;
    @RequestMapping(value = "/gettelplay",method = RequestMethod.GET)
    public Retjson getTeleplay(Integer page,String versionNumer){
        if(StringUtils.isEmpty(versionNumer)){
            return Retjson.fail("没有版本信息");
        }
        Retjson teleplay = classifyService.getTeleplay(page);
        return teleplay;
    }
}
