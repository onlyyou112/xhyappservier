package com.xhy.xhyappserver.entries;

public class Version {
    private String id;

    private String versionNum;

    private String downloadUrl;

    private String islatest;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIslatest() {
        return islatest;
    }

    public void setIslatest(String islatest) {
        this.islatest = islatest;
    }
}