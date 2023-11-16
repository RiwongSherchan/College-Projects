package application.controller;

import application.model.Candidate;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboardController {

	@FXML
	private AnchorPane candidateDetailsPane;

	@FXML
	private Label nameLabel;

	@FXML
	private Label genderLabel;

	@FXML
	private Label countryLabel;
	@FXML
	private ComboBox<Candidate> candidatesDropdown;

	public void initialize() {
		// Populate the candidatesDropdown with Candidate objects
		populateCandidatesDropdown();
		clearCandidateDetails();
	}

	private void populateCandidatesDropdown() {
		// Clear existing items
		candidatesDropdown.getItems().clear();

		// Add logic to read candidate data from the data source (e.g., Candidate.txt)
		// and populate the candidatesDropdown with Candidate objects

		// For example:
		List<Candidate> candidates = getCandidatesFromDataSource();
		candidatesDropdown.getItems().addAll(candidates);
	}

	// Dummy method to get a list of Candidate objects (replace with actual logic)
	private List<Candidate> getCandidatesFromDataSource() {
		List<Candidate> candidates = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader("Candidate.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String firstName = parts[0];
				String lastName = parts[1];
				String email = parts[2];
				String gender = parts[3];
				String country = parts[4];
				String password = parts[5];
				String contactNumber = parts[6];

				Candidate candidate = new Candidate(firstName, lastName, email, gender, country, password,
						contactNumber);
				candidates.add(candidate);
			}
		} catch (IOException e) {
			e.printStackTrace();
			// Handle the exception (e.g., log it, show an error message)
		}

		return candidates;
	}

	@FXML
	private void openTestResultAnalysis() {
		// Retrieve the selected candidate from the dropdown
		Candidate selectedCandidate = candidatesDropdown.getSelectionModel().getSelectedItem();

		if (selectedCandidate == null) {
			// Show an alert if no candidate is selected
			showAlert("Please select a candidate.");
		} else {
			// Open the test result analysis window for the selected candidate
			displayCandidateDetails(selectedCandidate);
		}
	}

	private void displayCandidateDetails(Candidate candidate) {
		nameLabel.setText("Name: " + candidate.getFirstName() + " " + candidate.getLastName());
		genderLabel.setText("Gender: " + candidate.getGender());
		countryLabel.setText("Country: " + candidate.getCountry());
	}

	private void clearCandidateDetails() {
		nameLabel.setText("Name: ");
		genderLabel.setText("Gender: ");
		countryLabel.setText("Country: ");
	}


	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
