package com.gunjan.pagepulse.controller;

import com.gunjan.pagepulse.dto.AuditRequest;
import com.gunjan.pagepulse.dto.AuditResponse;
import com.gunjan.pagepulse.service.AuditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @PostMapping
    public ResponseEntity<AuditResponse> auditWebsite(
            @Valid @RequestBody AuditRequest request) {

        return ResponseEntity.ok(
                auditService.auditWebsite(request)
        );
    }
}