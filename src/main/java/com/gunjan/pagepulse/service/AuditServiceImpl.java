package com.gunjan.pagepulse.service;

import com.gunjan.pagepulse.dto.AuditRequest;
import com.gunjan.pagepulse.dto.AuditResponse;
import com.gunjan.pagepulse.util.SeoScoreUtil;
import com.gunjan.pagepulse.util.WebsiteAuditUtil;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import com.gunjan.pagepulse.exception.InvalidWebsiteException;
import com.gunjan.pagepulse.exception.WebsiteAuditException;
import com.gunjan.pagepulse.exception.WebsiteUnavailableException;
import java.net.UnknownHostException;
import java.net.SocketTimeoutException;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final WebsiteAuditUtil websiteAuditUtil;
    private final SeoScoreUtil seoScoreUtil;
    private final CacheManager cacheManager;

    @Override
    public AuditResponse auditWebsite(AuditRequest request) {

        Cache cache = cacheManager.getCache("websiteAudit");

        // Check cache first
        if (cache != null) {
            AuditResponse cachedResponse = cache.get(request.getUrl(), AuditResponse.class);

            if (cachedResponse != null) {
                return AuditResponse.builder()
                        .requestId(cachedResponse.getRequestId())
                        .url(cachedResponse.getUrl())
                        .statusCode(cachedResponse.getStatusCode())
                        .responseTime(cachedResponse.getResponseTime())
                        .title(cachedResponse.getTitle())
                        .metaDescription(cachedResponse.getMetaDescription())
                        .h1Count(cachedResponse.getH1Count())
                        .seoScore(cachedResponse.getSeoScore())
                        .https(cachedResponse.isHttps())
                        .cached(true)
                        .timestamp(cachedResponse.getTimestamp())
                        .build();
            }
        }

        System.out.println("Audit Service Executed...");

        long startTime = System.currentTimeMillis();

        try {

            Connection.Response jsoupResponse = websiteAuditUtil.fetchResponse(request.getUrl());

            Document document = jsoupResponse.parse();

            long responseTime = System.currentTimeMillis() - startTime;

            String title = document.title();

            String metaDescription = document
                    .select("meta[name=description]")
                    .attr("content");

            int h1Count = document.select("h1").size();

            boolean https = request.getUrl().startsWith("https");

            int seoScore = seoScoreUtil.calculateSeoScore(
                    document,
                    jsoupResponse.statusCode(),
                    https
            );

            AuditResponse auditResponse = AuditResponse.builder()
                    .requestId(UUID.randomUUID().toString())
                    .url(request.getUrl())
                    .statusCode(jsoupResponse.statusCode())
                    .responseTime(responseTime)
                    .title(title)
                    .metaDescription(metaDescription)
                    .h1Count(h1Count)
                    .seoScore(seoScore)
                    .https(https)
                    .cached(false)
                    .timestamp(Instant.now())
                    .build();

            if (cache != null) {
                cache.put(request.getUrl(), auditResponse);
            }

            return auditResponse;

        }catch (UnknownHostException ex) {
            throw new WebsiteUnavailableException("Website is unreachable.");
        }
        catch (SocketTimeoutException ex) {
            throw new WebsiteUnavailableException("Website request timed out.");
        }
        catch (IllegalArgumentException ex) {
           throw new InvalidWebsiteException("Invalid website URL.");
        }
        catch (Exception ex) {
            throw new WebsiteAuditException("Failed to audit website.", ex);
        }
    }
}