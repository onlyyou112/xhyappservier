package com.xhy.xhyappserver.interfaceController;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.xhy.xhyappserver.entries.Version;
import com.xhy.xhyappserver.service.VersionService;
import com.xhy.xhyappserver.util.ResourcePathUtil;
import com.xhy.xhyappserver.util.Retjson;
import com.xhy.xhyappserver.util.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UploadPackageController {

@Autowired
    ResourcePathUtil resourcePathUtil;
@Value("${downloadUpdateUrl}")
private String downLoadFileUril;

@Autowired
    VersionService versionService;
@RequestMapping(value = {"/","/packageUpload",""})
    public String index(HttpServletRequest request){
    Version latestVersion = versionService.getLatestVersion();
    if(latestVersion==null){
        latestVersion=new Version();
        latestVersion.setVersionNum("0.0.0");
    }
    request.setAttribute("version",latestVersion);
    return "/admin/packageUpload.html";
}
@RequestMapping("/uploadsubmit")
@ResponseBody
    public Retjson uploadsubmit(HttpServletResponse response, Version version, String token,MultipartFile file)throws Exception{

    response.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    if(StringUtils.isEmpty(token)|| !"new1292861831".equals(token)){
        return Retjson.fail("对不起，密令不正确或为空！");
    }

    System.out.println("上传路径为"+resourcePathUtil.getRootPath());

    if(StringUtils.isEmpty(version.getVersionNum())){
        return Retjson.fail("对不起，版本号为空！");
    }
    Map map = checkVersion(version.getVersionNum());
    if(!(Boolean) map.get("compareResult")){
        return Retjson.fail("对不起，版本不得低于或等于上次版本号，旧版本号为"+map.get("oldVersion"));
    }


    if(file==null){
        return Retjson.fail("对不起，文件为空！");
    }
    if(StringUtils.isEmpty(version.getUpdateMessage())){
        return Retjson.fail("对不起，升级信息必须填写！");
    }
    if(StringUtils.isEmpty(version.getForceUpdate())){
        return Retjson.fail("对不起，强制升级选项不能为空！");
    }
    long size = file.getSize();
    if(StringUtils.isEmpty(resourcePathUtil.getRootPath())){
        return Retjson.fail("系统异常，请稍后重试！");
    }
    try {
        File uploadFile = new File(resourcePathUtil.getRootPath() + "update.apk");
        if (uploadFile.exists()) {
            uploadFile.delete();
            uploadFile.createNewFile();
        }
        if(!uploadFile.getParentFile().exists()){
           if(!uploadFile.getParentFile().mkdirs()){
               return Retjson.fail("系统异常，上传失败，系统未创建相关路径，请稍后重试！");
            }
        }
        file.transferTo(uploadFile);
        if(!(uploadFile.length()==size)){
            return Retjson.fail("系统异常，上传失败，请稍后重试！");

        }
    }catch(Exception e){
    return Retjson.fail("系统异常，上传失败，请稍后重试！");
    }
    version.setId(UUIDUtil.getUUID());
    version.setIslatest("1");
    version.setDownloadUrl(StringUtils.isEmpty(downLoadFileUril)?"/getUpdateFile":downLoadFileUril);
    Version latestVersion = versionService.getLatestVersion();
    Retjson retjson;
    if(latestVersion!=null){
        retjson = versionService.updateVersion(version);

    }else{
       retjson = versionService.insertVersion(version);
    }
    if(!retjson.getStatus().equals("success")){
        response.getWriter().write("<h1></h1>");
        return Retjson.fail("上传失败，稍后重试！");
    }
    return new Retjson().setMsg("上传成功！！");
}

    private Map checkVersion(String versionNum) {
        return versionService.checkVersion(versionNum);
    }

    @RequestMapping("/getUpdateFile")
    public void downFile(HttpServletResponse response)throws Exception{
    File file = new File(resourcePathUtil.getRootPath() + "update.apk");
    if(!file.exists()){
        response.getWriter().write("<h1>文件不存在！</h1>");
        return;
    }

    response.setContentType("application/vnd.android.package-archive");
    /*response.setHeader("content-type", "application/octet-stream");*/ //三种1
    /*response.setContentType("application/octet-stream");  *///三种而
    //response.setContentType("application/x-download");//设置response内容的类型 普通下载类型三种之3
   /*response.setContentLength((int) file.length());*/
    response.setContentLengthLong(file.length());
    response.setHeader("Accept-Encoding","identity");
   /* response.setHeader("Content-Disposition", "attachment;filename="+new String("update.apk".getBytes("utf-8"),"ISO-8859-1"));*/
    response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(file.getName(), "UTF-8"));


    try(
    InputStream inputStream=new FileInputStream(file);
    BufferedInputStream bf=new BufferedInputStream(inputStream);
     ServletOutputStream outputStream = response.getOutputStream();
    BufferedOutputStream bfOut=new BufferedOutputStream(outputStream)){
        int length;
        while((length=bf.read())!=-1){
            bfOut.write(length);
        }
    }


}
@RequestMapping("/updateInfo")
@ResponseBody
    public Version updateInfo(){
Map<String,String> returnMap=new HashMap<>();


    Version latestVersion = versionService.getLatestVersion();
   /* returnMap.put("versionNum",latestVersion.getVersionNum());
    returnMap.put("downloadUrl",latestVersion.getDownloadUrl());
    returnMap.put("updateMessage",latestVersion.getUpdateMessage());
    returnMap.put("forceUpdate",latestVersion.getIslatest());*/

    return latestVersion;
}


}
