package com.xhy.xhyappserver.mapping;

import com.xhy.xhyappserver.entries.Version;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VersionMapper {
    int insert(Version record);

    int insertSelective(Version record);
    Version selectLatest();

    int updateVersion(Version version);
}