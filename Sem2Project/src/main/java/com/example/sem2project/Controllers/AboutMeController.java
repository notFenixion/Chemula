package com.example.sem2project.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class AboutMeController implements Initializable {
    @FXML
    private Label aboutMeText;
    @FXML
    private ComboBox languageComboBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String[] languages = new String[]{"English", "Chinese", "Japanese", "Korean"};
        languageComboBox.getItems().removeAll(languageComboBox.getItems());
        languageComboBox.getItems().addAll(FXCollections.observableArrayList(languages));
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Locale locale;
                ResourceBundle bundle;
                if (languageComboBox.getValue().toString().equals("English")) {
                    locale = new Locale("en", "US");
                    bundle = ResourceBundle.getBundle("com/example/sem2project/LanguageBundle", locale);
                    aboutMeText.setText(bundle.getString("text"));
                    System.out.println("Language changed to English");
                }
                if (languageComboBox.getValue().toString().equals("Chinese")) {
                    locale = new Locale("zh", "CN");
                    bundle = ResourceBundle.getBundle("com/example/sem2project/LanguageBundle", locale);
                    aboutMeText.setText(bundle.getString("text"));
                    System.out.println("Language changed to Chinese");
                }


                switch (languageComboBox.getValue().toString()) {

                    case "Japanese":
                        locale = new Locale("ja", "JP");
                        bundle = ResourceBundle.getBundle("com/example/sem2project/LanguageBundle", locale);
                        aboutMeText.setText(bundle.getString("text"));
                        System.out.println("Language changed to Japanese");
                        break;

                    case "Korean":
                        locale = new Locale("ko", "KR");
                        bundle = ResourceBundle.getBundle("com/example/sem2project/LanguageBundle", locale);
                        aboutMeText.setText(bundle.getString("text"));
                        System.out.println("Language changed to Korean");
                        break;
                }
            }
        };
        languageComboBox.setOnAction(event);
    }
}
