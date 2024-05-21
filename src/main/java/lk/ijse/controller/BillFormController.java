package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.*;
import lk.ijse.model.tm.BillFormTm;

import lk.ijse.repository.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.fxml.FXMLLoader.load;

public class BillFormController {
    public AnchorPane rootBill;
    public Label lblBillDate;
    public Label lblBillId;
    public Label lblClientName;
    public Label lblPetName;
    public Label lblVeterinarianName;
    public TextField txtBillQty;
    public JFXButton btnAddToBill;
    public Label lblMedicineName;
    public Label lblMedicineType;
    public Label lblMedicinePrice;
    public Label lblMedicineQtyOnHand;
    public TableView<BillFormTm> tblBill;
    public TableColumn<?,?> colBillType;
    public TableColumn<?,?> colTreatmentId;
    public TableColumn<?,?> colTreatmentType;
    public TableColumn<?,?> colTreatmentDescription;
    public TableColumn<?,?> colTreatmentPrice;
    public TableColumn<?,?> colMedicineId;
    public TableColumn<?,?> colMedicineName;
    public TableColumn<?,?> colMedicineType;
    public TableColumn<?,?> colMedicinePrice;
    public TableColumn<?,?> colBillQty;
    public TableColumn<?,?> colTotal;
    public TableColumn<?,?> colAction;
    public Label lblBillTotal;
    public JFXComboBox<String> cmbClientId;
    public JFXComboBox<String> cmbPetId;
    public JFXComboBox<String> cmbVeterinarianId;
    public JFXComboBox<String> cmbMedicineId;


    private ObservableList<BillFormTm> obList = FXCollections.observableArrayList();


