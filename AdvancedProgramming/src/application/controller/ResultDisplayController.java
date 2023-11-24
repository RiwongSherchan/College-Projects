// ResultDisplayController.java

package application.controller;

import java.io.IOException;

import application.model.Candidate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ResultDisplayController {

    @FXML
    private Label resultLabel;

    @FXML
    private Button returnButton;
    

	@FXML
	private Label firstNameLabel;
	
	@FXML
	private ImageView countryFlagImageView;

	@FXML
	private Label genderLabel;

	@FXML
	private Label countryLabel;
	
	 @FXML
	    private PieChart pieChart;
    
    
	private Candidate candidate;

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

    private String result;
    
    private int score;

    public void initialize() {
    	
    	if (candidate != null) {
			firstNameLabel.setText("First Name: " + candidate.getFirstName());
			genderLabel.setText("Gender: " + candidate.getGender());
			countryLabel.setText("Country: " + candidate.getCountry());
			updateCountryFlagImage(candidate);
		}
    	 updatePieChart();
        // Set the resultLabel text with the result
        resultLabel.setText(result);
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    public void setscore(int score) {
        this.score = score;
    }
    
    private void updateCountryFlagImage(Candidate candidate) {
        System.out.println(candidate.getCountry());
		String country = candidate.getCountry().toLowerCase();
		String imageUrl = String.format("/application/resources/fxml_images/%sflag.jpg", country);
		Image image = new Image(getClass().getResourceAsStream(imageUrl));
		countryFlagImageView.setImage(image);

	}
    
    private void updatePieChart() {
        // Extract correct answers and total questions from the result
        int correctAnswers = score;
        int totalQuestions = 20;

        // Create pie chart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Correct Answers", correctAnswers),
                new PieChart.Data("Incorrect Answers", totalQuestions - correctAnswers)
        );

        // Set the pie chart data
        pieChart.setData(pieChartData);
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
