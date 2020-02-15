package com.xhy.xhyappserver.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResourcePathUtil {
    @Value("${windows_path}")
    private String window_path;
    @Value("${linux_path}")
    private String linux_path;

    public String getLinux_path() {
        return linux_path;
    }

    public String getWindow_path() {
        return window_path;
    }
    public String getRootPath() {
        return System.getProperty("os.name").toLowerCase().startsWith("win") ? window_path : linux_path;
    }
}
