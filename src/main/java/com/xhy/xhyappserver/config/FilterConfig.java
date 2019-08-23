package com.xhy.xhyappserver.config;

import com.xhy.xhyappserver.nativefilter.GdzFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class FilterConfig {
    //注册filter
    @Bean
    public FilterRegistrationBean  filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        GdzFilter gdzFilter=new GdzFilter();
        registrationBean.setFilter(gdzFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }


    /**
     *   暂时没用
     */

    //@Bean(name = "directLoginFilter")
    /*public Filter gdzFilter() {
        return new GdzFilter();
    }*/
}

