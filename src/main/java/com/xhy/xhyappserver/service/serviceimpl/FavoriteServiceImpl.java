package com.xhy.xhyappserver.service.serviceimpl;

import com.xhy.xhyappserver.entries.FavoriteVideo;
import com.xhy.xhyappserver.mapping.FavoriteMapper;
import com.xhy.xhyappserver.service.FavoriteService;
import com.xhy.xhyappserver.util.Retjson;
import com.xhy.xhyappserver.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    FavoriteMapper favoriteMapper;
    @Override
    public FavoriteVideo getFavorite(String id) {
        FavoriteVideo favoriteVideo = new FavoriteVideo();
        favoriteVideo.setId(id);
        return favoriteMapper.findVideo(favoriteVideo);

    }

    @Override
    public List<FavoriteVideo> getFavoriteList(String uid) {
        return  favoriteMapper.findAllFavoriteVideo(uid);

    }

    @Override
    public Retjson deleteFavorite(String id) {
        if(StringUtils.isEmpty(id)){
            return Retjson.fail("id为空");
        }
        favoriteMapper.deleteVideo(id);
        return new Retjson();

    }

    @Override
    public Retjson addFavorite(FavoriteVideo favoriteVideo) {
        Retjson<Object> retjson = new Retjson<>();
        if(StringUtils.isEmpty(favoriteVideo.getUserId())){
            return retjson.setStatus("fail").setMsg("用户id为空");
        }
        if(StringUtils.isEmpty(favoriteVideo.getVideoName())){
            return retjson.setStatus("fail").setMsg("视频名称为空");

        }if(StringUtils.isEmpty(favoriteVideo.getVideoUrl())){
            return retjson.setStatus("fail").setMsg("视频地址为空");
        }
        if(StringUtils.isEmpty(favoriteVideo.getDuration())){
            return retjson.setStatus("fail").setMsg("视频时长为空");
        }
        favoriteVideo.setId(UUIDUtil.getUUID());
        favoriteVideo.setCreateTime(new Date());
        try {
            favoriteMapper.addFavorite(favoriteVideo);

        }catch(Exception e){
            return retjson.setStatus("fail").setMsg(e.getMessage());
        }
            return retjson;
    }

    @Override
    public Retjson deleteAllFavorite(String uid) {
        try {
            favoriteMapper.deleteAllFavorite(uid);
        }catch(Exception e){
            return Retjson.fail(e.getMessage());
        }
        return new Retjson();
    }


}
