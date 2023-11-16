// ExaminationFormController.java
package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


import application.model.Candidate;

public class ExaminationFormController {

	@FXML
	private Label questionNumberLabel;

	@FXML
	private RadioButton optionARadioButton;

	@FXML
	private RadioButton optionBRadioButton;

	@FXML
	private RadioButton optionCRadioButton;

	@FXML
	private RadioButton optionDRadioButton;
	
	@FXML
	private ImageView countryFlagImageView;

	@FXML
	private Button nextButton;

	@FXML
	private Button backButton;

	@FXML
	private Label timerLabel;

	@FXML
	private TextArea resultTextArea;

	@FXML
	private ToggleGroup toggleGroup;

	@FXML
	private Label firstNameLabel;

	@FXML
	private Label genderLabel;

	@FXML
	private Label countryLabel;

	@FXML
	private Button submitButton;

	@FXML
	private Label resultLabel;
	
	@FXML
	private Label currentQuestionLabel;

	private List<String> selectedOptions = new ArrayList<>();
	private List<String> questionsWithOptions = new ArrayList<>();
	private List<String> correctAnswers = new ArrayList<>();
	private int currentQuestionIndex = 0;
	private int secondsRemaining = 300; // 5 minutes
	private Timeline timer;

	private Candidate candidate;

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public void initialize() {
		loadQuestionsWithOptions();
		updateQuestion();
		loadCorrectAnswers();
		initializeTimer();
		 updateCurrentQuestionLabel();

		if (candidate != null) {
			firstNameLabel.setText("First Name: " + candidate.getFirstName());
			genderLabel.setText("Gender: " + candidate.getGender());
			countryLabel.setText("Country: " + candidate.getCountry());
			updateCountryFlagImage(candidate);
		}

		toggleGroup = new ToggleGroup();
		optionARadioButton.setToggleGroup(toggleGroup);
		optionBRadioButton.setToggleGroup(toggleGroup);
		optionCRadioButton.setToggleGroup(toggleGroup);
		optionDRadioButton.setToggleGroup(toggleGroup);

		// Initially, set the Submit button to be invisible
		submitButton.setVisible(false);

	}
	
	private void updateCountryFlagImage(Candidate candidate) {
        System.out.println(candidate.getCountry());
		String country = candidate.getCountry().toLowerCase();
		String imageUrl = String.format("/application/resources/fxml_images/%sflag.jpg", country);
		Image image = new Image(getClass().getResourceAsStream(imageUrl));
		countryFlagImageView.setImage(image);

	}
	
	private void updateCurrentQuestionLabel() {
	    currentQuestionLabel.setText("Current Question: " + (currentQuestionIndex + 1));
	}
	
	private void initializeTimer() {
	    timer = new Timeline(
	            new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
	                @Override
	                public void handle(ActionEvent event) {
	                    updateTimerLabel();
	                    if (secondsRemaining <= 0) {
	                        timer.stop();
	                        handleTimerFinish();
	                    } else {
	                        secondsRemaining--;
	                    }
	                }
	            })
	    );

	    timer.setCycleCount(Timeline.INDEFINITE);
	    timer.play();
	}

	private void updateTimerLabel() {
	    int minutes = secondsRemaining / 60;
	    int seconds = secondsRemaining % 60;
	    timerLabel.setText(String.format("Time Remaining: %02d:%02d", minutes, seconds));
	}

	private void handleTimerFinish() {
	    // Perform actions when the timer finishes (e.g., submit the form)
	    submitButtonClicked();
	}

	private void loadQuestionsWithOptions() {
		try {
			InputStream inputStream = getClass().getResourceAsStream("/application/controller/question_list.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String line;
			while ((line = reader.readLine()) != null) {
				questionsWithOptions.add(line);
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace(); // Handle the exception appropriately in a real application
		}
	}

	private void updateQuestion() {
		if (currentQuestionIndex < questionsWithOptions.size()) {
			String[] parts = questionsWithOptions.get(currentQuestionIndex).split(":");
			questionNumberLabel.setText(parts[0]);
			optionARadioButton.setText(parts[1]);
			optionBRadioButton.setText(parts[2]);
			optionCRadioButton.setText(parts[3]);
			optionDRadioButton.setText(parts[4]);

			if (currentQuestionIndex == 20) {
				submitButton.setVisible(true);

			}
		}
	}

	@FXML
	private void nextButtonClicked() {
		// Get the selected option for the current question and add it to the list
		RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
		if (selectedRadioButton != null) {
			String selectedOption = selectedRadioButton.getText();
			selectedOptions.add(selectedOption);
		} else {
			// Handle the case where no option is selected, if needed
		}

		// Move to the next question and update the UI
		currentQuestionIndex++;

		// Disable the "Next" button after reaching question number 20
		if (currentQuestionIndex >= 20) {
			nextButton.setDisable(true);
		}

		updateQuestion();
		 updateCurrentQuestionLabel();
	}

	@FXML
	private void backButtonClicked() {
		// Move back to the previous question and update the UI
		if (currentQuestionIndex > 0) {
			currentQuestionIndex--;

			// Enable the "Next" button when going back
			nextButton.setDisable(false);

			updateQuestion();
			 updateCurrentQuestionLabel();

			// Remove the last selected option when going back
			if (!selectedOptions.isEmpty()) {
				selectedOptions.remove(selectedOptions.size() - 1);
			}
		}
	}

	@FXML
	private void submitButtonClicked() {
		// Calculate the score
		int score = 0;
		int totalQuestions = currentQuestionIndex;

		for (int i = 0; i < totalQuestions; i++) {
			String userAnswer = selectedOptions.get(i);
			String correctAnswer = correctAnswers.get(i);

			if (userAnswer.equals(correctAnswer)) {
				score++;
			}
		}

		// Calculate percentage
		double percentage = (double) score / totalQuestions * 100;

		// Calculate total correct out of total questions
		String result = String.format("You scored %d out of %d. Your percentage is %.2f%%", score, totalQuestions,
				percentage);

		// Pass the result to the ResultDisplayController

		// Print or process the user's score
		System.out.println("User's Score: " + score);
		saveTestResult(candidate.getEmail(), score, selectedOptions);
		timer.stop();

		openResultDisplay(result,score);

		// You can add further logic for processing the submitted answers
	}
	
	private void saveTestResult(String userEmail, int score, List<String> selectedOptions) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("test_result.txt", true))) {
	        // Append the details in comma-separated format
	        writer.write(userEmail + "," + score + "," + String.join(",", selectedOptions) + "\n");
	        System.out.println("Test result saved to test_result.txt");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	private void loadCorrectAnswers() {
		try {
			InputStream inputStream = getClass().getResourceAsStream("/application/controller/answer_list.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String line;
			while ((line = reader.readLine()) != null) {
				correctAnswers.add(line);
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace(); // Handle the exception appropriately in a real application
		}
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
	        Stage currentStage = (Stage) submitButton.getScene().getWindow();
	        currentStage.close();
	        

			Stage stage = new Stage();
			stage.setTitle("Result Display");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Existing code...



}
