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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
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
	private ImageView question_image;

	@FXML
	private ImageView optionAImageView;

	@FXML
	private ImageView optionBImageView;

	@FXML
	private ImageView optionCImageView;

	@FXML
	private ImageView optionDImageView;

	@FXML
	private Label currentQuestionLabel;

	private List<String> selectedOptions = new ArrayList<>();
	private List<String> questionsWithOptions = new ArrayList<>();
	private List<String> correctAnswers = new ArrayList<>();
	private int currentQuestionIndex = 0;
	private int secondsRemaining = 240; // 5 minutes
	private Timeline timer;
	private boolean timerFinished = false;
	
	


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

		optionAImageView.setVisible(false);
		optionBImageView.setVisible(false);
		optionCImageView.setVisible(false);
		optionDImageView.setVisible(false);
		question_image.setVisible(false);

		// Initially, set the Submit button to be invisible
		submitButton.setVisible(false);

	}

	private void loadOptionImage(ImageView imageView, String imageName) {
		String imageUrl = String.format("/application/resources/fxml_images/%s", imageName);
		Image image = new Image(getClass().getResourceAsStream(imageUrl));
		imageView.setImage(image);
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
		timer = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				if (secondsRemaining > 0) {
					secondsRemaining = secondsRemaining - 1;
					updateTimerLabel();
				} else if (secondsRemaining == 0 && !timerFinished) {
	                timer.stop();
	                timerFinished = true; // Set the flag to true

	                handleTimerFinish();
				}
				
				if (secondsRemaining == 180) { // 4 minutes remaining
                    playAudioWarning("3M_Remaining.mp3");
                } else if (secondsRemaining == 60) { // 3 minutes remaining
                    playAudioWarning("1M_Remaining.mp3");
                }
			}
		}));

		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();
	}
	
	private void playAudioWarning(String audioFileName) {
	    try {
	        String audioPath = "/application/resources/audio/" + audioFileName;
	        Media media = new Media(getClass().getResource(audioPath).toString());
	        MediaPlayer mediaPlayer = new MediaPlayer(media);
	        mediaPlayer.play();
	    } catch (Exception e) {
	        e.printStackTrace(); // Handle the exception appropriately
	    }
	}

	private void updateTimerLabel() {
		int minutes = secondsRemaining / 60;
		int seconds = secondsRemaining % 60;
		System.out.println(minutes + " : "  + seconds);
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

			if (currentQuestionIndex == 19) {
				submitButton.setVisible(true);

			};
			
			
			


			if (currentQuestionIndex == 14) {
				// Show image views for options
				optionAImageView.setVisible(true);
				optionBImageView.setVisible(true);
				optionCImageView.setVisible(true);
				optionDImageView.setVisible(true);

				// Load and set images for options
				loadOptionImage(optionAImageView, "elephant.jpg");
				loadOptionImage(optionBImageView, "panda.jpg");
				loadOptionImage(optionCImageView, "tiger.jpg");
				loadOptionImage(optionDImageView, "orangutan.jpg");
			} else if (currentQuestionIndex == 6) {
				// Show image views for options
				optionAImageView.setVisible(true);
				optionBImageView.setVisible(true);
				optionCImageView.setVisible(true);
				optionDImageView.setVisible(true);

				// Load and set images for options
				loadOptionImage(optionAImageView, "Petronas Twin Towers.jpg");
				loadOptionImage(optionBImageView, "Marina Bay Sands.jpg");
				loadOptionImage(optionCImageView, "Wat Arun.jpg");
				loadOptionImage(optionDImageView, "kuala lumpur tower.jpg");
			} else if (currentQuestionIndex == 19) {

				question_image.setVisible(true);
				loadOptionImage(question_image, "Nasi Lemak.jpg");
			}

			else {
				// Hide image views for other questions
				optionAImageView.setVisible(false);
				optionBImageView.setVisible(false);
				optionCImageView.setVisible(false);
				optionDImageView.setVisible(false);
				question_image.setVisible(false);
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
	        // Handle the case where no option is selected if needed
	        selectedOptions.add(""); // Add an empty string to indicate no selection
	    }

	    // Move to the next question and update the UI
	    currentQuestionIndex++;

	    // Disable the "Next" button after reaching question number 20
	    if (currentQuestionIndex >= 20) {
	        nextButton.setDisable(true);
	    }

	    updateQuestion();
	    updateCurrentQuestionLabel();

	    // Restore the selected state for the next question
	    restoreSelectedState();
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
			 restoreSelectedState();

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
		

		openResultDisplay(result, score);

		// You can add further logic for processing the submitted answers
	}

	private void saveTestResult(String userEmail, int score, List<String> selectedOptions) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/test_result.txt", true))) {
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
	
	//new
	
	
	private void restoreSelectedState() {
	    // Check if the currentQuestionIndex is within the bounds of selectedOptions
	    if (currentQuestionIndex < selectedOptions.size()) {
	        // Retrieve the selected option for the current question
	        String selectedOption = selectedOptions.get(currentQuestionIndex);

	        // Select the previously selected option if it exists
	        if (!selectedOption.isEmpty()) {
	            toggleGroup.selectToggle(getRadioButtonByText(selectedOption));
	        } else {
	            // If no option is selected, clear the selection
	            toggleGroup.selectToggle(null);
	        }
	    }
	}

	
	private RadioButton getRadioButtonByText(String text) {
	    // Helper method to get the RadioButton by text
	    if (text.equals(optionARadioButton.getText())) {
	        return optionARadioButton;
	    } else if (text.equals(optionBRadioButton.getText())) {
	        return optionBRadioButton;
	    } else if (text.equals(optionCRadioButton.getText())) {
	        return optionCRadioButton;
	    } else if (text.equals(optionDRadioButton.getText())) {
	        return optionDRadioButton;
	    }
	    return null;
	}

	// Existing code...

}
