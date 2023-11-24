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

    // JavaFX components defined in the FXML file
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button LoginButton;

    @FXML
    private Button CancelButton;

    // Handler for the "Login" button click
    @FXML
    private void loginButtonClicked(ActionEvent event) {
        // Implement your login logic here
        String email = emailField.getText();
        String password = passwordField.getText();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/Admin.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] components = line.split(",");
                // Check if the credentials match any line in the Admin.txt file
                if (components.length >= 2 && email.equals(components[0]) && password.equals(components[1])) {
                    System.out.println("User authenticated!");

                    // Load the AdminDashboard.fxml file and open the admin dashboard
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/AdminDashboard.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();

                        // Get the controller instance and initialize it if needed
                        AdminDashboardController controller = loader.getController();
                        controller.initialize(); // You can modify this method name as per your need

                        // Close the current login stage
                        Stage CloseCurrentStage = (Stage) LoginButton.getScene().getWindow();
                        CloseCurrentStage.close();

                        // Open the Admin Dashboard stage
                        Stage stage = new Stage();
                        stage.setTitle("Admin Dashboard");
                        stage.setScene(new Scene(root));
                        stage.show();

                        // Close the current stage if needed
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    // Display an error message if the login fails
                    System.out.println("Login failed");
                    showAlert("Login Failed", "Invalid credentials. Please try again.");
                }
            }
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

            // Get the controller instance and initialize it if needed
            HomeController controller = loader.getController();

            // Close the current login stage
            Stage currentStage = (Stage) CancelButton.getScene().getWindow();
            currentStage.close();

            // Open the home stage
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current stage if needed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Display an alert with the given title and message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

   
}
