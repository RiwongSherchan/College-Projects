package application.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import application.model.Candidate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CandidateLoginController {

	@FXML
	private TextField emailField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button loginButton;

	@FXML
	private Button cancelButton;

	@FXML
	private Label loginStatusLabel; // New label for displaying login status

	@FXML
	private void loginButtonClicked(ActionEvent event) {
		// Implement your login logic here
		String email = emailField.getText();
		String password = passwordField.getText();

		try (BufferedReader reader = new BufferedReader(new FileReader("Candidate.txt"))) {
			String line;
			boolean authenticationSuccessful = false;
			while ((line = reader.readLine()) != null) {
				String[] components = line.split(",");
				if (components.length >= 6 && email.equals(components[2]) && password.equals(components[5])) {
					System.out.println("User authenticated!");
					authenticationSuccessful = true;
					// Additional logic can be added here, such as opening the dashboard.
					// Authentication successful

					Candidate authenticatedCandidate = new Candidate(components[0], // firstName
							components[1], // lastName
							components[2], // email
							components[3], // gender
							components[4], // country
							components[5], // password
							components[6]); // contactNumber
					FXMLLoader loader = new FXMLLoader(
							getClass().getResource("/application/fxml/CandidateDashboard.fxml"));

					Parent root = null;
					try {
						root = loader.load();

						// Get the controller instance and initialize it if needed
						CandidateDashboardController controller = loader.getController();
						controller.setCandidate(authenticatedCandidate);
						controller.initialize(); // You can modify this method name as per your need
						Stage closeCurrentStage = (Stage) loginButton.getScene().getWindow();
						closeCurrentStage.close();

						Stage stage = new Stage();
						stage.setTitle("Candidate Dashboard");
						stage.setScene(new Scene(root));
						stage.show();

						// Close the current stage if needed

					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}

			if (!authenticationSuccessful) {
				System.out.println("Login failed");
				// Show login failed label
				loginStatusLabel.setText("Login failed. Please check your credentials.");
				loginStatusLabel.setVisible(true);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void cancelButtonClicked(ActionEvent event) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/home.fxml"));

		Parent root = null;
		try {
			root = loader.load();
			System.out.println("check 4");
			// Get the controller instance and initialize it if needed
			HomeController controller = loader.getController();

			// Close the current stage
			Stage currentStage = (Stage) cancelButton.getScene().getWindow();
			currentStage.close();

			Stage stage = new Stage();
			stage.setTitle("Home");
			stage.setScene(new Scene(root));
			stage.show();

			// Close the current stage if needed

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// You can add more methods as needed, such as a method to validate credentials

}
