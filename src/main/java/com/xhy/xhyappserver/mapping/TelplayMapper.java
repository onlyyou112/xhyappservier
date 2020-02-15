package com.xhy.xhyappserver.mapping;

import com.xhy.xhyappserver.entries.MovieEntry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TelplayMapper {

    List<MovieEntry> selectWithPage(@Param("startPage")Integer startPage, @Param("limit")Integer limit);
}
