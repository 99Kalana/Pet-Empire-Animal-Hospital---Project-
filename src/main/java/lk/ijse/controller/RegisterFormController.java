package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnectionPET;
import lk.ijse.util.QrRegex;
import lk.ijse.util.QrTextField;
import lk.ijse.util.RegisterRegex;
import lk.ijse.util.RegisterTextField;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFormController {


    @FXML
    public TextField txtUserId;
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtPassword;
    @FXML
    public AnchorPane root;

    @FXML
    public void btnRegisterOnAction(ActionEvent actionEvent) {

        String userId = txtUserId.getText();

        String name = txtName.getText();

        String password = txtPassword.getText();

        try{
            boolean isSaved = saveUser(userId,password,name);

            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "User Saved!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private boolean saveUser(String userId, String password, String name) throws SQLException {

        String sql = "INSERT INTO user VALUES(?, ?, ?, ?)";

        Connection connection = DbConnectionPET.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, userId);
        pstm.setObject(2, password);
        pstm.setObject(3, name);
        pstm.setObject(4, null);

        return pstm.executeUpdate() > 0;
    }

    @FXML
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {

        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Stage stage = (Stage) this.root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));

        stage.setTitle("Login Form");

        stage.centerOnScreen();

    }

    public void txtUserIDOnKeyReleased(KeyEvent keyEvent) {
        RegisterRegex.setTextColour(RegisterTextField.USERID,txtUserId);
    }

    public boolean isValid(){
        if (!RegisterRegex.setTextColour(RegisterTextField.USERID,txtUserId));

        return true;
    }

}
