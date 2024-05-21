package lk.ijse.util;

import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterRegex {
    public static boolean setTextColour(RegisterTextField location, TextField textField) {

        if (RegisterRegex.isTextFieldValid(location, textField.getText())) {
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

    private static boolean isTextFieldValid(RegisterTextField textField, String text) {

        String filled = "";

        switch (textField){

            case USERID :
                filled = "^([U][0-9]{3})$";


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
