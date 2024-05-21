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
import lk.ijse.model.Medicine;
import lk.ijse.model.PrescriptionTreatment;
import lk.ijse.model.tm.MedicineTm;
import lk.ijse.model.tm.PrescriptionTreatmentTm;
import lk.ijse.repository.MedicineRepo;
import lk.ijse.repository.PrescriptionTreatmentRepo;
import lk.ijse.util.PrescriptionMedicineRegex;
import lk.ijse.util.PrescriptionMedicineTextField;
import lk.ijse.util.PrescriptionTreatmentRegex;
import lk.ijse.util.PrescriptionTreatmentTextField;
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

public class PrescriptionTreatmentFormController {

    public AnchorPane root;

    public TextField txtId;
    public TextField txtTId;
    public TextField txtPrice;
    public TextField txtDate;
    public TextField txtTime;
    public TableView<PrescriptionTreatmentTm> tblPrescriptionTreatments;

    public TableColumn<?,?> colId;
    public TableColumn<?,?> colTId;
    public TableColumn<?,?> colPrice;
    public TableColumn<?,?> colDate;
    public TableColumn<?,?> colTime;

    public void txtSearchOnAction(ActionEvent event) throws SQLException {

        String id = txtId.getText();

        PrescriptionTreatment prescriptionTreatment = PrescriptionTreatmentRepo.searchById(id);

        if(prescriptionTreatment != null){
            txtId.setText(prescriptionTreatment.getpId());
            txtTId.setText(prescriptionTreatment.gettId());
            txtPrice.setText(Double.toString(prescriptionTreatment.getPrice()));
            txtDate.setText(prescriptionTreatment.getDate());
            txtTime.setText(prescriptionTreatment.getTime());

        }else{
            new Alert(Alert.AlertType.INFORMATION, "Prescription and Treatment's details not found!").show();
        }

    }

    public void btnSaveOnAction(ActionEvent event) {

        String pId = txtId.getText();
        String tId = txtTId.getText();
        double price = Double.parseDouble(txtPrice.getText());
        String date = txtDate.getText();
        String time = txtTime.getText();

        PrescriptionTreatment prescriptionTreatment = new PrescriptionTreatment(pId, tId, price, date, time);

        try{
            boolean isSaved = PrescriptionTreatmentRepo.save(prescriptionTreatment);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Prescription and Treatment's details saved!").show();
                clearFields();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        loadAllPrescriptionTreatments();

    }

    private void clearFields(){

        txtId.setText("");
        txtTId.setText("");
        txtPrice.setText("");
        txtDate.setText("");
        txtTime.setText("");

    }

    public void btnUpdateOnAction(ActionEvent event) {

        String pId = txtId.getText();
        String tId = txtTId.getText();
        double price = Double.parseDouble(txtPrice.getText());
        String date = txtDate.getText();
        String time = txtTime.getText();

        PrescriptionTreatment prescriptionTreatment = new PrescriptionTreatment(pId, tId, price, date, time);

        try{
            boolean isUpdated = PrescriptionTreatmentRepo.update(prescriptionTreatment);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Prescription and Treatment's details updated!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllPrescriptionTreatments();

    }

    public void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent event) {

        String id = txtId.getText();

        try{
            boolean isDeleted = PrescriptionTreatmentRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Prescription and Treatment's details deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllPrescriptionTreatments();

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        root.getChildren().clear();
        root.getChildren().add(dashboardPane);

    }

    public void initialize() {
        setCellValueFactory();
        loadAllPrescriptionTreatments();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("pId"));
        colTId.setCellValueFactory(new PropertyValueFactory<>("tId"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

    }

    private void loadAllPrescriptionTreatments() {
        ObservableList<PrescriptionTreatmentTm> obList = FXCollections.observableArrayList();

        try {
            List<PrescriptionTreatment> prescriptionTreatmentList = PrescriptionTreatmentRepo.getAll();
            for (PrescriptionTreatment prescriptionTreatment : prescriptionTreatmentList) {
                PrescriptionTreatmentTm tm = new PrescriptionTreatmentTm(
                        prescriptionTreatment.getpId(),
                        prescriptionTreatment.gettId(),
                        prescriptionTreatment.getPrice(),
                        prescriptionTreatment.getDate(),
                        prescriptionTreatment.getTime()
                );

                obList.add(tm);
            }

            tblPrescriptionTreatments.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtPrescriptionIDOnKeyReleased(KeyEvent keyEvent) {
        PrescriptionTreatmentRegex.setTextColour(PrescriptionTreatmentTextField.PID,txtId);
    }

    public void txtTreatmentIDOnKeyReleased(KeyEvent keyEvent) {
        PrescriptionTreatmentRegex.setTextColour(PrescriptionTreatmentTextField.TID,txtTId);
    }

    public void txtTreatmentPriceOnKeyReleased(KeyEvent keyEvent) {
        PrescriptionTreatmentRegex.setTextColour(PrescriptionTreatmentTextField.PRICE,txtPrice);
    }

    public void txtDateOnKeyReleased(KeyEvent keyEvent) {
        PrescriptionTreatmentRegex.setTextColour(PrescriptionTreatmentTextField.DATE,txtDate);
    }

    public void txtTimeOnKeyReleased(KeyEvent keyEvent) {
        PrescriptionTreatmentRegex.setTextColour(PrescriptionTreatmentTextField.TIME,txtTime);
    }

    public boolean isValid(){
        if (!PrescriptionTreatmentRegex.setTextColour(PrescriptionTreatmentTextField.PID,txtId));
        if (!PrescriptionTreatmentRegex.setTextColour(PrescriptionTreatmentTextField.TID,txtTId));
        if (!PrescriptionTreatmentRegex.setTextColour(PrescriptionTreatmentTextField.PRICE,txtPrice));
        if (!PrescriptionTreatmentRegex.setTextColour(PrescriptionTreatmentTextField.DATE,txtDate));
        if (!PrescriptionTreatmentRegex.setTextColour(PrescriptionTreatmentTextField.TIME,txtTime));

        return true;
    }

    public void btnPrintReportOnAction(ActionEvent event) throws JRException, SQLException {

        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();


        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reports/PrescriptionTreatmentReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        data.put("Date", dateString);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnectionPET.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }
}
