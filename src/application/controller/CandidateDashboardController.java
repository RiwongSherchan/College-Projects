package application.controller;

import application.util.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CandidateDashboardController {

    @FXML
    private Label candidateDetailsLabel;

    // Method to set candidate details
    public void setCandidateDetails(String details) {
        candidateDetailsLabel.setText(details);
    }

    // Method to handle the "Examination" button click
    @FXML
    private void openExamination(ActionEvent event) {
    	SceneLoader.loadScene("/application/fxml/ExaminationForm.fxml", "Masathai Exam");
    }

    // Method to handle the "Profile Settings" button click
    @FXML
    private void openProfileSettings(ActionEvent event) {
        // Implement logic to open the profile settings scene
    }

    // Method to handle the "Test Results" button click
    @FXML
    private void openTestResults(ActionEvent event) {
        // Implement logic to open the test results scene
    }

    // Method to handle the "Logout" button click
    @FXML
    private void logout(ActionEvent event) {
        // Implement logic to logout (e.g., return to the login scene)
    }
}
