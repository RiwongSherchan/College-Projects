package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeController {

	@FXML
	public void userRegistration(ActionEvent event) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/CandidateRegistration.fxml"));
		try {
			Parent root = loader.load();

			CandidateRegistrationController controller = loader.getController();

			Scene scene = new Scene(root, 600, 400);

			Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

			stage.setScene(scene);
			stage.setTitle("Candidate Registration");
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			// Handle the exception appropriately
		}
	}

	@FXML
	public void adminLogin(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/AdminLogin.fxml"));

		Parent root = null;
		try {
			root = loader.load();

			// Get the controller instance and initialize it if needed
			AdminLoginController controller = loader.getController();
			// controller.initialize(); // You can modify this method name as per your need

			Stage stage = new Stage();
			stage.setTitle("Admin Login");
			stage.setScene(new Scene(root));
			stage.show();

			// Close the current stage if needed

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void userLogin(ActionEvent event) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/CandidateLogin.fxml"));

		Parent root = null;
		try {
			root = loader.load();

			// Get the controller instance and initialize it if needed
			CandidateLoginController controller = loader.getController();
			// controller.initialize(); // You can modify this method name as per your need

			Stage stage = new Stage();
			stage.setTitle("User Login");
			stage.setScene(new Scene(root));
			stage.show();

			// Close the current stage if needed

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
