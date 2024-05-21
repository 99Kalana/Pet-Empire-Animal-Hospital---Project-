package lk.ijse.util;

import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VeterinarianRegex {
    public static boolean setTextColour(VeterinarianTextField location, TextField textField) {

        if (VeterinarianRegex.isTextFieldValid(location, textField.getText())) {
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

    private static boolean isTextFieldValid(VeterinarianTextField textField, String text) {

        String filled = "";

        switch (textField){

            case ID:
                filled = "^([V][0-9]{3})$";
                break;

            case CONTACTNO:
                filled = "^([0])([1-9]{2})([0-9]){7}$";
                break;

            case EMAIL:
                filled = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";

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
