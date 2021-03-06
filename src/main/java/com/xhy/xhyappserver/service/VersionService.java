package com.xhy.xhyappserver.service;

import com.xhy.xhyappserver.entries.Version;
import com.xhy.xhyappserver.util.Retjson;

import java.util.Map;

public interface VersionService {
    Version getLatestVersion();
    Retjson insertVersion(Version version);
    Retjson updateVersion(Version version);

    Map checkVersion(String versionNum);
}
