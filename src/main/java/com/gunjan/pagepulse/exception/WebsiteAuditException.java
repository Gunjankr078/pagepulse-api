package com.gunjan.pagepulse.exception;

public class WebsiteAuditException extends RuntimeException {

    public WebsiteAuditException(String message) {
        super(message);
    }

    public WebsiteAuditException(String message, Throwable cause) {
        super(message, cause);
    }
}