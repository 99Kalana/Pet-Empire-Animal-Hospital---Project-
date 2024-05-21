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
import lk.ijse.model.Supplier;
import lk.ijse.model.tm.MedicineTm;
import lk.ijse.model.tm.SupplierTm;
import lk.ijse.repository.MedicineRepo;
import lk.ijse.repository.SupplierRepo;
import lk.ijse.util.QrRegex;
import lk.ijse.util.QrTextField;
import lk.ijse.util.SupplierRegex;
import lk.ijse.util.SupplierTextField;
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

public class SupplierFormController {
    
    public AnchorPane root;
    
    public TextField txtId;
    
    public TextField txtName;
    public TextField txtContactNo;
    public TextField txtLocation;
    public TextField txtEmail;
    public TextField txtProductType;
    public TextField txtQtyOnHand;
    public TableView<SupplierTm> tblSupplier;

    public TableColumn<?,?> colId;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colContactNo;
    public TableColumn<?,?> colLocation;
    public TableColumn<?,?> colEmail;
    public TableColumn<?,?> colProductType;
    public TableColumn<?,?> colQtyOnHand;


    public void txtSearchOnAction(ActionEvent event) throws SQLException {

        String id = txtId.getText();

        Supplier supplier = SupplierRepo.searchById(id);

        if(supplier != null){

            txtId.setText(supplier.getId());
            txtName.setText(supplier.getName());
            txtContactNo.setText(Integer.toString(supplier.getContactNo()));
            txtLocation.setText(supplier.getLocation());
            txtEmail.setText(supplier.getEmail());
            txtProductType.setText(supplier.getProductType());
            txtQtyOnHand.setText(Integer.toString(supplier.getQtyOnHand()));


        }else{
            new Alert(Alert.AlertType.INFORMATION, "Supplier details not found!").show();
        }

    }

    public void btnSaveOnAction(ActionEvent event) {

        String id = txtId.getText();
        String name = txtName.getText();
        int contactNo = Integer.parseInt(txtContactNo.getText());
        String location = txtLocation.getText();
        String email = txtEmail.getText();
        String productType = txtProductType.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        Supplier supplier = new Supplier(id, name, contactNo, location, email, productType, qtyOnHand);

        try{
            boolean isSaved = SupplierRepo.save(supplier);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier details saved!").show();
                clearFields();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        loadAllSuppliers();

    }

    private void clearFields(){

        txtId.setText("");
        txtName.setText("");
        txtContactNo.setText("");
        txtLocation.setText("");
        txtEmail.setText("");
        txtProductType.setText("");
        txtQtyOnHand.setText("");

    }

    public void btnUpdateOnAction(ActionEvent event) {

        String id = txtId.getText();
        String name = txtName.getText();
        int contactNo = Integer.parseInt(txtContactNo.getText());
        String location = txtLocation.getText();
        String email = txtEmail.getText();
        String productType = txtProductType.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        Supplier supplier = new Supplier(id, name, contactNo, location, email, productType, qtyOnHand);

        try{
            boolean isUpdated = SupplierRepo.update(supplier);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier details updated!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllSuppliers();

    }

    public void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent event) {

        String id = txtId.getText();

        try{
            boolean isDeleted = SupplierRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier details deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllSuppliers();

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        root.getChildren().clear();
        root.getChildren().add(dashboardPane);

    }

    public void initialize() {
        setCellValueFactory();
        loadAllSuppliers();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colProductType.setCellValueFactory(new PropertyValueFactory<>("productType"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

    }

    private void loadAllSuppliers() {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<Supplier> supplierList = SupplierRepo.getAll();
            for (Supplier supplier : supplierList) {
                SupplierTm tm = new SupplierTm (
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getContactNo(),
                        supplier.getLocation(),
                        supplier.getEmail(),
                        supplier.getProductType(),
                        supplier.getQtyOnHand()
                );

                obList.add(tm);
            }

            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtSupplierIDOnKeyReleased(KeyEvent keyEvent) {
        SupplierRegex.setTextColour(SupplierTextField.ID,txtId);
    }

    public void txtContactNoOnKeyReleased(KeyEvent keyEvent) {
        SupplierRegex.setTextColour(SupplierTextField.CONTACTNO,txtContactNo);
    }

    public void txtSupplierEmailOnKeyReleased(KeyEvent keyEvent) {
        SupplierRegex.setTextColour(SupplierTextField.EMAIL,txtEmail);
    }

    public void txtQtyOnHandOnKeyReleased(KeyEvent keyEvent) {
        SupplierRegex.setTextColour(SupplierTextField.QTYONHAND,txtQtyOnHand);
    }

    public boolean isValid(){
        if (!SupplierRegex.setTextColour(SupplierTextField.ID,txtId));
        if (!SupplierRegex.setTextColour(SupplierTextField.CONTACTNO,txtContactNo));
        if (!SupplierRegex.setTextColour(SupplierTextField.EMAIL,txtEmail));
        if (!SupplierRegex.setTextColour(SupplierTextField.QTYONHAND,txtQtyOnHand));

        return true;
    }

    public void btnPrintReportOnAction(ActionEvent event) throws JRException, SQLException {

        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();


        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reports/SupplierReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        data.put("Date", dateString);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnectionPET.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }
}
