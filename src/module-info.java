module CitizenshipAssessmentSystem {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens application to javafx.fxml;
    opens application.controller to javafx.fxml;
    exports application;  // Export the entire application package
    exports application.controller;  // Export the controller package if needed
}
