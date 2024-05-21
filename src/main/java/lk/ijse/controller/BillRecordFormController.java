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
import lk.ijse.model.Client;
import lk.ijse.model.tm.BillRecordTm;
import lk.ijse.model.tm.ClientTm;
import lk.ijse.repository.BillRecordRepo;
import lk.ijse.repository.ClientRepo;
import lk.ijse.util.AppointmentRegex;
import lk.ijse.util.AppointmentTextField;
import lk.ijse.util.BillRecordRegex;
import lk.ijse.util.BillRecordTextField;
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

public class BillRecordFormController {
    public AnchorPane root;

    public TextField txtId;
    public TextField txtClientId;
    public TextField txtDate;
    public TableView<BillRecordTm> tblBillRecords;

    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colClientId;
    public TableColumn<?, ?> colDate;

    public void txtSearchOnAction(ActionEvent event) throws SQLException {

        String id = txtId.getText();

        BillRecord billRecord = BillRecordRepo.searchById(id);

        if(billRecord != null){
            txtId.setText(billRecord.getBillId());
            txtClientId.setText(billRecord.getClientId());
            txtDate.setText(billRecord.getDate());

        }else{
            new Alert(Alert.AlertType.INFORMATION, "Bill Records not found!").show();
        }

    }

    private void clearFields(){

        txtId.setText("");
        txtClientId.setText("");
        txtDate.setText("");

    }

    public void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent event) {

        String id = txtId.getText();

        try{
            boolean isDeleted = BillRecordRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Bill Record deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadBillRecords();

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane billRecordPane = FXMLLoader.load(this.getClass().getResource("/view/bill_form.fxml"));

        root.getChildren().clear();
        root.getChildren().add(billRecordPane);

    }

    public void initialize() {
        setCellValueFactory();
        loadBillRecords();
    }

    private void setCellValueFactory() {

        colId.setCellValueFactory(new PropertyValueFactory<>("billId"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    private void loadBillRecords() {
        ObservableList<BillRecordTm> obList = FXCollections.observableArrayList();

        try {
            List<BillRecord> billRecordList = BillRecordRepo.getAll();
            for (BillRecord billRecord : billRecordList) {
                BillRecordTm tm = new BillRecordTm(
                        billRecord.getBillId(),
                        billRecord.getClientId(),
                        billRecord.getDate()
                );

                obList.add(tm);
            }

            tblBillRecords.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtBillIDOnKeyReleased(KeyEvent keyEvent) {
        BillRecordRegex.setTextColour(BillRecordTextField.BILLID,txtId);
    }

    public boolean isValid(){
        if (!BillRecordRegex.setTextColour(BillRecordTextField.BILLID,txtId));

        return true;
    }


    public void btnPrintReportOnAction(ActionEvent event) throws JRException, SQLException {

        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();


        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reports/BillRecordsReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        data.put("Date", dateString);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnectionPET.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }
}
