package com.gunjan.pagepulse.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class AuditResponse {

    private String requestId;
    private String url;
    private int statusCode;
    private long responseTime;
    private String title;
    private String metaDescription;
    private int h1Count;
    private int seoScore;
    private boolean https;
    private boolean cached;
    private Instant timestamp;
}