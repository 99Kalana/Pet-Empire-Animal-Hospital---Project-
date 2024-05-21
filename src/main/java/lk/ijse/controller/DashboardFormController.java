package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.db.DbConnectionPET;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.fxml.FXMLLoader.load;


public class DashboardFormController {
    public AnchorPane root;
    public Label lblPetCount;
    public Label lblClientCount;
    public Label lblAppointmentCount;
    public Label lblVeterinarianCount;
    public Label lblSupplierCount;
    public Label lblUsername;

    private int petCount;
    private int clientCount;
    private int appointmentCount;
    private int veterinarianCount;
    private int supplierCount;

    private String userId;


    public void initialize() throws SQLException {



        try {
            petCount = getPetCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setPetCount(petCount);


        try {
            clientCount = getClientCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setClientCount(clientCount);


        try {
            appointmentCount = getAppointmentCount();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setAppoinmentCount(appointmentCount);


        try {
            veterinarianCount = getVeterinarianCount();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setVeterinarianCount(veterinarianCount);


        try {
            supplierCount = getSupplierCount();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setSupplierCount(supplierCount);


        setUserId(userId);

    }

    public void setUserId(String userId) throws SQLException {
        this.userId=userId;
        setUsername(userId);

    }

    public void setUsername( String userId) throws SQLException {
        String username = getUsername(userId);
        lblUsername.setText(username);
    }

    private String getUsername(String userId) throws SQLException {


        String sql = "SELECT user_name FROM user WHERE user_id = ?";
        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,userId);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            String username = resultSet.getString("user_name");
            return username;
        }
        return "Unknown";

    }





    private void setPetCount(int petCount) {
        lblPetCount.setText(String.valueOf(petCount));
    }

    private int getPetCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS pet_count FROM pet";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("pet_count");
        }
        return 0;
    }

    private void setClientCount(int clientCount) {
        lblClientCount.setText(String.valueOf(clientCount));
    }

    private int getClientCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS client_count FROM client";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("client_count");
        }
        return 0;
    }

    private void setAppoinmentCount(int appoinmentCount) {
        lblAppointmentCount.setText(String.valueOf(appoinmentCount));
    }

    private int getAppointmentCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS appointment_count FROM appointment";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("appointment_count");
        }
        return 0;
    }

    private void setVeterinarianCount(int veterinarianCount) {
        lblVeterinarianCount.setText(String.valueOf(veterinarianCount));
    }

    private int getVeterinarianCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS veterinarian_count FROM veterinarian";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("veterinarian_count");
        }
        return 0;
    }


    private void setSupplierCount(int supplierCount) {
        lblSupplierCount.setText(String.valueOf(supplierCount));
    }

    private int getSupplierCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS supplier_count FROM supplier";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("supplier_count");
        }
        return 0;
    }


    public void btnQrPageOnAction(ActionEvent event) throws IOException {

        AnchorPane petPane = FXMLLoader.load(this.getClass().getResource("/view/qr_form.fxml"));

        root.getChildren().clear();
        root.getChildren().add(petPane);


    }


}
