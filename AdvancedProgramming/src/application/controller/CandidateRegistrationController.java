package application.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CandidateRegistrationController {

    // JavaFX components defined in the FXML file
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

    // Handler for the "Register" button click
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

        // Validate that none of the fields are blank
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()
                || contactNumber.isBlank() || datePicker.getValue() == null) {
            // Show an alert if any field is blank
            showAlert("Error", "All fields must be filled.");
            return;
        }

        // Check if the email is already taken
        if (isEmailTaken(email)) {
            showAlert("Error", "Email is already taken. Please use a different email.");
            return;
        }

        // Print the retrieved values for debugging
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Gender: " + gender);
        System.out.println("Country: " + country);
        System.out.println("Contact Number: " + contactNumber);

        // Calculate age from the selected date of birth
        LocalDate dob = datePicker.getValue();
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dob, currentDate);
        int age_number = period.getYears();
        String age = String.valueOf(age_number);
        System.out.println("Age: " + age);

        // Create a new Candidate object with the retrieved data
        Candidate newCandidate = new Candidate(firstName, lastName, email, gender, country, password, contactNumber,
                age);
        candidates.add(newCandidate);

        // Save the new candidate to a text file
        saveToTextFile(firstName, lastName, email, gender, country, password, contactNumber, age);

        // Close the current stage and open the Candidate Dashboard
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
        openCandidateDashboard(newCandidate);
    }

    // Check if the given email is already taken
    private boolean isEmailTaken(String email) {
        List<Candidate> existingCandidates = readCandidatesFromFile();
        for (Candidate candidate : existingCandidates) {
            if (candidate.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    // Read existing candidates from the text file
    private List<Candidate> readCandidatesFromFile() {
        List<Candidate> existingCandidates = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Candidate.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // Assuming the order of data is: firstName, lastName, email, gender, country, password, contactNumber, age
                Candidate candidate = new Candidate(data[0], data[1], data[2], data[3], data[4], data[5], data[6],
                        data[7]);
                existingCandidates.add(candidate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existingCandidates;
    }

    // Show an alert with the given title and content
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Open the Candidate Dashboard with the provided candidate data
    private void openCandidateDashboard(Candidate candidate) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/CandidateDashboard.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            CandidateDashboardController controller = loader.getController();
            controller.setCandidate(candidate);
            controller.initialize(); // You can modify this method name as per your need
            Stage stage = new Stage();
            stage.setTitle("Masathai Exam");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handler for the "Cancel" button click
    @FXML
    private void cancelButtonClicked(ActionEvent event) {
        // Load the home.fxml file and open the home stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/home.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            HomeController controller = loader.getController();
            Stage CloseCurrentStage = (Stage) CancelButton.getScene().getWindow();
            CloseCurrentStage.close();
            Stage stage = new Stage();
            stage.setTitle("home");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save candidate details to a text file
    private void saveToTextFile(String firstName, String lastName, String email, String gender, String country,
            String password, String contactNumber, String age) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Candidate.txt", true))) {
            // Append the details in comma-separated format
            writer.write(firstName + "," + lastName + "," + email + "," + gender + "," + country + "," + password + ","
                    + contactNumber + "," + age + "\n");
            System.out.println("Details saved to Candidate.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
