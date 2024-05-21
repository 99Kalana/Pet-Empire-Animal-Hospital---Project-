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
import lk.ijse.repository.AppointmentRepo;
import lk.ijse.repository.ClientRepo;
import lk.ijse.util.AppointmentRegex;
import lk.ijse.util.AppointmentTextField;
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

public class AppointmentFormController {

    public AnchorPane root;
    public TextField txtId;
    public TextField txtClientId;
    public TextField txtNo;
    public TextField txtDate;
    public TextField txtTime;
    public TableView<AppointmentTm> tblAppointment;
    public TableColumn<?,?> colApId;
    public TableColumn<?,?> colCId;
    public TableColumn<?,?> colNo;
    public TableColumn<?,?> colDate;
    public TableColumn<?,?> colTime;

    public void txtSearchOnAction(ActionEvent event) throws SQLException {

        String id = txtId.getText();

        Appointment appointment = AppointmentRepo.searchById(id);

        if(appointment != null){
            txtId.setText(appointment.getId());
            txtClientId.setText(appointment.getClientId());
            txtNo.setText(Integer.toString(appointment.getNo()));
            txtDate.setText(appointment.getDate());
            txtTime.setText(appointment.getTime());
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Appointment details not found!").show();
        }

    }

    public void btnSaveOnAction(ActionEvent event) {

        String id = txtId.getText();
        String clientId = txtClientId.getText();
        int no = Integer.parseInt(txtNo.getText());
        String date = txtDate.getText();
        String time = txtTime.getText();


        Appointment appointment = new Appointment(id, clientId, no, date, time);

        try{
            boolean isSaved = AppointmentRepo.save(appointment);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Appointment's details saved!").show();
                clearFields();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        loadAllAppointments();

    }

    private void clearFields(){

        txtId.setText("");
        txtClientId.setText("");
        txtNo.setText("");
        txtDate.setText("");
        txtTime.setText("");

    }

    public void btnUpdateOnAction(ActionEvent event) {

        String id = txtId.getText();
        String clientId = txtClientId.getText();
        int no = Integer.parseInt(txtNo.getText());
        String date = txtDate.getText();
        String time = txtTime.getText();


        Appointment appointment = new Appointment(id, clientId, no, date, time);

        try{
            boolean isUpdated = AppointmentRepo.update(appointment);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Appointment's details updated!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllAppointments();

    }

    public void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent event) {

        String id = txtId.getText();

        try{
            boolean isDeleted = AppointmentRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Appointment's details deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllAppointments();

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        root.getChildren().clear();
        root.getChildren().add(dashboardPane);

    }


    public void initialize() {
        setCellValueFactory();
        loadAllAppointments();
    }

    private void setCellValueFactory() {
        colApId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    private void loadAllAppointments() {
        ObservableList<AppointmentTm> obList = FXCollections.observableArrayList();

        try {
            List<Appointment> appointmentList = AppointmentRepo.getAll();
            for (Appointment appointment : appointmentList) {
                AppointmentTm tm = new AppointmentTm(
                        appointment.getId(),
                        appointment.getClientId(),
                        appointment.getNo(),
                        appointment.getDate(),
                        appointment.getTime()
                );

                obList.add(tm);
            }

            tblAppointment.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void txtAppointmentIDOnKeyReleased(KeyEvent keyEvent) {
        AppointmentRegex.setTextColour(AppointmentTextField.ID,txtId);
    }

    public void txtClientIDOnKeyReleased(KeyEvent keyEvent) {
        AppointmentRegex.setTextColour(AppointmentTextField.CLIENTID,txtClientId);
    }

    public void txtAppointmentNoOnKeyReleased(KeyEvent keyEvent) {
        AppointmentRegex.setTextColour(AppointmentTextField.NO,txtNo);
    }

    public void txtAppointmentDateOnKeyReleased(KeyEvent keyEvent) {
        AppointmentRegex.setTextColour(AppointmentTextField.DATE,txtDate);
    }

    public void txtAppointmentTimeOnKeyReleased(KeyEvent keyEvent) {
        AppointmentRegex.setTextColour(AppointmentTextField.TIME,txtTime);
    }

    public boolean isValid(){
        if (!AppointmentRegex.setTextColour(AppointmentTextField.ID,txtId));
        if (!AppointmentRegex.setTextColour(AppointmentTextField.CLIENTID,txtClientId));
        if (!AppointmentRegex.setTextColour(AppointmentTextField.NO,txtNo));
        if (!AppointmentRegex.setTextColour(AppointmentTextField.DATE,txtDate));
        if (!AppointmentRegex.setTextColour(AppointmentTextField.TIME,txtTime));
        return true;
    }



    public void btnPrintReportOnAction(ActionEvent event) throws JRException, SQLException {

        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();


        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reports/AppointmentReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        data.put("date", dateString);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnectionPET.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);


    }
}
