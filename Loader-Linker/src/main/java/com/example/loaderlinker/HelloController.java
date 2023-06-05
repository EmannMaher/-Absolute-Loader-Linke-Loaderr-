package com.example.loaderlinker;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;



public class HelloController implements Initializable {
    @FXML
    private TableView<Table> table;
    @FXML
    private TableColumn<Table, String> Address;

    @FXML
    private TableColumn<Table, String> col_0;

    @FXML
    private TableColumn<Table, String> col_1;

    @FXML
    private TableColumn<Table, String> col_2;

    @FXML
    private TableColumn<Table, String> col_3;

    @FXML
    private TableColumn<Table, String> col_4;

    @FXML
    private TableColumn<Table, String> col_5;

    @FXML
    private TableColumn<Table, String> col_6;

    @FXML
    private TableColumn<Table, String> col_7;

    @FXML
    private TableColumn<Table, String> col_8;

    @FXML
    private TableColumn<Table, String> col_9;

    @FXML
    private TableColumn<Table, String> col_10;

    @FXML
    private TableColumn<Table, String> col_11;

    @FXML
    private TableColumn<Table, String> col_12;

    @FXML
    private TableColumn<Table, String> col_13;

    @FXML
    private TableColumn<Table, String> col_14;

    @FXML
    private TableColumn<Table, String> col_15;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Address.setCellValueFactory(new PropertyValueFactory<Table, String>("address"));
        col_0.setCellValueFactory(new PropertyValueFactory<Table, String>("col_0"));
        col_1.setCellValueFactory(new PropertyValueFactory<Table, String>("col_1"));
        col_2.setCellValueFactory(new PropertyValueFactory<Table, String>("col_2"));
        col_3.setCellValueFactory(new PropertyValueFactory<Table, String>("col_3"));
        col_4.setCellValueFactory(new PropertyValueFactory<Table, String>("col_4"));
        col_5.setCellValueFactory(new PropertyValueFactory<Table, String>("col_5"));
        col_6.setCellValueFactory(new PropertyValueFactory<Table, String>("col_6"));
        col_7.setCellValueFactory(new PropertyValueFactory<Table, String>("col_7"));
        col_8.setCellValueFactory(new PropertyValueFactory<Table, String>("col_8"));
        col_9.setCellValueFactory(new PropertyValueFactory<Table, String>("col_9"));
        col_10.setCellValueFactory(new PropertyValueFactory<Table, String>("col_A"));
        col_11.setCellValueFactory(new PropertyValueFactory<Table, String>("col_B"));
        col_12.setCellValueFactory(new PropertyValueFactory<Table, String>("col_C"));
        col_13.setCellValueFactory(new PropertyValueFactory<Table, String>("col_D"));
        col_14.setCellValueFactory(new PropertyValueFactory<Table, String>("col_E"));
        col_15.setCellValueFactory(new PropertyValueFactory<Table, String>("col_F"));
        table.setItems(HelloApplication.data);
    }
}
