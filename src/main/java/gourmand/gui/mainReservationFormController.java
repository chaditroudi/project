/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmand.gui;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import gourmand.entity.Categorie;
import gourmand.entity.Product;
import gourmand.entity.Reservation;
import gourmand.entity.restaurantTable;
import gourmand.services.ProductService;
import gourmand.services.ReservationService;
import gourmand.services.RestaurantTableService;
import gourmand.utils.Database;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;

public class mainReservationFormController implements Initializable {

    @FXML
    public Button reservation_addBtn;

    @FXML
    public Button reservation_updateBtn;

    @FXML
    public Button reservation_clearBtn;

    @FXML
    public Button reservation_deleteBtn;


    @FXML
    public TableColumn reservation_col_customerName;

    @FXML
    public TableColumn reservation_col_dateTime;

    @FXML
    public TableColumn reservation_col_numberPersonnes;

    @FXML
    public TextField reservation_customer_tv;


    @FXML
    public Button reservation_btn;


    public TableColumn reservation_col_status;
    public AnchorPane reservation_form;


    @FXML
    public ComboBox<String> reservation_type_tables;

    @FXML
    public DatePicker reservation_datetime_pk;

    @FXML
    public TextField reservation_nb_per_tv;

    @FXML
    public AnchorPane tables_form;

    @FXML
    public Button tables_btn;
    public Button tables_btn_addBtn;
    public Button tables_btn_updateBtn;
    public Button tables_btn_clearBtn;
    public Button tables_btn_deleteBtn;

    @FXML
    public TableView<restaurantTable> tables_tableview;

    @FXML
    public TableColumn tables_col_cap;
    @FXML
    public TableColumn tables_col_desc;
    public TextArea desc_tv;
    public TextField capacity_tv;
    public TableColumn tables_col_status;
    public TableColumn reservation_col_id;
    public TableColumn tables_col_id;
    @FXML
    private AnchorPane main_form;


    @FXML
    private TextField cat_nom;

    @FXML
    private TableView<Reservation> reservation_tableView;

    private Alert alert;

    public ObservableList<Reservation> reservationsDataList()  {

        List<String> res = new ArrayList<>();




        for (Reservation data : servicereservation.getAll()) {
            reservationListData.add(data);            System.out.println("data="+reservationListData);

        }

        return  reservationListData;
    }


    private ObservableList<Reservation> reservationListData = FXCollections.observableArrayList();
    private ObservableList<restaurantTable> tablesListData = FXCollections.observableArrayList();

    public void reservationsShowData()  {


        reservationListData = reservationsDataList();

        reservation_col_customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        reservation_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        reservation_col_dateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        reservation_col_numberPersonnes.setCellValueFactory(new PropertyValueFactory<>("numberPersonnes"));

        reservation_tableView.setItems(reservationListData);

        System.out.println("data===>"+reservationListData.toString());

    }



    public void switchForm(ActionEvent event) throws SQLException {


        if(event.getSource() == tables_btn) {
            reservation_form.setVisible(false);
            tables_form.setVisible(true);
            tablesList();

        }

        if (event.getSource() == reservation_btn) {
            reservation_form.setVisible(true);
            tables_form.setVisible(false);
            tablesList();


        }

    }
// LETS PROCEED TO OUR DASHBOARD FORM : )

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        tablesList();
        tableShowData();
        reservationsShowData();










    }


