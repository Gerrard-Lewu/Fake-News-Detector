package com.fakenews.detector_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PredictionRequest(
        @NotBlank(message = "Article text cannot be empty.")
        @Size(min = 100, max = 20000, message = "Article must be between 100 and 20,000 characters.")
        String text
) {}
