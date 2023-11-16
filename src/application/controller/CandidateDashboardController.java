package application.controller;

import java.io.BufferedReader;
import java.io.FileReader;
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

	@FXML
	private Button ViewTest;

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

	@FXML
	private void ViewTestReport(ActionEvent event) {

		// Fetch and pass the score to ResultDisplayController
		int score = fetchScoreFromTestResults();
		double percentage = (double) score / 20 * 100;

		// Calculate total correct out of total questions
		String result = String.format("You scored %d out of %d. Your percentage is %.2f%%", score, 20,
				percentage);

		// Pass the result to the ResultDisplayController

		// Print or process the user's score
		System.out.println("User's Score: " + score);

		openResultDisplay(result, score);

	}

	private void openResultDisplay(String result, int score) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/ResultDisplay.fxml"));
			Parent root = loader.load();

			// Get the controller instance
			ResultDisplayController controller = loader.getController();

			// Pass the result to the controller
			controller.setResult(result);
			controller.setCandidate(candidate);
			controller.setscore(score);

			// Initialize the controller
			controller.initialize();
			
			
			// Close the current stage
	        Stage currentStage = (Stage) ViewTest.getScene().getWindow();
	        currentStage.close();
	        

			Stage stage = new Stage();
			stage.setTitle("Result Display");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int fetchScoreFromTestResults() {
		try (BufferedReader reader = new BufferedReader(new FileReader("test_result.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String resultEmail = parts[0];
				System.out.println(resultEmail);
				System.out.println(candidate.getEmail());
				if (resultEmail.equals(candidate.getEmail())) {
					// Assuming the score is at index 1 in the result file
					return Integer.parseInt(parts[1]);
				}
			}
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
			// Handle the exception (e.g., log it, show an error message)
		}
		// Return a default score if no matching email is found
		return 0;
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
