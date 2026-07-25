package com.gunjan.pagepulse.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ErrorResponse {

    private String requestId;
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}