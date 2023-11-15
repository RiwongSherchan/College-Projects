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
		// Implement admin login logic
	}

	@FXML
	public void userLogin(ActionEvent event) {
		// Implement user login logic
	}
}
