package com.xhy.xhyappserver.service;

import com.xhy.xhyappserver.entries.FavoriteVideo;
import com.xhy.xhyappserver.util.Retjson;

import java.util.List;

public interface FavoriteService {
   FavoriteVideo getFavorite(String id);
   List<FavoriteVideo> getFavoriteList(String uid);
   Retjson deleteFavorite(String  id);
   Retjson addFavorite(FavoriteVideo favoriteVideo);
   Retjson deleteAllFavorite(String uid);
}
