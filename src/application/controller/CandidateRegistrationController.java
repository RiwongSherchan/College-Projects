package application.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.Period;

import application.model.Candidate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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

	@FXML
	private Button CancelButton;

	@FXML
	private TextField passwordField;

	@FXML
	private DatePicker datePicker;

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
		String password = passwordField.getText();
		String contactNumber = contactNumberField.getText();

		System.out.println("First Name: " + firstName);
		System.out.println("Last Name: " + lastName);
		System.out.println("Email: " + email);
		System.out.println("Gender: " + gender);
		System.out.println("Country: " + country);
		System.out.println("Contact Number: " + contactNumber);

		LocalDate dob = datePicker.getValue();

		LocalDate currentDate = LocalDate.now();

		// Calculate the period between the date of birth and the current date
		Period period = Period.between(dob, currentDate);

		// Extract the years from the period
		int age_number = period.getYears();
		String age = String.valueOf(age_number);

		// Use the age in years as needed in your registration logic
		System.out.println("Age: " + age);

		Candidate NewCandidate = new Candidate(firstName, lastName, email, gender, country, password, contactNumber,
				age);
		candidates.add(NewCandidate);

		saveToTextFile(firstName, lastName, email, gender, country, password, contactNumber,age);

		Stage stage = (Stage) registerButton.getScene().getWindow();
		stage.close();

		openCandidateDashboard(NewCandidate);
	}

	private void openCandidateDashboard(Candidate candidate) {

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

	@FXML
	private void cancelButtonClicked(ActionEvent event) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/home.fxml"));

		Parent root = null;
		try {
			root = loader.load();

			// Get the controller instance and initialize it if needed
			HomeController controller = loader.getController();
			// controller.initialize(); // You can modify this method name as per your need
			Stage CloseCurrentStage = (Stage) CancelButton.getScene().getWindow();
			CloseCurrentStage.close();
			Stage stage = new Stage();
			stage.setTitle("home");
			stage.setScene(new Scene(root));
			stage.show();

			// Close the current stage if needed

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void saveToTextFile(String firstName, String lastName, String email, String gender, String country,
			String password, String contactNumber, String age) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Candidate.txt", true))) {
			// Append the details in comma-separated format
			writer.write(firstName + "," + lastName + "," + email + "," + gender + "," + country + "," + password + ","
					+ contactNumber + "," + age + "\n");
			System.out.println("Details saved to candidates.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
