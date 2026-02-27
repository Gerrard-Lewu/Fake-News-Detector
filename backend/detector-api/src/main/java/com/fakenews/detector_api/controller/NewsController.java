package com.fakenews.detector_api.controller;

import com.fakenews.detector_api.dto.PredictionRequest;
import com.fakenews.detector_api.dto.PredictionResponse;
import com.fakenews.detector_api.service.PythonIntegrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
@CrossOrigin(origins = "*") // Allows any frontend to call this API
public class NewsController {

    private final PythonIntegrationService pythonIntegrationService;

    public NewsController(PythonIntegrationService pythonIntegrationService) {
        this.pythonIntegrationService = pythonIntegrationService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<PredictionResponse> analyzeNews(@Valid @RequestBody PredictionRequest request) {
        PredictionResponse response = pythonIntegrationService.analyzeText(request.text());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API is running and ready for predictions.");
    }
}