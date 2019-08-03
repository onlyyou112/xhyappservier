package com.xhy.xhyappserver.service.serviceimpl;

import com.xhy.xhyappserver.service.CleanService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * @program: xhyappserver
 * @description: 清理缓存实现类
 * @author: Mr.Wang
 * @create: 2019-07-06 22:02
 **/
@Service
@CacheConfig(cacheNames = "urlcache")
public class CleanServiceImpl implements CleanService {
    @Override
    @CacheEvict(allEntries = true)
    public void clean() {

    }
}
