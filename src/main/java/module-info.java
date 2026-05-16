module com.example.eclipsedsun {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.eclipsedsun.game to javafx.fxml;
    exports com.eclipsedsun.game;
}