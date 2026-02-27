package com.fakenews.detector_api.service;

import com.fakenews.detector_api.dto.PredictionResponse;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Service
public class PythonIntegrationService {

    public PredictionResponse analyzeText(String text) {
        long startTime = System.currentTimeMillis();

        try {
            // Target the python script. Ensure paths align with your repo structure.
            ProcessBuilder processBuilder = new ProcessBuilder("python", "ml-python/predict_stdin.py");
            Process process = processBuilder.start();

            // Pass the large text via Standard Input to avoid OS command line limits
            try (OutputStream os = process.getOutputStream()) {
                os.write(text.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            // Read the Python script's output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine();
            process.waitFor();

            if (output == null || process.exitValue() != 0) {
                throw new RuntimeException("Python script failed to execute properly.");
            }

            // Parse the output (Assuming python prints "FAKE,0.92")
            String[] parts = output.split(",");
            String label = parts[0];
            double confidence = Double.parseDouble(parts[1]);

            long processingTime = System.currentTimeMillis() - startTime;

            return new PredictionResponse(label, confidence, processingTime);

        } catch (Exception e) {
            throw new RuntimeException("Failed to communicate with ML model: " + e.getMessage());
        }
    }
}