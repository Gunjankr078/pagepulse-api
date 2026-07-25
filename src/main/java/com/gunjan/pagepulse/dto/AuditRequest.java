package com.gunjan.pagepulse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditRequest {

    @NotBlank(message = "URL is required")
    @Pattern(
            regexp = "^(https?://).+",
            message = "URL must start with http:// or https://"
    )
    private String url;
}