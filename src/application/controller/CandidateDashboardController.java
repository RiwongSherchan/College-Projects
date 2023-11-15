package application.controller;

import java.io.IOException;

import application.model.Candidate;
import application.util.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CandidateDashboardController {

	@FXML
	private Label firstNameLabel;

	@FXML
	private Label lastNameLabel;

	@FXML
	private Label genderLabel;
	
	@FXML
	private Button openExamination;
	
	@FXML
	private Button LogoutButton;

	private Candidate candidate;

	public void initialize() {
		// Initialize the UI with candidate information
		if (candidate != null) {
			firstNameLabel.setText("First Name: " + candidate.getFirstName());
			lastNameLabel.setText("Last Name: " + candidate.getLastName());
			genderLabel.setText("Gender: " + candidate.getGender());
		}
	}

	// Add a setter method to set the Candidate object
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	// Method to handle the "Profile Settings" button click
	@FXML
	private void openProfileSettings(ActionEvent event) {
		// Implement logic to open the profile settings scene
	}

	@FXML
	private void openExamination(ActionEvent event) {

		System.out.println("check 1");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/ExaminationForm.fxml"));
		System.out.println("check 3");
		Parent root = null;
		try {
			root = loader.load();
			System.out.println("check 4");
			// Get the controller instance and initialize it if needed
			ExaminationFormController controller = loader.getController();
			controller.setCandidate(candidate);
			controller.initialize(); // You can modify this method name as per your need
			
			
			// Close the current stage
	        Stage currentStage = (Stage) openExamination.getScene().getWindow();
	        currentStage.close();
	        
			System.out.println("check 2 CDC");
			Stage stage = new Stage();
			stage.setTitle("Masathai Exam");
			stage.setScene(new Scene(root));
			stage.show();
			
			

			// Close the current stage if needed

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to handle the "Test Results" button click
	@FXML
	private void openTestResults(ActionEvent event) {
		// Implement logic to open the test results scene
	}

	// Method to handle the "Logout" button click
	@FXML
	private void logout(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/home.fxml"));

		Parent root = null;
		try {
			root = loader.load();

			// Get the controller instance and initialize it if needed
			HomeController controller = loader.getController();
			// controller.initialize(); // You can modify this method name as per your need
			Stage CloseCurrentStage = (Stage) LogoutButton.getScene().getWindow();
			CloseCurrentStage.close();
			Stage stage = new Stage();
			stage.setTitle("home");
			stage.setScene(new Scene(root));
			stage.show();

			// Close the current stage if needed

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
