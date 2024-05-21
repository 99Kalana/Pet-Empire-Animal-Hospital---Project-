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
import lk.ijse.model.Client;
import lk.ijse.model.Medicine;
import lk.ijse.model.Veterinarian;
import lk.ijse.model.tm.ClientTm;
import lk.ijse.model.tm.MedicineTm;
import lk.ijse.repository.ClientRepo;
import lk.ijse.repository.MedicineRepo;
import lk.ijse.repository.VeterinarianRepo;
import lk.ijse.util.ClientRegex;
import lk.ijse.util.ClientTextField;
import lk.ijse.util.MedicineRegex;
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

public class MedicineFormController {
    public AnchorPane root;
    
    public TextField txtId;
    public TextField txtName;
    public TextField txtType;
    public TextField txtPrice;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public TableView<MedicineTm> tblMedicine;

    public TableColumn<?,?> colId;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colType;
    public TableColumn<?,?> colPrice;
    public TableColumn<?,?> colDescription;
    public TableColumn<?,?> colQtyOnHand;

    public void txtSearchOnAction(ActionEvent event) throws SQLException {

        String id = txtId.getText();

        Medicine medicine = MedicineRepo.searchById(id);

        if(medicine != null){
            txtId.setText(medicine.getId());
            txtName.setText(medicine.getName());
            txtType.setText(medicine.getType());
            txtPrice.setText(Double.toString(medicine.getPrice()));
            txtDescription.setText(medicine.getDescription());
            txtQtyOnHand.setText(Integer.toString(medicine.getQtyOnHand()));

        }else{
            new Alert(Alert.AlertType.INFORMATION, "Medicine details not found!").show();
        }

    }

    public void btnSaveOnAction(ActionEvent event) {

        String id = txtId.getText();
        String name = txtName.getText();
        String type = txtType.getText();
        double price = Double.parseDouble(txtPrice.getText());
        String description = txtDescription.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        Medicine medicine = new Medicine(id, name, type, price, description, qtyOnHand);

        try{
            boolean isSaved = MedicineRepo.save(medicine);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Medicine details saved!").show();
                clearFields();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        loadAllMedicine();

    }

    private void clearFields(){

        txtId.setText("");
        txtName.setText("");
        txtType.setText("");
        txtPrice.setText("");
        txtDescription.setText("");
        txtQtyOnHand.setText("");

    }

    public void btnUpdateOnAction(ActionEvent event) {

        String id = txtId.getText();
        String name = txtName.getText();
        String type = txtType.getText();
        double price = Double.parseDouble(txtPrice.getText());
        String description = txtDescription.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        Medicine medicine = new Medicine(id, name, type, price, description, qtyOnHand);

        try{
            boolean isUpdated = MedicineRepo.update(medicine);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Medicine's details updated!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMedicine();

    }

    public void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent event) {

        String id = txtId.getText();

        try{
            boolean isDeleted = MedicineRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Medicine details deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMedicine();

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        root.getChildren().clear();
        root.getChildren().add(dashboardPane);

    }


    public void initialize() {
        setCellValueFactory();
        loadAllMedicine();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

    }

    private void loadAllMedicine() {
        ObservableList<MedicineTm> obList = FXCollections.observableArrayList();

        try {
            List<Medicine> medicineList = MedicineRepo.getAll();
            for (Medicine medicine : medicineList) {
                MedicineTm tm = new MedicineTm(
                        medicine.getId(),
                        medicine.getName(),
                        medicine.getType(),
                        medicine.getPrice(),
                        medicine.getDescription(),
                        medicine.getQtyOnHand()
                );

                obList.add(tm);
            }

            tblMedicine.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void txtMedicineIDOnKeyReleased(KeyEvent keyEvent) {
        MedicineRegex.setTextColour(MedicineTextField.ID,txtId);
    }

    public void txtMedicinePriceOnKeyReleased(KeyEvent keyEvent) {
        MedicineRegex.setTextColour(MedicineTextField.PRICE,txtPrice);
    }

    public void txtMedicineQtyOnHandOnKeyReleased(KeyEvent keyEvent) {
        MedicineRegex.setTextColour(MedicineTextField.QTYONHAND,txtQtyOnHand);
    }

    public boolean isValid(){
        if (!MedicineRegex.setTextColour(MedicineTextField.ID,txtId));
        if (!MedicineRegex.setTextColour(MedicineTextField.PRICE,txtPrice));
        if (!MedicineRegex.setTextColour(MedicineTextField.QTYONHAND,txtQtyOnHand));

        return true;
    }

    public void btnPrintReportOnAction(ActionEvent event) throws JRException, SQLException {

        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();


        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reports/MedicineReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        data.put("Date", dateString);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnectionPET.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }
}
