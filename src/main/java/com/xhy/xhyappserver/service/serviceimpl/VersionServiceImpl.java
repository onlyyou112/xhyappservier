package com.xhy.xhyappserver.service.serviceimpl;

import com.xhy.xhyappserver.entries.Version;
import com.xhy.xhyappserver.mapping.VersionMapper;
import com.xhy.xhyappserver.service.VersionService;
import com.xhy.xhyappserver.util.Retjson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersionServiceImpl implements VersionService {
    @Autowired
    VersionMapper versionMapper;
    @Override
    public Version getLatestVersion() {

        return versionMapper.selectLatest();
    }

    @Override
    public Retjson insertVersion(Version version) {
        try{
            versionMapper.insert(version);
        }catch (Exception e){
            e.printStackTrace();
            return Retjson.fail("上传文件失败！");
        }
        return new Retjson();
    }

    @Override
    public Retjson updateVersion(Version version) {
        try{
            versionMapper.updateVersion(version);
        }catch (Exception e){
            e.printStackTrace();
            return Retjson.fail("上传文件失败！");
        }
        return new Retjson();
    }
}
