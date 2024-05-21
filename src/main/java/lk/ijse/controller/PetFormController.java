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
import lk.ijse.model.Pet;
import lk.ijse.model.tm.MedicineTm;
import lk.ijse.model.tm.PetTm;
import lk.ijse.repository.MedicineRepo;
import lk.ijse.repository.PetRepo;
import lk.ijse.util.MedicineSupplierRegex;
import lk.ijse.util.MedicineSupplierTextField;
import lk.ijse.util.PetRegex;
import lk.ijse.util.PetTextField;
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

public class PetFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtBreed;
    public TextField txtWeight;
    public TextField txtColour;

    public AnchorPane root;
    public TableView<PetTm> tblPet;

    public TableColumn<?,?> colId;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colBreed;
    public TableColumn<?,?> colWeight;
    public TableColumn<?,?> colColour;

    public void btnSaveOnAction(ActionEvent actionEvent) {

        String id = txtId.getText();
        String name = txtName.getText();
        String breed = txtBreed.getText();
        double weight = Double.parseDouble(txtWeight.getText());
        String colour = txtColour.getText();

        Pet pet = new Pet(id, name, breed, weight, colour);

        try{
            boolean isSaved = PetRepo.save(pet);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Pet's details saved!").show();
                clearFields();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        loadAllPets();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        String id = txtId.getText();
        String name = txtName.getText();
        String breed = txtBreed.getText();
        double weight = Double.parseDouble(txtWeight.getText());
        String colour = txtColour.getText();

        Pet pet = new Pet(id, name, breed, weight, colour);

        try{
            boolean isUpdated = PetRepo.update(pet);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Pet's details updated!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllPets();

    }

    public void btnClearOnAction(ActionEvent actionEvent) {

        clearFields();

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        String id = txtId.getText();

        try{
            boolean isDeleted = PetRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Pet's details deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllPets();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {

        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        root.getChildren().clear();
        root.getChildren().add(dashboardPane);

    }

    private void clearFields(){
        txtId.setText("");
        txtName.setText("");
        txtBreed.setText("");
        txtWeight.setText("");
        txtColour.setText("");
    }


    public void txtSearchOnAction(ActionEvent event) throws SQLException {

        String id = txtId.getText();

        Pet pet = PetRepo.searchById(id);

        if(pet != null){
            txtId.setText(pet.getId());
            txtName.setText(pet.getName());
            txtBreed.setText(pet.getBreed());
            txtWeight.setText(Double.toString(pet.getWeight()));
            txtColour.setText(pet.getColour());
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Pet details not found!").show();
        }

    }


    public void initialize() {
        setCellValueFactory();
        loadAllPets();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBreed.setCellValueFactory(new PropertyValueFactory<>("breed"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colColour.setCellValueFactory(new PropertyValueFactory<>("colour"));

    }

    private void loadAllPets() {
        ObservableList<PetTm> obList = FXCollections.observableArrayList();

        try {
            List<Pet> petList = PetRepo.getAll();
            for (Pet pet : petList) {
                PetTm tm = new PetTm(
                        pet.getId(),
                        pet.getName(),
                        pet.getBreed(),
                        pet.getWeight(),
                        pet.getColour()
                );

                obList.add(tm);
            }

            tblPet.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void txtPetIDOnKeyReleased(KeyEvent keyEvent) {
        PetRegex.setTextColour(PetTextField.ID,txtId);
    }

    public void txtPetWeightOnKeyReleased(KeyEvent keyEvent) {
        PetRegex.setTextColour(PetTextField.WEIGHT,txtWeight);
    }

    public boolean isValid(){
        if (!PetRegex.setTextColour(PetTextField.ID,txtId));
        if (!PetRegex.setTextColour(PetTextField.WEIGHT,txtWeight));

        return true;
    }


    public void btnPrintReportOnAction(ActionEvent event) throws JRException, SQLException {

        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();


        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reports/PetReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        data.put("Date", dateString);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnectionPET.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }
}
