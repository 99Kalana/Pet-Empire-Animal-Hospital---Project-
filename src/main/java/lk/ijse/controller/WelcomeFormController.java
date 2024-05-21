package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeFormController {


    public AnchorPane rootNodeO;

    public void btnWelcomeOnAction(ActionEvent event) throws IOException {

        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) this.rootNodeO.getScene().getWindow();

        stage.setScene(scene);

        stage.centerOnScreen();

        stage.setTitle("Login Form");

    }
}
