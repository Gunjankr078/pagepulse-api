package com.gunjan.pagepulse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "✅ PagePulse API is running successfully.";
    }

    @GetMapping("/api/v1/audit")
    public String auditHelp() {
        return """
                This endpoint accepts POST requests only.

                POST /api/v1/audit

                Example JSON:
                {
                  "url": "https://google.com"
                }
                """;
    }
}