package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnectionPET;
import lk.ijse.util.LoginRegex;
import lk.ijse.util.LoginTextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    public TextField txtUserId;

    public TextField txtPassword;

    public AnchorPane rootNode;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {

        String userId = txtUserId.getText();
        String password = txtPassword.getText();

        try{
            checkCredential(userId,password);
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }




    private void checkCredential(String userId,String password) throws SQLException, IOException {

        String sql = "SELECT user_id, password FROM user WHERE user_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1,userId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String dbPassword = resultSet.getString("password");

            if (password.equals(dbPassword)){

                navigateToTheDashboard(userId);

            }else{
                new Alert(Alert.AlertType.ERROR,"Sorry! Entered password is incorrect!").show();
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Sorry! Entered user ID can't be find!").show();
        }

    }

    private void navigateToTheDashboard(String userId) throws IOException, SQLException {

       //AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/home_page_form.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home_page_form.fxml"));
        AnchorPane rootNode = loader.load();
        HomePageFormController controller = loader.getController();
        controller.setUserId(userId);

       Scene scene = new Scene(rootNode);

       Stage stage = (Stage) this.rootNode.getScene().getWindow();

       stage.setScene(scene);

       stage.centerOnScreen();

       stage.setTitle("Homepage Form");

    }


    public void linkRegisterOnAction(ActionEvent actionEvent) throws IOException {

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/register_form.fxml"));

        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setScene(new Scene(rootNode));

        stage.setTitle("Register Form");

        stage.centerOnScreen();

    }

    public void txtUserIDOnKeyReleased(KeyEvent keyEvent) {
        LoginRegex.setTextColour(LoginTextField.USERID,txtUserId);
    }

    public void linkForgotPasswordOnAction(ActionEvent event) throws IOException {

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/reset_password_form.fxml"));

        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setScene(new Scene(rootNode));

        stage.setTitle("Register Form");

        stage.centerOnScreen();

    }

    public void btnExitOnAction(ActionEvent event) throws IOException {

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/welcome_form.fxml"));

        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setScene(new Scene(rootNode));

        stage.setTitle("Welcome Form");

        stage.centerOnScreen();

    }
}
