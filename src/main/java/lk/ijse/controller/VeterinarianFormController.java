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
import lk.ijse.model.Medicine;
import lk.ijse.model.Veterinarian;
import lk.ijse.model.tm.MedicineTm;
import lk.ijse.model.tm.VeterinarianTm;
import lk.ijse.repository.AppointmentRepo;
import lk.ijse.repository.MedicineRepo;
import lk.ijse.repository.VeterinarianRepo;
import lk.ijse.util.*;
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

public class VeterinarianFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtContactNo;
    public TextField txtEmail;

    public AnchorPane root;
    public TableView<VeterinarianTm> tblVeterinarian;

    public TableColumn<?,?> colId;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colContactNo;
    public TableColumn<?,?> colEmail;

    public void txtSearchOnAction(ActionEvent event) throws SQLException {

        String id = txtId.getText();

        Veterinarian veterinarian = VeterinarianRepo.searchById(id);

        if(veterinarian != null){
            txtId.setText(veterinarian.getId());
            txtName.setText(veterinarian.getName());
            txtContactNo.setText(Integer.toString(veterinarian.getContactNo()));
            txtEmail.setText(veterinarian.getEmail());

        }else{
            new Alert(Alert.AlertType.INFORMATION, "Veterinarian's details not found!").show();
        }

    }

    public void btnSaveOnAction(ActionEvent event) {

        String id = txtId.getText();
        String name = txtName.getText();
        int contactNo = Integer.parseInt(txtContactNo.getText());
        String email = txtEmail.getText();

        Veterinarian veterinarian = new Veterinarian(id, name, contactNo, email);

        try{
            boolean isSaved = VeterinarianRepo.save(veterinarian);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Veterinarian's details saved!").show();
                clearFields();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        loadAllVeterinarian();

    }

    private void clearFields(){

        txtId.setText("");
        txtName.setText("");
        txtContactNo.setText("");
        txtEmail.setText("");

    }

    public void btnUpdateOnAction(ActionEvent event) {

        String id = txtId.getText();
        String name = txtName.getText();
        int contactNo = Integer.parseInt(txtContactNo.getText());
        String email = txtEmail.getText();

        Veterinarian veterinarian = new Veterinarian(id, name, contactNo, email);

        try{
            boolean isUpdated = VeterinarianRepo.update(veterinarian);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Veterinarian's details updated!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllVeterinarian();

    }

    public void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent event) {

        String id = txtId.getText();

        try{
            boolean isDeleted = VeterinarianRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Veterinarian's details deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllVeterinarian();

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        root.getChildren().clear();
        root.getChildren().add(dashboardPane);

    }


    public void initialize() {
        setCellValueFactory();
        loadAllVeterinarian();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void loadAllVeterinarian() {
        ObservableList<VeterinarianTm> obList = FXCollections.observableArrayList();

        try {
            List<Veterinarian> veterinarianList = VeterinarianRepo.getAll();
            for (Veterinarian veterinarian : veterinarianList) {
                VeterinarianTm tm = new VeterinarianTm(
                        veterinarian.getId(),
                        veterinarian.getName(),
                        veterinarian.getContactNo(),
                        veterinarian.getEmail()
                );

                obList.add(tm);
            }

            tblVeterinarian.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtVeterinarianIDOnKeyReleased(KeyEvent keyEvent) {
        VeterinarianRegex.setTextColour(VeterinarianTextField.ID,txtId);
    }

    public void txtVeterinarianContactNoOnKeyReleased(KeyEvent keyEvent) {
        VeterinarianRegex.setTextColour(VeterinarianTextField.CONTACTNO,txtContactNo);
    }

    public void txtVeterinarianEmailOnKeyReleased(KeyEvent keyEvent) {
        VeterinarianRegex.setTextColour(VeterinarianTextField.EMAIL,txtEmail);
    }


    public boolean isValid(){
        if (!VeterinarianRegex.setTextColour(VeterinarianTextField.ID,txtId));
        if (!VeterinarianRegex.setTextColour(VeterinarianTextField.CONTACTNO,txtContactNo));
        if (!VeterinarianRegex.setTextColour(VeterinarianTextField.EMAIL,txtEmail));

        return true;
    }


    public void btnPrintReportOnAction(ActionEvent event) throws JRException, SQLException {

        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();


        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reports/VeterinarianReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        data.put("Date", dateString);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnectionPET.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }
}
