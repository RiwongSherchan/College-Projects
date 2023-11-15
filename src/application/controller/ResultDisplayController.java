// ResultDisplayController.java

package application.controller;

import java.io.IOException;

import application.model.Candidate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ResultDisplayController {

    @FXML
    private Label resultLabel;

    @FXML
    private Button returnButton;
    

	@FXML
	private Label firstNameLabel;

	@FXML
	private Label genderLabel;

	@FXML
	private Label countryLabel;
    
    
	private Candidate candidate;

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

    private String result;

    public void initialize() {
    	
    	if (candidate != null) {
			firstNameLabel.setText("First Name: " + candidate.getFirstName());
			genderLabel.setText("Gender: " + candidate.getGender());
			countryLabel.setText("Country: " + candidate.getCountry());
		}
        // Set the resultLabel text with the result
        resultLabel.setText(result);
    }

    public void setResult(String result) {
        this.result = result;
    }

    @FXML
    private void returnButtonClicked(ActionEvent event) {

		System.out.println("check 1");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/CandidateDashboard.fxml"));
		System.out.println("check 3");
		Parent root = null;
		try {
			root = loader.load();
			System.out.println("check 4");
			// Get the controller instance and initialize it if needed
			CandidateDashboardController controller = loader.getController();
			controller.setCandidate(candidate);
			controller.initialize(); // You can modify this method name as per your need
			
			
			
			// Close the current stage
	        Stage currentStage = (Stage) returnButton.getScene().getWindow();
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
}
