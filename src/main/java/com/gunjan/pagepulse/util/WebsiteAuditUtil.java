package com.gunjan.pagepulse.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
public class WebsiteAuditUtil {

    public Connection.Response fetchResponse(String url) throws Exception {

        return Jsoup.connect(url)
                .userAgent("PagePulseBot/1.0")
                .timeout(10000)
                .followRedirects(true)
                .execute();
    }
}