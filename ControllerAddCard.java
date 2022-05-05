package sample.controllers;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import sample.database.CardTableData;
import sample.database.DatabaseHandler;
import org.apache.log4j.Logger;

public class ControllerAddCard {
    private static final Logger log = Logger.getLogger(String.valueOf(ControllerAddCard.class));

    @FXML
    private Label errorLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label secondNameLabel;
    @FXML
    private Label birthdateLabel;
    @FXML
    private Label lnipaLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField secondNameTextField;
    @FXML
    private TextField birthdateTextField;
    @FXML
    private TextField lnipaTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button applyButton;
    @FXML
    private Button cancelButton;
    DatabaseHandler databaseHandler = new DatabaseHandler();
    CardTableData cardData = new CardTableData();
    boolean nameSet;
    boolean lastNameSet;
    boolean secondNameSet;
    boolean birthdateSet;
    boolean lnipaSet;
    boolean phoneNumberSet;

    public ControllerAddCard() {
        BasicConfigurator.configure();
    }

    @FXML
    void initialize() {
        this.addButton.setOnAction((actionEvent) -> {
            this.errorLabel.setText("");
            String name = this.nameTextField.getText();
            String lastName = this.lastNameTextField.getText();
            String secondName = this.secondNameTextField.getText();
            String birthdate = this.birthdateTextField.getText();
            String lnipa = this.lnipaTextField.getText();
            String phoneNumber = this.phoneNumberTextField.getText();
            if (!name.equals("")) {
                this.cardData.setPatientName(name);
                this.nameLabel.setText(String.valueOf(this.cardData.getPatientName()));
                this.nameSet = true;
                log.info("successfully Name");
            }

            if (!lastName.equals("")) {
                this.cardData.setPatientLastName(lastName);
                this.lastNameLabel.setText(String.valueOf(this.cardData.getPatientLastName()));
                this.lastNameSet = true;
                log.info("successfully Last Name");
            }

            if (!secondName.equals("")) {
                this.cardData.setPatientSecondName(secondName);
                this.secondNameLabel.setText(String.valueOf(this.cardData.getPatientSecondName()));
                this.secondNameSet = true;
                log.info("successfully Second name");
            }

            if (!birthdate.equals("")) {
                this.cardData.setBirthdate(birthdate);
                this.birthdateLabel.setText(String.valueOf(this.cardData.getBirthdate()));
                this.birthdateSet = true;
                log.info("successfully Birthdate");
            }

            if (!lnipa.equals("")) {
                this.cardData.setLnipa(lnipa);
                this.lnipaLabel.setText(String.valueOf(this.cardData.getLnipa()));
                this.lnipaSet = true;
                log.info("successfully Lnipa");
            }

            if (!phoneNumber.equals("")) {
                this.cardData.setPhoneNumber(phoneNumber);
                this.phoneNumberLabel.setText(String.valueOf(this.cardData.getPhoneNumber()));
                this.phoneNumberSet = true;
                log.info("successfully Phone number");
            }

        });
        this.applyButton.setOnAction((actionEvent) -> {
            if (this.nameSet && this.lastNameSet && this.secondNameSet && this.birthdateSet && this.lnipaSet && this.phoneNumberSet) {
                try {
                    this.databaseHandler.addCard(this.cardData);
                    log.info("successfully Add Card");
                } catch (ClassNotFoundException | SQLException var3) {
                    var3.printStackTrace();
                    log.info("error");
                }

                this.openDesktopWindow();
            } else {
                this.errorLabel.setText("Не все поля заполнены");
                log.info("error");
            }

        });
        this.cancelButton.setOnAction((actionEvent) -> {
            this.openDesktopWindow();
        });
    }

    private void openDesktopWindow() {
        this.cancelButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/sample/layout/desktop.fxml"));

        try {
            loader.load();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        Parent root = (Parent)loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}