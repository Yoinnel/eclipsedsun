module com.example.eclipsedsun {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.eclipsedsun to javafx.fxml;
    exports com.example.eclipsedsun;
}