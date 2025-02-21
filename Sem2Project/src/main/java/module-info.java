module com.example.sem2project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sem2project to javafx.fxml;
    exports com.example.sem2project;
    exports com.example.sem2project.Model;
    opens com.example.sem2project.Model to javafx.fxml;
    opens com.example.sem2project.Controllers to javafx.fxml;
    exports com.example.sem2project.Controllers;
}