package com.fakenews.detector_api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        String status,
        String message,
        List<String> details,
        LocalDateTime timestamp
) {}