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
import lk.ijse.model.MedicineSupplier;
import lk.ijse.model.Supplier;
import lk.ijse.model.tm.MedicineSupplierTm;
import lk.ijse.model.tm.MedicineTm;
import lk.ijse.repository.MedicineRepo;
import lk.ijse.repository.MedicineSupplierRepo;
import lk.ijse.repository.SupplierRepo;
import lk.ijse.util.MedicineRegex;
import lk.ijse.util.MedicineSupplierRegex;
import lk.ijse.util.MedicineSupplierTextField;
import lk.ijse.util.MedicineTextField;
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

public class MedicineSupplierFormController {

    public AnchorPane root;

    public TextField txtId;
    public TextField txtMId;
    public TextField txtDate;
    public TableView<MedicineSupplierTm> tblMedicineSupply;

    public TableColumn<?,?> colId;
    public TableColumn<?,?> colMId;
    public TableColumn<?,?> colDate;


    public void txtSearchOnAction(ActionEvent event) throws SQLException {

        String id = txtId.getText();

        MedicineSupplier medicineSupplier = MedicineSupplierRepo.searchById(id);

        if(medicineSupplier != null){

            txtId.setText(medicineSupplier.getId());
            txtMId.setText(medicineSupplier.getmId());
            txtDate.setText(medicineSupplier.getDate());


        }else{
            new Alert(Alert.AlertType.INFORMATION, " Medicine Supplier details not found!").show();
        }

    }

    public void btnSaveOnAction(ActionEvent event) {

        String id = txtId.getText();
        String mId = txtMId.getText();
        String date = txtDate.getText();

        MedicineSupplier medicineSupplier = new MedicineSupplier(id, mId, date);

        try{
            boolean isSaved = MedicineSupplierRepo.save(medicineSupplier);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, " Medicine Supplier details saved!").show();
                clearFields();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        loadAllMedicineSuppliers();

    }

    private void clearFields(){

        txtId.setText("");
        txtMId.setText("");
        txtDate.setText("");

    }

    public void btnUpdateOnAction(ActionEvent event) {

        String id = txtId.getText();
        String mId = txtMId.getText();
        String date = txtDate.getText();

        MedicineSupplier medicineSupplier = new MedicineSupplier(id, mId, date);

        try{
            boolean isUpdated = MedicineSupplierRepo.update(medicineSupplier);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, " Medicine Supplier details updated!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMedicineSuppliers();

    }

    public void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent event) {

        String id = txtId.getText();

        try{
            boolean isDeleted = MedicineSupplierRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, " Medicine Supplier details deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMedicineSuppliers();

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        root.getChildren().clear();
        root.getChildren().add(dashboardPane);

    }

    public void initialize() {
        setCellValueFactory();
        loadAllMedicineSuppliers();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMId.setCellValueFactory(new PropertyValueFactory<>("mId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    private void loadAllMedicineSuppliers() {
        ObservableList<MedicineSupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<MedicineSupplier> medicineSupplierList = MedicineSupplierRepo.getAll();
            for (MedicineSupplier medicineSupplier : medicineSupplierList) {
                MedicineSupplierTm tm = new MedicineSupplierTm(
                        medicineSupplier.getId(),
                        medicineSupplier.getmId(),
                        medicineSupplier.getDate()
                );

                obList.add(tm);
            }

            tblMedicineSupply.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtSupplierIDOnKeyReleased(KeyEvent keyEvent) {
        MedicineSupplierRegex.setTextColour(MedicineSupplierTextField.ID,txtId);
    }

    public void txtMedicineIDOnKeyReleased(KeyEvent keyEvent) {
        MedicineSupplierRegex.setTextColour(MedicineSupplierTextField.MID,txtMId);
    }

    public void txtDateOnKeyReleased(KeyEvent keyEvent) {
        MedicineSupplierRegex.setTextColour(MedicineSupplierTextField.DATE,txtDate);
    }

    public boolean isValid(){
        if (!MedicineSupplierRegex.setTextColour(MedicineSupplierTextField.ID,txtId));
        if (!MedicineSupplierRegex.setTextColour(MedicineSupplierTextField.MID,txtMId));
        if (!MedicineSupplierRegex.setTextColour(MedicineSupplierTextField.DATE,txtDate));

        return true;
    }

    public void btnPrintReportOnAction(ActionEvent event) throws JRException, SQLException {

        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();


        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reports/MedicineSupplier.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        data.put("Date", dateString);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnectionPET.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }
}
