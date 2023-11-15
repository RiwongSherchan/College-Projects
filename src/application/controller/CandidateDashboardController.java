package application.controller;

import java.io.IOException;

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
	private Label candidateDetailsLabel;

	// Method to set candidate details
	public void setCandidateDetails(String details) {
		candidateDetailsLabel.setText(details);
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
			controller.initialize(); // You can modify this method name as per your need
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
		// Implement logic to logout (e.g., return to the login scene)
	}
}
