package lk.ijse.util;

import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MedicineRegex {
    public static boolean setTextColour(MedicineTextField location, TextField textField) {
        if (MedicineRegex.isTextFieldValid(location, textField.getText())) {
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

    private static boolean isTextFieldValid(MedicineTextField textField, String text) {
        String filled = "";

        switch (textField){

            case ID:
                filled = "^([M][0-9]{3})$";
                break;

            case PRICE:
                filled = "^([0-9]){1,}[.]([0-9]){1,}$";
                break;

            case QTYONHAND:
                filled = "^[0-9]+$";


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
