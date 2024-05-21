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
import lk.ijse.model.Prescription;
import lk.ijse.model.Veterinarian;
import lk.ijse.model.tm.MedicineTm;
import lk.ijse.model.tm.PrescriptionTm;
import lk.ijse.repository.MedicineRepo;
import lk.ijse.repository.PrescriptionRepo;
import lk.ijse.repository.VeterinarianRepo;
import lk.ijse.util.PetRegex;
import lk.ijse.util.PetTextField;
import lk.ijse.util.PrescriptionRegex;
import lk.ijse.util.PrescriptionTextField;
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

public class PrescriptionFormController {

    public AnchorPane root;

    public TextField txtId;
    public TextField txtType;
    public TextField txtVeterinarianId;
    public TableView<PrescriptionTm> tblPrescription;

    public TableColumn<?,?> colId;
    public TableColumn<?,?> colType;
    public TableColumn<?,?> colVeterinarianId;

    public void txtSearchOnAction(ActionEvent event) throws SQLException {

        String id = txtId.getText();

        Prescription prescription = PrescriptionRepo.searchById(id);

        if(prescription != null){
            txtId.setText(prescription.getId());
            txtType.setText(prescription.getType());
            txtVeterinarianId.setText(prescription.getVeterinarianId());

        }else{
            new Alert(Alert.AlertType.INFORMATION, "Prescription details not found!").show();
        }

    }

    public void btnSaveOnAction(ActionEvent event) {

        String id = txtId.getText();
        String type = txtType.getText();
        String veterinarianId = txtVeterinarianId.getText();

        Prescription prescription = new Prescription(id, type, veterinarianId);

        try{
            boolean isSaved = PrescriptionRepo.save(prescription);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Prescription details saved!").show();
                clearFields();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        loadAllPrescriptions();

    }

    private void clearFields(){

        txtId.setText("");
        txtType.setText("");
        txtVeterinarianId.setText("");

    }

    public void btnUpdateOnAction(ActionEvent event) {

        String id = txtId.getText();
        String type = txtType.getText();
        String veterinarianId = txtVeterinarianId.getText();

        Prescription prescription = new Prescription(id, type, veterinarianId);

        try{
            boolean isUpdated = PrescriptionRepo.update(prescription);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Prescription details updated!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllPrescriptions();

    }

    public void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent event) {

        String id = txtId.getText();

        try{
            boolean isDeleted = PrescriptionRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Prescription details deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllPrescriptions();

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        root.getChildren().clear();
        root.getChildren().add(dashboardPane);

    }

    public void initialize() {
        setCellValueFactory();
        loadAllPrescriptions();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colVeterinarianId.setCellValueFactory(new PropertyValueFactory<>("veterinarianId"));

    }

    private void loadAllPrescriptions() {
        ObservableList<PrescriptionTm> obList = FXCollections.observableArrayList();

        try {
            List<Prescription> prescriptionList = PrescriptionRepo.getAll();
            for (Prescription prescription : prescriptionList) {
                PrescriptionTm tm = new PrescriptionTm(
                        prescription.getId(),
                        prescription.getType(),
                        prescription.getVeterinarianId()
                );

                obList.add(tm);
            }

            tblPrescription.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtPrescriptionIDOnKeyReleased(KeyEvent keyEvent) {
        PrescriptionRegex.setTextColour(PrescriptionTextField.ID,txtId);
    }

    public void txtVeterinarianIDOnKeyReleased(KeyEvent keyEvent) {
        PrescriptionRegex.setTextColour(PrescriptionTextField.VETERINARIANID,txtVeterinarianId);
    }

    public boolean isValid(){
        if (!PrescriptionRegex.setTextColour(PrescriptionTextField.ID,txtId));
        if (!PrescriptionRegex.setTextColour(PrescriptionTextField.VETERINARIANID,txtVeterinarianId));

        return true;
    }

    public void btnPrintReportOnAction(ActionEvent event) throws JRException, SQLException {

        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();


        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reports/PrescriptionReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        data.put("Date", dateString);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnectionPET.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }
}
