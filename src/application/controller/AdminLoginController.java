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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminLoginController {

	@FXML
	private TextField emailField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button LoginButton;

	@FXML
	private Button CancelButton;

	@FXML
	private void loginButtonClicked(ActionEvent event) {
		// Implement your login logic here
		String email = emailField.getText();
		String password = passwordField.getText();

		try (BufferedReader reader = new BufferedReader(new FileReader("Admin.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] components = line.split(",");
				if (components.length >= 2 && email.equals(components[0]) && password.equals(components[1])) {
					System.out.println("User authenticated!");

					FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/AdminDashboard.fxml"));

					Parent root = null;
					try {
						root = loader.load();

						// Get the controller instance and initialize it if needed
						AdminDashboardController controller = loader.getController();
						controller.initialize(); // You can modify this method name as per your need
						Stage CloseCurrentStage = (Stage) LoginButton.getScene().getWindow();
						CloseCurrentStage.close();

						Stage stage = new Stage();
						stage.setTitle("Admin Dashboard");
						stage.setScene(new Scene(root));
						stage.show();

						// Close the current stage if needed

					} catch (IOException e) {
						e.printStackTrace();
					}

				} else {
					System.out.println("failed");
					System.out.println("Login failed");
					showAlert("Login Failed", "Invalid credentials. Please try again.");
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void cancelButtonClicked(ActionEvent event) {
		Stage stage = (Stage) CancelButton.getScene().getWindow();
		stage.close();
	}
	
	
	private void showAlert(String title, String message) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}

	// You can add more methods as needed, such as a method to validate credentials

}
