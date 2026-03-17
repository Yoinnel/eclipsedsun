package com.eclipsedsun.game;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EclipsedSunController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
