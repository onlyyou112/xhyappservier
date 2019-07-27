package com.xhy.xhyappservier.mapping;

import com.xhy.xhyappservier.entries.FavoriteVideo;

import java.util.List;

public interface FavoriteMapper {
    List<FavoriteVideo> findAllFavoriteVideo();
    int addFavorite(FavoriteVideo favoriteVideo);
    FavoriteVideo findVideo(FavoriteVideo favoriteVideo);
    int deleteVideo(String id);
    int deleteAllFavorite(String userId);
}
