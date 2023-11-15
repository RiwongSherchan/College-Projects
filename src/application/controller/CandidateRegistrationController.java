package application.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.model.Candidate;
import application.util.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CandidateRegistrationController {

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;

	@FXML
	private TextField emailField;

	@FXML
	private RadioButton maleRadioButton;

	@FXML
	private RadioButton femaleRadioButton;

	@FXML
	private RadioButton thailandRadioButton;

	@FXML
	private RadioButton malaysiaRadioButton;

	@FXML
	private RadioButton singaporeRadioButton;

	@FXML
	private TextField contactNumberField;

	@FXML
	private Button registerButton;

	private List<Candidate> candidates = new ArrayList<>();

	@FXML
	public void registerButtonClicked(ActionEvent event) {
		// Retrieve values from the form
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String email = emailField.getText();
		String gender = maleRadioButton.isSelected() ? "Male" : "Female";
		String country = thailandRadioButton.isSelected() ? "Thailand"
				: malaysiaRadioButton.isSelected() ? "Malaysia" : "Singapore";
		String contactNumber = contactNumberField.getText();

		System.out.println("First Name: " + firstName);
		System.out.println("Last Name: " + lastName);
		System.out.println("Email: " + email);
		System.out.println("Gender: " + gender);
		System.out.println("Country: " + country);
		System.out.println("Contact Number: " + contactNumber);

		Candidate NewCandidate = new Candidate(firstName, lastName, email, gender, country, contactNumber);
		candidates.add(NewCandidate);

		saveToTextFile(firstName, lastName, email, gender, country, contactNumber);

		Stage stage = (Stage) registerButton.getScene().getWindow();
		stage.close();

		openCandidateDashboard();
	}

	private void openCandidateDashboard() {
		SceneLoader.loadScene("/application/fxml/CandidateDashboard.fxml", "Candidate Dashboard");

	}

	private void saveToTextFile(String firstName, String lastName, String email, String gender, String country,
			String contactNumber) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("Candidate.txt", true))) {
			// Append the details in comma-separated format
			writer.write(firstName + "," + lastName + "," + email + "," + gender + "," + country + "," + contactNumber
					+ "\n");
			System.out.println("Details saved to candidates.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
