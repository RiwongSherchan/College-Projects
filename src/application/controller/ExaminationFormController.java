// ExaminationFormController.java
package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
	    private Button nextButton;

	    @FXML
	    private Button backButton;

	    @FXML
	    private Label timerLabel;

	    @FXML
	    private TextArea resultTextArea;

	    @FXML
	    private ToggleGroup toggleGroup;

    private List<String> questionsWithOptions = new ArrayList<>();
    private int currentQuestionIndex = 0;

    public void initialize() {
        loadQuestionsWithOptions();
        updateQuestion();
        toggleGroup = new ToggleGroup();
        optionARadioButton.setToggleGroup(toggleGroup);
        optionBRadioButton.setToggleGroup(toggleGroup);
        optionCRadioButton.setToggleGroup(toggleGroup);
        optionDRadioButton.setToggleGroup(toggleGroup);
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
        }
    }
    
    @FXML
    private void nextButtonClicked() {
        // Your logic for the "Next" button goes here
    }

    @FXML
    private void backButtonClicked() {
        // Your logic for the "Back" button goes here
    }
    
    
    
}
