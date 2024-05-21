package lk.ijse.controller;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.model.Client;
import lk.ijse.model.Qr;
import lk.ijse.repository.ClientRepo;
import lk.ijse.util.PrescriptionTreatmentRegex;
import lk.ijse.util.PrescriptionTreatmentTextField;
import lk.ijse.util.QrRegex;
import lk.ijse.util.QrTextField;
//import lk.ijse.Repo.QrRepo;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class QrFormController {
    public AnchorPane rootQr;
    public AnchorPane paneQr;
    public Label lblQR;
    public TextField txtClientContact;
    public TextField txtClientEmail;
    public TextField txtClientName;
    public TextField txtPetId;
    public TextField txtClientId;

    private String qrPath;



    public void btnBrowseQrOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(298);
                imageView.setFitHeight(264);
                paneQr.getChildren().add(imageView);
                qrPath = file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void btnReadQrOnAction(ActionEvent event) {


        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hintmap = new EnumMap<>(EncodeHintType.class);
        hintmap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        try {
            String data = QRCodeReader(qrPath, charset);
            String [] datas = data.split("\n");
            txtClientId.setText(datas[4]);
            txtPetId.setText(datas[3]);
            txtClientName.setText(datas[2]);
            txtClientEmail.setText(datas[1]);
            txtClientContact.setText(datas[0]);
        } catch (IOException e) {
            Logger.getLogger(QrFormController.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotFoundException e) {
            Logger.getLogger(QrFormController.class.getName()).log(Level.SEVERE, null, e);
        }


    }

    /*public void btnGenerateQrOnAction(ActionEvent event) {

        String clientContact = txtClientContact.getText();
        String clientEmail = txtClientEmail.getText();
        String clientName = txtClientName.getText();
        String petId = txtPetId.getText();
        String clientId = txtClientId.getText();

        String data = clientContact + "\n" + clientEmail + "\n" + clientName + "\n" + petId + "\n" + clientId;

        // Path where the QR code image will be saved
        String path = "C:\\Users\\Karl\\Desktop\\QRProjec\\qr_code.png";

        // Charset for encoding the data
        String charset = "UTF-8";

        // Height and width of the QR code
        int height = 264;
        int width = 298;

        generateqrcode(data, path, charset, height, width);

        //Qr qr1 = new Qr(clientId, petId, clientName, clientEmail, Integer.parseInt(clientContact));
        //saveToDatabase(qr1);

    }*/

    public void btnGenerateQrOnAction(ActionEvent event) {
        String clientContact = txtClientContact.getText();
        String clientEmail = txtClientEmail.getText();
        String clientName = txtClientName.getText();
        String petId = txtPetId.getText();
        String clientId = txtClientId.getText();

        String data = clientContact + "\n" + clientEmail + "\n" + clientName + "\n" + petId + "\n" + clientId;


        String directoryPath = "C:\\Users\\Karl\\Desktop\\QRProjec";


        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }


        int count = 1;
        String filename = "QRCode" + count + ".png";
        while (Files.exists(Paths.get(directoryPath, filename))) {
            count++;
            filename = "QRCode" + count + ".png";
        }

        String path = directoryPath + File.separator + filename;

        String charset = "UTF-8";

        int height = 264;
        int width = 298;

        generateqrcode(data, path, charset, height, width);


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("QR Code Saved");
        alert.setHeaderText(null);
        alert.setContentText("QR code image saved as: " + path);
        alert.showAndWait();

    }

    /*private void saveToDatabase(Qr qr1) throws NoSuchFieldError {

        try {
            QrRepo.save(qr1);
        } catch (SQLException e) {
            Logger.getLogger(QrFormController.class.getName()).log(Level.SEVERE, null, e);
        }

    }*/


    private void generateqrcode(String data, String path, String charset, int height, int width) {

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
            File qrFile = new File(path);
            MatrixToImageWriter.writeToPath(matrix, "PNG", qrFile.toPath());

            WritableImage qrImage = SwingFXUtils.toFXImage(ImageIO.read(qrFile), null);

            lblQR.setGraphic(new ImageView(qrImage));
        } catch (UnsupportedEncodingException | WriterException e) {
            Logger.getLogger(QrFormController.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(QrFormController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    private String QRCodeReader(String path, String charset) throws FileNotFoundException,IOException, NotFoundException {

        Map<DecodeHintType,Object> hintmap = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        hintmap.put(DecodeHintType.PURE_BARCODE,Boolean.TRUE);
        BinaryBitmap binarybm = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(path)))));
        Result result = new MultiFormatReader().decode(binarybm, hintmap);
        return result.getText();


    }
    public void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtClientId.setText("");
        txtPetId.setText("");
        txtClientName.setText("");
        txtClientEmail.setText("");
        txtClientContact.setText("");
        paneQr.getChildren().clear();

    }

    /*public void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        rootQr.getChildren().clear();
        rootQr.getChildren().add(dashboardPane);

    }*/

    public void txtClientContactOnKeyReleased(KeyEvent keyEvent) {
        QrRegex.setTextColour(QrTextField.CLIENTCONTACTNO,txtClientContact);
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        QrRegex.setTextColour(QrTextField.CLIENTEMAIL,txtClientEmail);
    }

    public void txtPetIDOnKeyReleased(KeyEvent keyEvent) {
        QrRegex.setTextColour(QrTextField.PETID,txtPetId);
    }

    public void txtClientIDOnKeyReleased(KeyEvent keyEvent) {
        QrRegex.setTextColour(QrTextField.CLIENTID,txtClientId);
    }

    public boolean isValid(){
        if (!QrRegex.setTextColour(QrTextField.CLIENTID,txtClientId));
        if (!QrRegex.setTextColour(QrTextField.PETID,txtPetId));
        if (!QrRegex.setTextColour(QrTextField.CLIENTEMAIL,txtClientEmail));
        if (!QrRegex.setTextColour(QrTextField.CLIENTCONTACTNO,txtClientContact));

        return true;
    }

    public void txtSearchOnAction(ActionEvent event) throws SQLException {

        String id = txtClientId.getText();

        Client client = ClientRepo.searchById(id);

        if(client != null){
            txtClientId.setText(client.getId());
            txtPetId.setText(client.getPetId());
            txtClientName.setText(client.getName());
            txtClientEmail.setText(client.getEmail());
            txtClientContact.setText(Integer.toString(client.getContactNo()));
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Client details not found!").show();
        }


    }
}
