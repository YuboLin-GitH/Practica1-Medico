package org.example.practica1medicoyubo;

import javafx.scene.Parent;
import javafx.scene.text.Font;
import org.example.practica1medicoyubo.util.R;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.FileInputStream;
import java.net.URL;


public class App extends Application {


    @Override
    public void start(Stage stage) throws Exception {



        FXMLLoader loader = new FXMLLoader(R.getUI("iniciaSesion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Inicio de Sesión");
        stage.setScene(scene);
        stage.show();


    }


    public static void main(String[] args) {
        launch();
    }
}
