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
import lk.ijse.model.BillRecord;
import lk.ijse.model.BillsDetails;
import lk.ijse.model.tm.BillRecordTm;
import lk.ijse.model.tm.BillsDetailsTm;
import lk.ijse.repository.BillRecordRepo;
import lk.ijse.repository.BillsDetailsRepo;
import lk.ijse.util.AppointmentRegex;
import lk.ijse.util.AppointmentTextField;
import lk.ijse.util.BillDetailRegex;
import lk.ijse.util.BillDetailTextField;
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

public class BillDetailFormController {
    
    public AnchorPane root;
    
    public TextField txtId;
    public TextField txtMedicineId;
    public TextField txtMedicineQty;
    public TextField txtMedicinePrice;
    public TableView<BillsDetailsTm> tblBillDetails;
    
    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colMedicineId;
    public TableColumn<?, ?> colMedicineQty;
    public TableColumn<?, ?> colMedicinePrice;


    public void txtSearchOnAction(ActionEvent event) throws SQLException {

        String id = txtId.getText();

        BillsDetails billsDetails = BillsDetailsRepo.searchById(id);

        if(billsDetails != null){
            txtId.setText(billsDetails.getBillId());
            txtMedicineId.setText(billsDetails.getMedicineId());
            txtMedicineQty.setText(String.valueOf(billsDetails.getMedicineQty()));
            txtMedicinePrice.setText(String.valueOf(billsDetails.getMedicinePrice()));


        }else{
            new Alert(Alert.AlertType.INFORMATION, "Bill Details not found!").show();
        }

    }

    private void clearFields(){

        txtId.setText("");
        txtMedicineId.setText("");
        txtMedicineQty.setText("");
        txtMedicinePrice.setText("");

    }

    public void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent event) {

        String id = txtId.getText();

        try{
            boolean isDeleted = BillsDetailsRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Bill Detail deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllBillDetails();

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane billDetailPane = FXMLLoader.load(this.getClass().getResource("/view/bill_form.fxml"));

        root.getChildren().clear();
        root.getChildren().add(billDetailPane);

    }


    public void initialize() {
        setCellValueFactory();
        loadAllBillDetails();
    }

    private void setCellValueFactory() {

        colId.setCellValueFactory(new PropertyValueFactory<>("billId"));
        colMedicineId.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        colMedicineQty.setCellValueFactory(new PropertyValueFactory<>("medicineQty"));
        colMedicinePrice.setCellValueFactory(new PropertyValueFactory<>("medicinePrice"));

    }

    private void loadAllBillDetails() {
        ObservableList<BillsDetailsTm> obList = FXCollections.observableArrayList();

        try {
            List<BillsDetails> billsDetailsList = BillsDetailsRepo.getAll();
            for (BillsDetails billsDetails : billsDetailsList) {
                BillsDetailsTm tm = new BillsDetailsTm(
                        billsDetails.getBillId(),
                        billsDetails.getMedicineId(),
                        billsDetails.getMedicineQty(),
                        billsDetails.getMedicinePrice()
                );

                obList.add(tm);
            }

            tblBillDetails.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void txtBillIDOnKeyReleased(KeyEvent keyEvent) {
        BillDetailRegex.setTextColour(BillDetailTextField.BILLID,txtId);
    }

    public boolean isValid(){
        if (!BillDetailRegex.setTextColour(BillDetailTextField.BILLID,txtId));
        return true;
    }

    public void btnPrintReportOnAction(ActionEvent event) throws JRException, SQLException {

        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();


        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reports/BillDetailReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        data.put("Date", dateString);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnectionPET.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }


}
