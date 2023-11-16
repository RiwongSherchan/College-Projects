package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminDashboardController {

    @FXML
    private Label totalCandidatesLabel;

    @FXML
    private ComboBox<String> candidateComboBox;

    private Map<String, String> candidateData = new HashMap<>();

    public void initialize() {
        // Initialize the total number of candidates
        int totalCandidates = loadCandidateData();
        totalCandidatesLabel.setText(String.valueOf(totalCandidates));

        // Populate the ComboBox with candidate names
        ObservableList<String> candidateNames = FXCollections.observableArrayList(candidateData.keySet());
        candidateComboBox.setItems(candidateNames);
    }

    @FXML
    private void openTestResultAnalysis() {
        String selectedCandidate = candidateComboBox.getValue();
        if (selectedCandidate != null) {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/application/fxml/TestResultAnalysis.fxml"));

            Parent root = null;
            try {
                root = loader.load();

                // Get the controller instance and initialize it if needed
                TestResultAnalysisController controller = loader.getController();
                controller.initialize(selectedCandidate, candidateData.get(selectedCandidate));

                Stage stage = new Stage();
                stage.setTitle("Admin Dashboard");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Dummy method to load candidate data from Candidate.txt
    private int loadCandidateData() {
        int totalCandidates = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("Candidate.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    String fullName = parts[0] + " " + parts[1];
                    String email = parts[2];
                    String gender = parts[3];
                    String country = parts[4];

                    candidateData.put(fullName, email + "," + gender + "," + country);
                    totalCandidates++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalCandidates;
    }
}
