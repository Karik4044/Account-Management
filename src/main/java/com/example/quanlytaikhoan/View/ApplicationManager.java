package com.example.quanlytaikhoan.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.lang.Exception;

//Lớp này có chức năng khởi động ứng dụng JavaFX
public class ApplicationManager extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationManager.class.getResource("/com/example/quanlytaikhoan/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Image image = new Image("6405024.png");
        primaryStage.getIcons().add(image);


        primaryStage.setResizable(false);
        primaryStage.setTitle("User Account Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}