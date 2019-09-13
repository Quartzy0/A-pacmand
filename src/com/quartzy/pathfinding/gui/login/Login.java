package com.quartzy.pathfinding.gui.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application{
    
    @Override
    public void start(Stage primaryStage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        primaryStage.setTitle("Authentication");
        primaryStage.setScene(new Scene(root, 302, 210));
        primaryStage.show();
    }
    
    public void doIt() throws ClassNotFoundException{
        launch((Class<? extends Application>) Class.forName("com.quartzy.pathfinding.gui.login.Login"));
    }
}