// reservationS CRUD :



    ObservableList<Reservation> listCat = FXCollections.observableArrayList();
    ObservableList<restaurantTable> listTab = FXCollections.observableArrayList();

    ReservationService servicereservation = new ReservationService();



    RestaurantTableService restaurantTableService = new RestaurantTableService();

    public void tablesList() {

        List<String> tablesL = new ArrayList<>();
        System.out.println("here");

        // cattegory dispaly


        for (restaurantTable data : restaurantTableService.getTablesDispo()) {
            tablesL.add(String.valueOf(data.getId()));
        }

        System.out.println(tablesL);

        ObservableList listData = FXCollections.observableArrayList(tablesL);
        reservation_type_tables.setItems(listData);
    }


    @FXML
    private void reservationClearBtn() {
        reservation_nb_per_tv.setText("");
        reservation_customer_tv.setText("");
        reservation_datetime_pk.setValue(null);
    }


    public void tableAddBtn() throws SQLException {

        if (!capacity_tv.getText().matches("\\d*") ) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter only numbers");
            alert.showAndWait();
            return;
        }
        if(capacity_tv.getText().isEmpty() || desc_tv.getText().isEmpty()) {



            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            restaurantTable restaurantTable = new restaurantTable();
            restaurantTable.setCapacity(Integer.parseInt(capacity_tv.getText()));
            restaurantTable.setDescription(desc_tv.getText());
            restaurantTable.setAvailable(true);


            //Add table:
            restaurantTableService.create(restaurantTable);


            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();


            listTab.clear();
            listTab = restaurantTableService.getAll();
            tables_tableview.setItems(listTab);
        }
    }




    public void categoryDeleteBtn() {

    }

    public void reservationsSelectData() {
        System.out.println("hi");

        Reservation reservation = reservation_tableView.getSelectionModel().getSelectedItem();
        int num = reservation_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        reservation_customer_tv.setText(reservation.getCustomerName());
        reservation_nb_per_tv.setText(String.valueOf(reservation.getNumberPersonnes()));
        reservation_datetime_pk.setValue(reservation.getDateTime().toLocalDate());

    }



    public void reservationUpdateBtn() throws SQLException {

        if (!reservation_nb_per_tv.getText().matches("\\d*") ) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter only numbers");
            alert.showAndWait();
            return;
        }

        if (reservation_customer_tv.getText().isEmpty()
                || reservation_col_numberPersonnes.getText().isEmpty()
                || reservation_type_tables.getValue().isEmpty()||
                reservation_datetime_pk.getValue()  == null

        ) {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else
        {   alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE reservation ID: " +

                    reservation_tableView.getSelectionModel().getSelectedItem().getId() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

            Reservation reservation = new Reservation();
            reservation.setCustomerName(reservation_customer_tv.getText());
            reservation.setStatus("passé");
            reservation.setDateTime(reservation_datetime_pk.getValue().atStartOfDay());



            reservation.setNumberPersonnes(Integer.parseInt(reservation_nb_per_tv.getText()));



            // get table by id :
            restaurantTable TableCourant =restaurantTableService.getById(Integer.valueOf(reservation_type_tables.getValue()));



            //Add product:
            servicereservation.update(reservation_tableView.getSelectionModel().getSelectedItem().getId(),reservation);

            //update capacity

            if(reservation.getNumberPersonnes() > TableCourant.getCapacity()) {

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("number of person greater than capcity of tables!");
                alert.showAndWait();
                return;

            }



            int cap = TableCourant.getCapacity() - reservation.getNumberPersonnes();
            System.out.println("cap="+cap);
            // assign reservation to table :
            restaurantTable restaurantTable = new restaurantTable();

            System.out.println("resrevation id ========>"+

                    reservation_tableView.getSelectionModel().getSelectedItem().getId());
            restaurantTable.setReservationId((reservation_tableView.getSelectionModel().getSelectedItem().getId()));
            restaurantTable.setCapacity(cap);

            restaurantTable.setAvailable(false); // if 0 ===> not avalaible else if 1 ===> available
            restaurantTableService.update(TableCourant.getId(),restaurantTable);


            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Updated!");
            alert.showAndWait();

            listCat.clear();
            listCat = servicereservation.getAll();
            reservation_tableView.setItems(listCat);
            }
        }




        }


    public void reservationAddBtn() throws SQLException {
        if (!reservation_nb_per_tv.getText().matches("\\d*") ) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter only numbers");
            alert.showAndWait();
            return;
        }

        if (reservation_customer_tv.getText().isEmpty()
            || reservation_col_numberPersonnes.getText().isEmpty() ||
                reservation_datetime_pk.getValue()  == null

        ) {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            Reservation reservation = new Reservation();
            reservation.setCustomerName(reservation_customer_tv.getText());
            reservation.setStatus("passé");
            reservation.setDateTime(reservation_datetime_pk.getValue().atStartOfDay());
            reservation.setNumberPersonnes(Integer.parseInt(reservation_nb_per_tv.getText()));



            // get table by id :
            restaurantTable TableCourant =restaurantTableService.getById(Integer.valueOf(reservation_type_tables.getValue()));



            //Add product:
            servicereservation.create(reservation);

            //update capacity

            if(reservation.getNumberPersonnes() > TableCourant.getCapacity()) {

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("number of person greater than capcity of tables!");
                alert.showAndWait();
                return;

            }



            int cap = TableCourant.getCapacity() - reservation.getNumberPersonnes();
            System.out.println("cap="+cap);
            // assign reservation to table :
            restaurantTable restaurantTable = new restaurantTable();

            System.out.println("resrevation id ========>"+reservation.getId());
            restaurantTable.setReservationId((reservation.getId()));
            restaurantTable.setCapacity(cap);

            restaurantTable.setAvailable(false); // if 0 ===> not avalaible else if 1 ===> available
            restaurantTableService.update(TableCourant.getId(),restaurantTable);


            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();

            listCat.clear();
            listCat = servicereservation.getAll();
            reservation_tableView.setItems(listCat);
        }

    }



    public void reservationDeleteBtn(ActionEvent event) {

        int id =                 reservation_tableView.getSelectionModel().getSelectedItem().getId();
        if (id == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE reservation ID: " + id + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                try {
                    // Then delete the product record

                    servicereservation.delete(id);




                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    reservationListData.clear();
                    reservationListData = servicereservation.getAll();
                    reservation_tableView.setItems(reservationListData);

                    // TO CLEAR YOUR FIELDS
                    reservationClearBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }

        }
    }



    public void tableUpdateBtn() {


        if (!capacity_tv.getText().matches("\\d*") ) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter only numbers");
            alert.showAndWait();
            return;
        }
        if(capacity_tv.getText().isEmpty() || desc_tv.getText().isEmpty()) {



            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {



            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE tables ID: " +

                    tables_tableview.getSelectionModel().getSelectedItem().getId() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                restaurantTable restaurantTable = new restaurantTable();
                restaurantTable.setDescription(desc_tv.getText());
                restaurantTable.setCapacity(Integer.parseInt(capacity_tv.getText()));


                restaurantTableService.update(tables_tableview.getSelectionModel().getSelectedItem().getId(), restaurantTable);



                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();

                listTab.clear();
                listTab = restaurantTableService.getAll();
                tables_tableview.setItems(listTab);
            }
        }
    }


        public void tableClearBtn() {
        capacity_tv.setText("");
        desc_tv.setText("");
    }

    public void tableDeleteBtn() {

        int id =                 tables_tableview.getSelectionModel().getSelectedItem().getId();
        if (id == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE table ID: " + id + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                try {
                    // Then delete the product record

                    restaurantTableService.delete(id);




                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    listTab.clear();
                    listTab = restaurantTableService.getAll();
                    tables_tableview.setItems(listTab);

                    // TO CLEAR YOUR FIELDS
                    tableClearBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }

        }

    }

    public void tableSelectData(MouseEvent mouseEvent) {
        System.out.println("hi");

        restaurantTable restaurantTable = tables_tableview.getSelectionModel().getSelectedItem();
        int num = tables_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        capacity_tv.setText(String.valueOf(restaurantTable.getCapacity()));
        desc_tv.setText(restaurantTable.getDescription());

    }


    public ObservableList<restaurantTable> tablesListData()  {

        for (restaurantTable data : restaurantTableService.getAll()) {

            tablesListData.add(data);

        }

        return  tablesListData;
    }

    public void tableShowData() {


        tablesListData = tablesListData();

        tables_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));

        tables_col_cap.setCellValueFactory(new PropertyValueFactory<>("capacity"));
            tables_col_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        // Set the cell value factory for status with custom logic
        tables_col_status.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<restaurantTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<restaurantTable, String> param) {
                String status = param.getValue().isAvailable() ? "Dispo" : "Non-Dispo";
                return new SimpleStringProperty(status);
            }
        });
            tables_tableview.setItems(tablesListData);



    }
}