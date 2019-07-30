package com.xhy.xhyappservier.service;

import com.xhy.xhyappservier.entries.FavoriteVideo;
import com.xhy.xhyappservier.util.Retjson;

import java.util.List;

public interface FavoriteService {
   FavoriteVideo getFavorite(String id);
   List<FavoriteVideo> getFavoriteList(String uid);
   Retjson deleteFavorite(String  id);
   Retjson addFavorite(FavoriteVideo favoriteVideo);
   Retjson deleteAllFavorite(String uid);
}
