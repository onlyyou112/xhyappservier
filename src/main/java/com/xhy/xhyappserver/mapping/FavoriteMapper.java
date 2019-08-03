package com.xhy.xhyappserver.mapping;

import com.xhy.xhyappserver.entries.FavoriteVideo;

import java.util.List;

public interface FavoriteMapper {
    List<FavoriteVideo> findAllFavoriteVideo(String uid);
    int addFavorite(FavoriteVideo favoriteVideo);
    FavoriteVideo findVideo(FavoriteVideo favoriteVideo);
    int deleteVideo(String id);
    int deleteAllFavorite(String userId);
}
