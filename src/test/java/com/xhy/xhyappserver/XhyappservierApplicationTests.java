package com.xhy.xhyappserver;

import com.xhy.xhyappserver.service.GetUrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XhyappservierApplicationTests {
@Autowired
    GetUrlService getUrlService;
    @Test
    public void contextLoads() {


    }

}
