package com.gunjan.pagepulse.service;

import com.gunjan.pagepulse.dto.AuditRequest;
import com.gunjan.pagepulse.dto.AuditResponse;

public interface AuditService {

    AuditResponse auditWebsite(AuditRequest request);

}