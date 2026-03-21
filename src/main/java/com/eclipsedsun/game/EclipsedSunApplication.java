/**
 * Game "El sol eclipsado"
 * Developed int JavaFX, using IntelliJ Idea and SceneBuilder
 * Languages used: Java
 * <p>
 * Description of the game:
 * "El sol eclipsado" it is a game where one player must enter a word which will be "the secret word";
 * the second player must guess the word, being able to use clues if needed.
 * <p>
 * This class contains the principal application of the game, this show the main window of the game
 *
 * @author Estaban Granada Salamanca
 * @author Yoinnel Gabriel Martinez Brito
 *
 * @version 1.0
 * @since 2026
 */

package com.eclipsedsun.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @since 1.0
 * This is the main window of the game.
 */
public class EclipsedSunApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EclipsedSunApplication.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("El Sol Eclipsado");
        stage.setScene(scene);
        stage.show();
    }
}