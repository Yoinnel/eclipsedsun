module com.example.eclipsedsun {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.eclipsedsun.game to javafx.fxml;
    exports com.eclipsedsun.game;
}