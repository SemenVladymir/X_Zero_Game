package com.example.x_zero_game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 499, 490);
        stage.setTitle("X-Zero GAME!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch();}
}