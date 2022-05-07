package sample.controllers;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.database.CardTableData;
import sample.database.DatabaseHandler;
import sample.database.ScheduleTableData;
import sample.database.User;
/**
 * класс ControllerDesktop для реализации главного окна приложения
 * @author Alina
 * @version 1.0
 */
public class ControllerDesktop {
    @FXML
    private TableView<CardTableData> cardTable;
    @FXML
    private TableColumn<CardTableData, Integer> cardId;
    @FXML
    private TableColumn<CardTableData, String> patientName;
    @FXML
    private TableColumn<CardTableData, String> patientLastName;
    @FXML
    private TableView<ScheduleTableData> scheduleTable;
    @FXML
    private TableColumn<ScheduleTableData, String> doctorData;
    @FXML
    private TableColumn<ScheduleTableData, String> doctor;
    @FXML
    private TableColumn<ScheduleTableData, String> doctorName;
    @FXML
    private TextField cardIdTextField;
    @FXML
    private Button openButton;
    @FXML
    private Button deleteCardButton;
    @FXML
    private Button addCardButton;
    @FXML
    private Button editCardButton;
    @FXML
    private Button exitButton;
    DatabaseHandler databaseHandler = new DatabaseHandler();

    public ControllerDesktop() {
    }
    /**
     * метод для инициализации всех элементов и разметок
     */
    @FXML
    void initialize() {
        this.cardId.setCellValueFactory(new PropertyValueFactory("id"));
        this.patientName.setCellValueFactory(new PropertyValueFactory("patientName"));
        this.patientLastName.setCellValueFactory(new PropertyValueFactory("patientLastName"));
        this.doctorData.setCellValueFactory(new PropertyValueFactory("doctorData"));
        this.doctor.setCellValueFactory(new PropertyValueFactory("doctor"));
        this.doctorName.setCellValueFactory(new PropertyValueFactory("doctorName"));
        this.updateCardTable();
        this.updateScheduleTable();
        boolean role = User.getRole().equals("doctor");
        this.openButton.setVisible(role);
        this.deleteCardButton.setVisible(role);
        this.editCardButton.setVisible(role);
        this.cardIdTextField.setVisible(role);
        this.openButton.setOnAction((actionEvent) -> {
            try {
                User.setEditedCardId(Integer.parseInt(this.cardIdTextField.getText()));
                this.openOtherWindow("/sample/layout/open_card.fxml");
            } catch (Exception var3) {
            }

        });
        this.deleteCardButton.setOnAction((actionEvent) -> {
            this.databaseHandler.deleteCard(Integer.parseInt(this.cardIdTextField.getText()));
            this.updateCardTable();
        });
        this.editCardButton.setOnAction((actionEvent) -> {
            try {
                User.setEditedCardId(Integer.parseInt(this.cardIdTextField.getText()));
                this.openOtherWindow("/sample/layout/edit_card.fxml");
            } catch (Exception var3) {
            }

        });
        this.addCardButton.setOnAction((actionEvent) -> {
            this.openOtherWindow("/sample/layout/add_card.fxml");
        });
        this.exitButton.setOnAction((actionEvent) -> {
            this.openOtherWindow("/sample/layout/authorization.fxml");
        });
    }
    /**
     * метод для изменения карты
     */
    private void updateCardTable() {
        try {
            this.cardTable.setItems(this.databaseHandler.returnCards("SELECT * FROM praktika.cards"));
        } catch (ClassNotFoundException | SQLException var2) {
            var2.printStackTrace();
        }

    }
    /**
     * метод для изменения расписания
     */
    private void updateScheduleTable() {
        try {
            this.scheduleTable.setItems(this.databaseHandler.returnSchedule("SELECT * FROM praktika.schedule"));
        } catch (ClassNotFoundException | SQLException var2) {
            var2.printStackTrace();
        }

    }
    /**
     * метод для кнопки выхода
     */
    private void openOtherWindow(String window) {
        this.exitButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        Parent root = (Parent)loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}