package com.xhy.xhyappserver.service.serviceimpl;

import com.xhy.xhyappserver.entries.Version;
import com.xhy.xhyappserver.mapping.VersionMapper;
import com.xhy.xhyappserver.service.VersionService;
import com.xhy.xhyappserver.util.Retjson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class VersionServiceImpl implements VersionService {
    @Resource
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

    @Override
    public Map checkVersion(String versionNum) {
        Map returnMap=new HashMap();
        Version version = versionMapper.selectLatest();

      if(version==null||compareVersion(version.getVersionNum(),versionNum)){
          returnMap.put("compareResult",true);
      }else{
          returnMap.put("compareResult",false);
          returnMap.put("oldVersion",version.getVersionNum());

      }


        return returnMap;
    }

    /**
     * 比较版本号 当新版本大于旧版本，返回true，小于等于则返回false
     * @param oldVersionNum
     * @param newVersionNum
     * @return
     */

    private boolean compareVersion(String oldVersionNum,String newVersionNum){
         return newVersionNum.compareTo(oldVersionNum)>0;
    }
}
