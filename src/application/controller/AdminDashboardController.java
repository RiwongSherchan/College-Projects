package application.controller;

import application.model.Candidate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

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
	private Label scoreLabel;

	@FXML
	private Label selectedAnswersLabel;

	@FXML
	private Label correctAnswersLabel;
	
	@FXML
	private Label QuestionLabel;

	@FXML
	private ComboBox<Candidate> candidatesDropdown;
	
	@FXML
	private Button LogoutButton;

	public void initialize() {
		// Populate the candidatesDropdown with Candidate objects
		populateCandidatesDropdown();
		clearCandidateDetails();
		displayQuestionNumbers();
	}
	
	private void displayQuestionNumbers() {
	    int totalQuestions = 20;
	    int questionNumber = 1; // Starting question number

	    // Assuming you have labels for each question, you can update them in a loop
	    while (questionNumber <= totalQuestions) {
	        // Create labels for question details
	        Label questionLabel = new Label("Question " + questionNumber + ":");
	        questionLabel.setLayoutX(7.0); // Adjust the layout as needed
	        questionLabel.setLayoutY(78.0 + (questionNumber * 20)); // Adjust the layout as needed

	        // Add the labels to your layout (replace with your specific layout)
	        candidateDetailsPane.getChildren().add(questionLabel);

	        questionNumber++;
	    }
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

		try (BufferedReader reader = new BufferedReader(new FileReader("src/Candidate.txt"))) {
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
	
	private void clearCandidateDetails() {
		nameLabel.setText("Name: ");
		genderLabel.setText("Gender: ");
		countryLabel.setText("Country: ");
		scoreLabel.setText("Score: ");
		selectedAnswersLabel.setText("Selected Answers: ");
		correctAnswersLabel.setText("Correct Answers: ");
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

		// Fetch and display the score, selected answers, and correct answers
		String email = candidate.getEmail();
		int score = getScoreFromTestResults(email);
		List<String> selectedAnswers = getSelectedAnswersFromTestResults(email);
		List<String> correctAnswers = getCorrectAnswers();

		// Display the fetched data
		scoreLabel.setText("Score: " + score);
		selectedAnswersLabel.setText("Selected Answers: \n" + String.join("\n", selectedAnswers));
		correctAnswersLabel.setText("Correct Answers: \n" + String.join("\n", correctAnswers));
	}

	

	private int getScoreFromTestResults(String email) {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/test_result.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String resultEmail = parts[0];
				if (resultEmail.equals(email)) {
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

	private List<String> getSelectedAnswersFromTestResults(String email) {
		List<String> selectedAnswers = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader("src/test_result.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String resultEmail = parts[0];
				if (resultEmail.equals(email)) {
					// Assuming the answers start from index 2 in the result file
					for (int i = 2; i < parts.length; i++) {
						selectedAnswers.add(parts[i]);
					}
					break; // Break once the matching email is found
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			// Handle the exception (e.g., log it, show an error message)
		}

		return selectedAnswers;
	}

	private List<String> getCorrectAnswers() {
		List<String> correctAnswers = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader("src/answer_list.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				correctAnswers.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			// Handle the exception (e.g., log it, show an error message)
		}

		return correctAnswers;
	}

	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
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
