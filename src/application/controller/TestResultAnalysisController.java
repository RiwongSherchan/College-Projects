package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestResultAnalysisController {

    @FXML
    private Label testResultsLabel;

    // Method to initialize the test result analysis scene
    public void initialize() {
        // Fetch and display test results
        String testResults = fetchTestResults();
        testResultsLabel.setText(testResults);
    }

    // Method to fetch test results from test_result.txt
    private String fetchTestResults() {
        StringBuilder resultText = new StringBuilder("Test Results Analysis:\n");

        try (BufferedReader reader = new BufferedReader(new FileReader("test_result.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] components = line.split(",");
                if (components.length >= 2) {
                    resultText.append("Email: ").append(components[0]).append(", Score: ").append(components[1]).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultText.toString();
    }
}
