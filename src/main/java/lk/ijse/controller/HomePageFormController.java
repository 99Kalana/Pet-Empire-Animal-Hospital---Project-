package lk.ijse.controller;

import com.github.plushaze.traynotification.notification.Notifications;
import com.github.plushaze.traynotification.notification.TrayNotification;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.fxml.FXMLLoader.load;

public class HomePageFormController {
    public AnchorPane homeNode;
    public AnchorPane viewNode;
    public JFXButton btnDashboard;
    public JFXButton btnPet;
    public JFXButton btnClient;
    public JFXButton btnAppointment;
    public JFXButton btnVeterinarian;
    public JFXButton btnPrescription;
    public JFXButton btnTreatments;
    public JFXButton btnPresTreat;
    public JFXButton btnPresMed;
    public JFXButton btnSupplier;
    public JFXButton btnMedicine;
    public JFXButton btnMedSup;
    public JFXButton btnBills;
    private JFXButton selectedButton;

    private String userId;


    public void initialize() throws IOException, SQLException {
        loginNotification();
        setUserId(userId);

    }


    private void loginNotification() {

        String title = "Welcome!!!";
        String message = "Login Succeed!!!";

        Notifications notification = Notifications.SUCCESS;
        TrayNotification tray = new TrayNotification(title, message, notification);
        tray.showAndWait();

    }


    public void setUserId(String userId) throws SQLException, IOException {
        this.userId=userId;
        loadDashboard(userId);
    }



    private void loadDashboard(String userId) throws IOException, SQLException {

        //AnchorPane petPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
        AnchorPane petPane = loader.load();
        DashboardFormController controller = loader.getController();
        controller.setUserId(userId);


        viewNode.getChildren().clear();
        viewNode.getChildren().add(petPane);

        selectButton(btnDashboard);

    }

    private void selectButton(JFXButton button) {
        // Reset style of previously selected button
        if (selectedButton != null) {
            selectedButton.setStyle("-fx-background-color: #0984e3");
        }

        // Set style for the selected button
        button.setStyle("-fx-background-color: #95a5a6");
        selectedButton = button;
    }

    public void btnPetFormOnAction(ActionEvent event) throws IOException {

        AnchorPane petPane = FXMLLoader.load(this.getClass().getResource("/view/pet_form.fxml"));

        viewNode.getChildren().clear();
        viewNode.getChildren().add(petPane);

        selectButton(btnPet);

    }

    public void btnClientFormOnAction(ActionEvent event) throws IOException {

        AnchorPane clientPane = FXMLLoader.load(this.getClass().getResource("/view/client_form.fxml"));

        viewNode.getChildren().clear();
        viewNode.getChildren().add(clientPane);

        selectButton(btnClient);

    }

    public void btnAppointmentFormOnAction(ActionEvent event) throws IOException {

        AnchorPane appointmentPane = FXMLLoader.load(this.getClass().getResource("/view/appoinment_form.fxml"));

        viewNode.getChildren().clear();
        viewNode.getChildren().add(appointmentPane);

        selectButton(btnAppointment);

    }

    public void btnVeterinarianFormOnAction(ActionEvent event) throws IOException {

        AnchorPane veterinarianPane = FXMLLoader.load(this.getClass().getResource("/view/veterinarian_form.fxml"));

        viewNode.getChildren().clear();
        viewNode.getChildren().add(veterinarianPane);

        selectButton(btnVeterinarian);

    }

    public void btnPrescriptionFormOnAction(ActionEvent event) throws IOException {

        AnchorPane prescriptionPane = FXMLLoader.load(this.getClass().getResource("/view/prescription_form.fxml"));

        viewNode.getChildren().clear();
        viewNode.getChildren().add(prescriptionPane);

        selectButton(btnPrescription);

    }

    public void btnTreatmentsFormOnAction(ActionEvent event) throws IOException {

        AnchorPane treatmentsPane = FXMLLoader.load(this.getClass().getResource("/view/treatments_form.fxml"));

        viewNode.getChildren().clear();
        viewNode.getChildren().add(treatmentsPane);

        selectButton(btnTreatments);

    }

    public void btnPrescriptionTreatmentFormOnAction(ActionEvent event) throws IOException {

        AnchorPane prescriptionTreatmentPane = FXMLLoader.load(this.getClass().getResource("/view/prescription_treatment_form.fxml"));

        viewNode.getChildren().clear();
        viewNode.getChildren().add(prescriptionTreatmentPane);

        selectButton(btnPresTreat);

    }

    public void btnPrescriptionMedicineFormOnAction(ActionEvent event) throws IOException {

        AnchorPane prescriptionMedicinePane = FXMLLoader.load(this.getClass().getResource("/view/prescription_medicine_form.fxml"));

        viewNode.getChildren().clear();
        viewNode.getChildren().add(prescriptionMedicinePane);

        selectButton(btnPresMed);

    }

    public void btnSupplierFormOnAction(ActionEvent event) throws IOException {

        AnchorPane supplierPane = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));

        viewNode.getChildren().clear();
        viewNode.getChildren().add(supplierPane);

        selectButton(btnSupplier);

    }

    public void btnMedicineFormOnAction(ActionEvent event) throws IOException {

        AnchorPane medicinePane = FXMLLoader.load(this.getClass().getResource("/view/medicine_form.fxml"));

        viewNode.getChildren().clear();
        viewNode.getChildren().add(medicinePane);

        selectButton(btnMedicine);

    }

    public void btnMedicineSupplierFormOnAction(ActionEvent event) throws IOException {

        AnchorPane supplierMedicinePane = FXMLLoader.load(this.getClass().getResource("/view/medicine_supplier_form.fxml"));

        viewNode.getChildren().clear();
        viewNode.getChildren().add(supplierMedicinePane);

        selectButton(btnMedSup);

    }

    public void btnBillFormOnAction(ActionEvent event) throws IOException {

        AnchorPane billPane = FXMLLoader.load(this.getClass().getResource("/view/bill_form.fxml"));

        viewNode.getChildren().clear();
        viewNode.getChildren().add(billPane);

        selectButton(btnBills);

    }

    public void btnLogOutOnAction(ActionEvent event) throws IOException {

        AnchorPane anchorPane = load(this.getClass().getResource("/view/login_form.fxml"));

        Stage stage = (Stage) this.homeNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));

        stage.setTitle("Login Form");

        stage.centerOnScreen();

    }

    public void btnDashboardOnAction(ActionEvent event) throws IOException {

        AnchorPane petPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        viewNode.getChildren().clear();
        viewNode.getChildren().add(petPane);

        selectButton(btnDashboard);

        try {
            loadDashboard(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
