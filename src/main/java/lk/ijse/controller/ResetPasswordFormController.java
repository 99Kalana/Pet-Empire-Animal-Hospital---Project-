package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnectionPET;
import lk.ijse.util.ResetPasswordRegex;
import lk.ijse.util.ResetPasswordTextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResetPasswordFormController {
    public TextField txtUserId;
    
    public TextField txtName;
    public TextField txtPassword;

    public AnchorPane root;



    public void btnResetPasswordOnAction(ActionEvent event) {


        String userId = txtUserId.getText();
        String name = txtName.getText();
        String newPassword = txtPassword.getText();

        try {
            if (isValidUser(userId,name)){
                boolean isReset = resetPassword(userId, newPassword);
                if (isReset){
                    new Alert(Alert.AlertType.CONFIRMATION,"Password reset Successfully!").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Password reset Unsuccessful!").show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"Invalid User ID or Username!").show();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Error occurred while resetting password"+e.getMessage()).show();
        }


    }

    private boolean isValidUser(String userId, String name) throws SQLException {

        String sql = "SELECT user_id FROM user WHERE user_id = ? AND user_name = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, userId);
        pstm.setString(2,name);

        ResultSet resultSet = pstm.executeQuery();
        return resultSet.next();

    }

    private boolean resetPassword(String userId, String newPassword) throws SQLException {

        String sql = "UPDATE user SET password = ? WHERE user_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,newPassword);
        pstm.setString(2,userId);

        return pstm.executeUpdate()>0;

    }


    public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Stage stage = (Stage) this.root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));

        stage.setTitle("Login Form");

        stage.centerOnScreen();

    }

    public void txtUserIDOnKeyReleased(KeyEvent keyEvent) {
        ResetPasswordRegex.setTextColour(ResetPasswordTextField.USERID,txtUserId);
    }



}
