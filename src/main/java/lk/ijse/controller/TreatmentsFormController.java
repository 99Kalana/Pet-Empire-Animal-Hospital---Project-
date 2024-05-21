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
import lk.ijse.model.Treatments;
import lk.ijse.model.Veterinarian;
import lk.ijse.model.tm.MedicineTm;
import lk.ijse.model.tm.TreatmentsTm;
import lk.ijse.repository.MedicineRepo;
import lk.ijse.repository.TreatmentsRepo;
import lk.ijse.repository.VeterinarianRepo;
import lk.ijse.util.SupplierRegex;
import lk.ijse.util.SupplierTextField;
import lk.ijse.util.TreatmentsRegex;
import lk.ijse.util.TreatmentsTextField;
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

public class TreatmentsFormController {
    public AnchorPane root;

    public TextField txtId;
    public TextField txtType;
    public TextField txtDescription;
    public TableView<TreatmentsTm> tblTreatments;

    public TableColumn<?,?> colId;
    public TableColumn<?,?> colType;
    public TableColumn<?,?> colDescription;

    public void txtSearchOnAction(ActionEvent event) throws SQLException {

        String id = txtId.getText();

        Treatments treatments = TreatmentsRepo.searchById(id);

        if(treatments != null){
            txtId.setText(treatments.getId());
            txtType.setText(treatments.getType());
            txtDescription.setText(treatments.getDescription());

        }else{
            new Alert(Alert.AlertType.INFORMATION, "Treatments details not found!").show();
        }

    }

    public void btnSaveOnAction(ActionEvent event) {

        String id = txtId.getText();
        String type = txtType.getText();
        String description = txtDescription.getText();

        Treatments treatments = new Treatments(id, type, description);

        try{
            boolean isSaved = TreatmentsRepo.save(treatments);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Treatments details saved!").show();
                clearFields();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        loadAllTreatments();

    }

    private void clearFields(){

        txtId.setText("");
        txtType.setText("");
        txtDescription.setText("");

    }

    public void btnUpdateOnAction(ActionEvent event) {

        String id = txtId.getText();
        String type = txtType.getText();
        String description = txtDescription.getText();

        Treatments treatments = new Treatments(id, type, description);

        try{
            boolean isUpdated = TreatmentsRepo.update(treatments);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Treatments details updated!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllTreatments();

    }

    public void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent event) {

        String id = txtId.getText();

        try{
            boolean isDeleted = TreatmentsRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Treatments details deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllTreatments();

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        root.getChildren().clear();
        root.getChildren().add(dashboardPane);

    }

    public void initialize() {
        setCellValueFactory();
        loadAllTreatments();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

    private void loadAllTreatments() {
        ObservableList<TreatmentsTm> obList = FXCollections.observableArrayList();

        try {
            List<Treatments> treatmentsList = TreatmentsRepo.getAll();
            for (Treatments treatments : treatmentsList) {
                TreatmentsTm tm = new TreatmentsTm(
                        treatments.getId(),
                        treatments.getType(),
                        treatments.getDescription()
                );

                obList.add(tm);
            }

            tblTreatments.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtTreatmentIDOnKeyReleased(KeyEvent keyEvent) {

        TreatmentsRegex.setTextColour(TreatmentsTextField.ID,txtId);

    }

    public boolean isValid(){
        if (!SupplierRegex.setTextColour(SupplierTextField.ID,txtId));

        return true;
    }

    public void btnPrintReportOnAction(ActionEvent event) throws JRException, SQLException {

        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();


        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reports/TreatmentReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        data.put("Date", dateString);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnectionPET.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }
}
