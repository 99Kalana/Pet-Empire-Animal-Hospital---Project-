package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.Appointment;
import lk.ijse.model.Client;
import lk.ijse.model.tm.AppointmentTm;
import lk.ijse.model.tm.ClientTm;
import lk.ijse.repository.AppointmentRepo;
import lk.ijse.repository.ClientRepo;
import lk.ijse.util.AppointmentRegex;
import lk.ijse.util.AppointmentTextField;
import lk.ijse.util.ClientRegex;
import lk.ijse.util.ClientTextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientFormController {
    public AnchorPane root;

    public TextField txtId;
    public TextField txtPetId;

    public TextField txtName;
    public TextField txtAddress;
    public TextField txtEmail;
    public TextField txtContactNo;
    public TableView<ClientTm> tblClient;

    public TableColumn<?,?> colId;
    public TableColumn<?,?> colPetId;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colAddress;
    public TableColumn<?,?> colEmail;
    public TableColumn<?,?> colContactNo;

    public void txtSearchOnAction(ActionEvent event) throws SQLException {

        String id = txtId.getText();

        Client client = ClientRepo.searchById(id);

        if(client != null){
            txtId.setText(client.getId());
            txtPetId.setText(client.getPetId());
            txtName.setText(client.getName());
            txtAddress.setText(client.getAddress());
            txtEmail.setText(client.getEmail());
            txtContactNo.setText(Integer.toString(client.getContactNo()));
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Client details not found!").show();
        }

    }

    public void btnSaveOnAction(ActionEvent event) {

        String id = txtId.getText();
        String petId = txtPetId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        int contactNo = Integer.parseInt(txtContactNo.getText());

        Client client = new Client(id, petId, name, address, email, contactNo);

        try{
            boolean isSaved = ClientRepo.save(client);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Client's details saved!").show();
                clearFields();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        loadAllClients();

    }

    private void clearFields(){

        txtId.setText("");
        txtPetId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtContactNo.setText("");


    }

    public void btnUpdateOnAction(ActionEvent event) {

        String id = txtId.getText();
        String petId = txtPetId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        int contactNo = Integer.parseInt(txtContactNo.getText());

        Client client = new Client(id, petId, name, address, email, contactNo);

        try{
            boolean isUpdated = ClientRepo.update(client);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Client's details updated!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllClients();

    }

    public void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent event) {

        String id = txtId.getText();

        try{
            boolean isDeleted = ClientRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Client's details deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllClients();

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        root.getChildren().clear();
        root.getChildren().add(dashboardPane);

    }

    public void initialize() {
        setCellValueFactory();
        loadAllClients();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPetId.setCellValueFactory(new PropertyValueFactory<>("petId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));

    }

    private void loadAllClients() {
        ObservableList<ClientTm> obList = FXCollections.observableArrayList();

        try {
            List<Client> clientList = ClientRepo.getAll();
            for (Client client : clientList) {
                ClientTm tm = new ClientTm(
                        client.getId(),
                        client.getPetId(),
                        client.getName(),
                        client.getAddress(),
                        client.getEmail(),
                        client.getContactNo()
                );

                obList.add(tm);
            }

            tblClient.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtClientIDOnKeyReleased(KeyEvent keyEvent) {
        ClientRegex.setTextColour(ClientTextField.ID,txtId);
    }

    public void txtPetIDOnKeyReleased(KeyEvent keyEvent) {
        ClientRegex.setTextColour(ClientTextField.PETID,txtPetId);
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        ClientRegex.setTextColour(ClientTextField.EMAIL,txtEmail);
    }

    public void txtContactNoOnKeyReleased(KeyEvent keyEvent) {
        ClientRegex.setTextColour(ClientTextField.CONTACTNO,txtContactNo);
    }

    public boolean isValid(){
        if (!ClientRegex.setTextColour(ClientTextField.ID,txtId));
        if (!ClientRegex.setTextColour(ClientTextField.PETID,txtPetId));
        if (!ClientRegex.setTextColour(ClientTextField.EMAIL,txtEmail));
        if (!ClientRegex.setTextColour(ClientTextField.CONTACTNO,txtContactNo));
        return true;
    }

    public void btnPrintReportOnAction(ActionEvent event) throws JRException, SQLException {

        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();


        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reports/ClientReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        data.put("Date", dateString);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnectionPET.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }
}