    public void initialize(){
        setDate();
        getCurrentBillId();
        getClientIds();
        getPetIds();
        getVeterinarianIds();
        getMedicineIds();
        setCellValueFactory();

    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblBillDate.setText(String.valueOf(now));
    }
    private void getCurrentBillId() {
        try {
            String currentBillId = BillRepo.getCurrentBillId();

            String nextBillId = generateNextBillId(currentBillId);

            lblBillId.setText(nextBillId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextBillId(String currentBillId) {

       /* if(currentBillId != null) {
            String[] split = currentBillId.split("B");
            int idNum = Integer.parseInt(split[1]);
            return "B" + ++idNum;
        }
        return "B1";*/

        if (currentBillId != null) {
            String[] split = currentBillId.split("B");
            int idNum = Integer.parseInt(split[1]);
            if (idNum < 99) {
                idNum++;
                return "B" + String.format("%03d", idNum);
            } else {
                return "Error: Maximum bill ID reached (B999)";
            }
        }
        return "B001";

    }



    private void getClientIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = ClientRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            cmbClientId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void cmbClientOnAction(ActionEvent event) {

        String clientId = cmbClientId.getValue();

        try {
            Client client = ClientRepo.searchById(clientId);

            lblClientName.setText(client.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    private void getPetIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = PetRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            cmbPetId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbPetOnAction(ActionEvent event)  {

        String petId = cmbPetId.getValue();

        try {
            Pet pet = PetRepo.searchById(petId);

            lblPetName.setText(pet.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnNewClientOnAction(ActionEvent event) throws IOException {

       /* AnchorPane anchorPane = load(this.getClass().getResource("/view/client_form.fxml"));

        Stage stage = (Stage) this.rootBill.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));

        stage.setTitle("Client Form");

        stage.centerOnScreen();*/


        AnchorPane clientPane = FXMLLoader.load(this.getClass().getResource("/view/client_form.fxml"));

        rootBill.getChildren().clear();
        rootBill.getChildren().add(clientPane);



    }

    public void btnNewPetOnAction(ActionEvent event) throws IOException {

        /*AnchorPane anchorPane = load(this.getClass().getResource("/view/pet_form.fxml"));

        Stage stage = (Stage) this.rootBill.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));

        stage.setTitle("Pet Form");

        stage.centerOnScreen();*/

        AnchorPane petPane = FXMLLoader.load(this.getClass().getResource("/view/pet_form.fxml"));

        rootBill.getChildren().clear();
        rootBill.getChildren().add(petPane);

    }

    private void getVeterinarianIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = VeterinarianRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            cmbVeterinarianId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbVeterinarianOnAction(ActionEvent event) {
        String veterinarianId = cmbVeterinarianId.getValue();

        try {
            Veterinarian veterinarian = VeterinarianRepo.searchById(veterinarianId);

            lblVeterinarianName.setText(veterinarian.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void getMedicineIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = MedicineRepo.getIds();

            for (String id : idList) {
                obList.add(id);
            }
            cmbMedicineId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbMedicineOnAction(ActionEvent event) {

        String id = cmbMedicineId.getValue();

        try {
            Medicine medicine = MedicineRepo.searchById(id);
            if(medicine != null) {
                lblMedicineName.setText(medicine.getName());
                lblMedicineType.setText(medicine.getType());
                lblMedicinePrice.setText(String.valueOf(medicine.getPrice()));
                lblMedicineQtyOnHand.setText(String.valueOf(medicine.getQtyOnHand()));
            }

            txtBillQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void txtBillQtyOnAction(ActionEvent event) {
        btnAddToBillOnAction(event);
    }


    private void setCellValueFactory() {
        colMedicineId.setCellValueFactory(new PropertyValueFactory<>("mId"));
        colMedicineName.setCellValueFactory(new PropertyValueFactory<>("mName"));
        colMedicineType.setCellValueFactory(new PropertyValueFactory<>("mType"));
        colMedicinePrice.setCellValueFactory(new PropertyValueFactory<>("mPrice"));
        colBillQty.setCellValueFactory(new PropertyValueFactory<>("mQty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }



    public void btnAddToBillOnAction(ActionEvent event) {

        String mId = cmbMedicineId.getValue();
        String mName = lblMedicineName.getText();
        String mType = lblMedicineType.getText();
        double mPrice = Double.parseDouble(lblMedicinePrice.getText());
        int mQty = Integer.parseInt(txtBillQty.getText());
        double total = mQty * mPrice;

        JFXButton btnRemove = new JFXButton("REMOVE");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {

            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you want to Remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblBill.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblBill.refresh();
                calculateBillTotal();

            }
        });


        for (int i = 0; i < tblBill.getItems().size(); i++) {
            if(mId.equals(colMedicineId.getCellData(i))) {

                BillFormTm tm = obList.get(i);
                mQty += tm.getMQty();
                total = mQty * mPrice;

                tm.setMQty(mQty);

                tm.setTotal(total);

                tblBill.refresh();

                calculateBillTotal();
                return;
            }
        }


        BillFormTm tm = new BillFormTm(mId, mName, mType, mPrice, mQty, total, btnRemove);
        obList.add(tm);

        tblBill.setItems(obList);
        calculateBillTotal();
        txtBillQty.setText("");



    }

    private void calculateBillTotal() {
        int billTotal = 0;
        for (int i = 0; i < tblBill.getItems().size(); i++) {
            billTotal += (double) colTotal.getCellData(i);
        }
        lblBillTotal.setText(String.valueOf(billTotal));

    }



    public void btnPlaceBillOnAction(ActionEvent event) {



        String billId = lblBillId.getText();
        String clientId = cmbClientId.getValue();
        Date date = Date.valueOf(LocalDate.now());

        var bill = new Bill(billId,clientId,date);

        List<BillDetail> billList = new ArrayList<>();

        for(int i = 0; i < tblBill.getItems().size(); i++){

            BillFormTm tm = obList.get(i);

            BillDetail bd = new BillDetail(
                    billId,
                    tm.getMId(),
                    tm.getMQty(),
                    tm.getMPrice()
            );

            billList.add(bd);

        }

        PlaceBill pb = new PlaceBill(bill,billList);

        try {
            boolean isPlaced = PlaceBillRepo.placeBill(pb);
            if (isPlaced){
                new Alert(Alert.AlertType.CONFIRMATION, "Bill Placed!").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Bill Placed Unsuccessfully!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



    }





    /*public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        rootBill.getChildren().clear();
        rootBill.getChildren().add(dashboardPane);

    }*/


    public void btnBillRecordsOnAction(ActionEvent event) throws IOException {

        AnchorPane billRecordPane = FXMLLoader.load(this.getClass().getResource("/view/bill_record_form.fxml"));

        rootBill.getChildren().clear();
        rootBill.getChildren().add(billRecordPane);

    }

    public void btnBillDetailsOnAction(ActionEvent event) throws IOException {

        AnchorPane billDetailPane = FXMLLoader.load(this.getClass().getResource("/view/bill_detail_form.fxml"));

        rootBill.getChildren().clear();
        rootBill.getChildren().add(billDetailPane);

    }

    public void btnPrintBillOnAction(ActionEvent event) throws JRException, SQLException {

        calculateBillTotal();
        int totalPrice = Integer.parseInt(lblBillTotal.getText());
        String billId = lblBillId.getText();
        String date = lblBillDate.getText();
        String clientId = cmbClientId.getValue();
        String veterinarianId = cmbVeterinarianId.getValue();

        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reports/PlaceBill.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        data.put("BillID", billId);
        data.put("Date",date);
        data.put("ClientID",clientId);
        data.put("VeterinarianID",veterinarianId);
        data.put("TotalPrice", totalPrice);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnectionPET.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }



}
