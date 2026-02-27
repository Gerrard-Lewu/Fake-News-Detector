package com.fakenews.detector_api.dto;

public record PredictionResponse(
        String label,
        double confidence,
        long processingTimeMs
) {}
