package lk.ijse.util;

import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrescriptionTreatmentRegex {
    public static boolean setTextColour(PrescriptionTreatmentTextField location, TextField textField) {

        if (PrescriptionTreatmentRegex.isTextFieldValid(location, textField.getText())) {
            textField.setStyle("-fx-background-color: SpringGreen");
            // Set unfocus color to white
            textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) { // When the TextField loses focus
                    textField.setStyle("-fx-background-color: White");
                }
            });
            return true;
        } else {
            textField.setStyle("-fx-background-color: Tomato");
            // Set unfocus color to white
            textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) { // When the TextField loses focus
                    textField.setStyle("-fx-background-color: White");
                }
            });
            return false;
        }

    }

    private static boolean isTextFieldValid(PrescriptionTreatmentTextField textField, String text) {

        String filled = "";

        switch (textField){

            case PID:
                filled = "^([P][S][0-9]{3})$";
                break;

            case TID:
                filled = "^([T][0-9]{3})$";
                break;

            case PRICE:
                filled = "^([0-9]){1,}[.]([0-9]){1,}$";
                break;

            case DATE:
                filled = "[1-9][0-9][0-9]{2}-([0][1-9]|[1][0-2])-([1-2][0-9]|[0][1-9]|[3][0-1])";
                break;

            case TIME:
                filled = "^(2[0-3]|[01]?[0-9]):([0-5]?[0-9])$";


        }

        Pattern pattern = Pattern.compile(filled);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }

        return false;

    }
}
