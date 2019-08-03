package com.xhy.xhyappserver.interfaceController;

import com.xhy.xhyappserver.entries.Version;
import com.xhy.xhyappserver.service.VersionService;
import com.xhy.xhyappserver.util.Retjson;
import com.xhy.xhyappserver.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UploadPackageController {

@Value("${windows_path}")
private String uploadPath;
@Value("${downloadUpdateUrl}")
private String downLoadFileUril;
@Autowired
    VersionService versionService;
@RequestMapping(value = {"/","/packageUpload",""})
    public String index(){
    return "/admin/packageUpload.html";
}
@RequestMapping("/uploadsubmit")
    public void uploadsubmit(HttpServletResponse response, Version version, MultipartFile file)throws Exception{
    System.out.println("上传路径为"+uploadPath);
    response.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    if(StringUtils.isEmpty(version.getVersionNum())){
    response.getWriter().write("对不起，版本号为空！");
    return;
    }
    if(file==null){
        response.getWriter().write("对不起，文件为空！");
        return;
    }
    long size = file.getSize();
    if(StringUtils.isEmpty(uploadPath)){
        response.getWriter().write("系统异常，请稍后重试！");
        return;
    }
    try {
        File uploadFile = new File(uploadPath + "update.apk");
        if (uploadFile.exists()) {
            uploadFile.delete();
            uploadFile.createNewFile();
        }
        file.transferTo(uploadFile);
        if(!(uploadFile.length()==size)){
            response.getWriter().write("<h1>系统异常，上传失败，请稍后重试！</h1>");
            return;
        }
    }catch(Exception e){
    response.getWriter().write("<h1>系统异常，上传失败，请稍后重试！</h1>");
    return;
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
        response.getWriter().write("<h1>上传失败，稍后重试！</h1>");
        return;
    }
    response.getWriter().write("<h1>上传成功！！</h1>");

}
@RequestMapping("/getUpdateFile")
    public void downFile(HttpServletResponse response)throws Exception{
    File file = new File(uploadPath + "update.apk");
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
    public Map<String,String> updateInfo(){
    Version latestVersion = versionService.getLatestVersion();
    HashMap<String, String> returnMap = new HashMap<>();
    returnMap.put("versionNum",latestVersion.getVersionNum());
    returnMap.put("downloadUrl",latestVersion.getDownloadUrl());
    return returnMap;
}


}