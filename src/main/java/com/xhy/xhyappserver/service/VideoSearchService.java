package com.xhy.xhyappserver.service;

import com.xhy.xhyappserver.util.ResJson;

import java.util.HashMap;
import java.util.List;

public interface VideoSearchService {
    ResJson<List,String> searchVideo(String word, Integer page);
    HashMap<String, HashMap<String, String>> getVideoList(String videoHref);

    String getVideoPlayUrl(String videoHref);
}
