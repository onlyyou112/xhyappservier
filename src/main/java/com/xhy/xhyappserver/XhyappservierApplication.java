package com.xhy.xhyappserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableScheduling
@EnableCaching
/*@MapperScan(basePackages = {"com.xhy.xhyappserver.mapping"})*/
public class XhyappservierApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(XhyappservierApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(XhyappservierApplication.class);
    }
}
