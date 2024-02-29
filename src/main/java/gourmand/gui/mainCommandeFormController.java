/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmand.gui;

import gourmand.entity.*;
import gourmand.services.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class mainCommandeFormController implements Initializable {

    @FXML
    public Button commande_addBtn;

    @FXML
    public Button commande_updateBtn;

    @FXML
    public Button commande_clearBtn;

    @FXML
    public Button commande_deleteBtn;


    @FXML
    public TextField reservation_customer_tv;

    @FXML
    public ComboBox<Integer> panier_cb;

    @FXML
    public DatePicker reservation_datetime_pk;

    @FXML
    public TextField reservation_nb_per_tv;


    @FXML
    public TableView<restaurantTable> tables_tableview;
    public Button commande_btn;
    public TextField commande_adr_dest_tv;
    public Label commande_prix_total_tv;
    public ComboBox<String> status_cb;
    public TableColumn commande_col_status;
    public Button liv_btn;
    public AnchorPane liv_form;
    public TableView<Livraison> liv_tableView;
    public Button live_addBtn;
    public Button live_updateBtn;
    public Button live_clearBtn;
    public Button live_deleteBtn;
    public TextField adresse_depart_tv;
    public TableColumn live_col_adr_depart;
    public TableColumn live_col_adr_arrive;
    public TableColumn live_col_commande;
    public TableColumn live_col_date;
    public TableColumn live_col_etat;
    public TextField adresse_dest_tv;
    public ComboBox<String> etat_cb;
    public ComboBox<Integer> commande_cb;
    public DatePicker liv_date_pk;

    CommandeService servicecommande = new CommandeService();

    public TextArea desc_tv;
    public TextField capacity_tv;
    public AnchorPane commande_form;
    public TableView<Commande> commande_tableView;
    public TableColumn commande_col_adr;
    public TableColumn commande_col_panier;
    public TableColumn commande_col_prix_total;

    LivraisonService livraisonService = new LivraisonService();


    @FXML
    private TextField cat_nom;

    @FXML
    private TableView<Reservation> reservation_tableView;

    private Alert alert;


    private ObservableList<Commande> reservationListData = FXCollections.observableArrayList();
    private ObservableList<Livraison> livListData = FXCollections.observableArrayList();


    public ObservableList<Livraison> livDataList() {

        List<String> res = new ArrayList<>();


        for (Livraison data : livraisonService.getAll()) {
            livListData.add(data);

            System.out.println("data=" + livListData);

        }

        return livListData;
    }


    public void livShowData() {


        livListData = livDataList();

        live_col_adr_depart.setCellValueFactory(new PropertyValueFactory<>("adresse_depart"));
        live_col_adr_arrive.setCellValueFactory(new PropertyValueFactory<>("adresse_depart"));
        live_col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        live_col_date.setCellValueFactory(new PropertyValueFactory<>("date_reception"));
        live_col_commande.setCellValueFactory(new PropertyValueFactory<>("commandeId"));

        liv_tableView.setItems(livListData);

        System.out.println("data===>" + livListData.toString());

    }


    public ObservableList<Commande> cmdList() {

        List<Commande> res = new ArrayList<>();


        for (Commande data : servicecommande.getAll()) {
            cmdListData.add(data);

        }
        return cmdListData;

    }

    public void cmdShowData() {


        cmdListData = cmdList();

        commande_col_adr.setCellValueFactory(new PropertyValueFactory<>("adresse_dest"));
        commande_col_prix_total.setCellValueFactory(new PropertyValueFactory<>("prix_total"));
        commande_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        commande_col_panier.setCellValueFactory(new PropertyValueFactory<>("idPanier"));

        commande_tableView.setItems(cmdListData);


    }


    public void switchForm(ActionEvent event) throws SQLException {


        if (event.getSource() == liv_btn) {
            commande_form.setVisible(false);
            liv_form.setVisible(true);


        }

        if (event.getSource() == commande_btn) {
            commande_form.setVisible(true);
            liv_form.setVisible(false);


        }

    }
// LETS PROCEED TO OUR DASHBOARD FORM : )


    int selected = 0;
    double currentPrixTotal = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        commande_form.setVisible(true);
        //  commandesList();
        commandesList();

        tablesList();
        livShowData();
        cmdShowData();
        etatListData();

        commande_cb.setOnAction(e -> {
            selectCmd = commande_cb.getValue();


        });


        etat_cb.setOnAction(e -> {
            selectEtat = etat_cb.getValue();


        });



        //commande status :
        commandeStatusList();

        // select status :

        status_cb.setOnAction(e -> {
            selectStatus = status_cb.getValue();
        });
        //select :
        panier_cb.setOnAction(e -> {
            selected = panier_cb.getValue();


            System.out.println("Selected item: " + selected);
            // Perform actions based on the selected item

            Panier panier = new Panier();
            ServicePanier servicePanier = new ServicePanier();

            panier = servicePanier.getById(selected);

            commande_prix_total_tv.setText("Total comande:" + String.valueOf(panier.getPrix_total()));
            currentPrixTotal = panier.getPrix_total();
        });

        tablesList();
        // tableShowData();


    }


// reservationS CRUD :


    ObservableList<Reservation> listCat = FXCollections.observableArrayList();
    ObservableList<Commande> listCmd = FXCollections.observableArrayList();
    ObservableList<Livraison> listLiv = FXCollections.observableArrayList();


    ReservationService servicereservation = new ReservationService();


    RestaurantTableService restaurantTableService = new RestaurantTableService();

    public void tablesList() {

        List<Integer> tablesL = new ArrayList<>();
        System.out.println("here");

        // cattegory dispaly


        for (Panier data : servicecommande.getAllPanier()) {
            tablesL.add(data.getId());
        }

        System.out.println(tablesL);

        ObservableList<Integer> listData = FXCollections.observableArrayList(tablesL);
        panier_cb.setItems(listData);
    }

    String selectStatus = "";

    public void commandeStatusList() {

        List<String> commandeStatus = new ArrayList<>();
        System.out.println("here");

        // cattegory dispaly


        commandeStatus.add("en cours");
        commandeStatus.add("confirmé");

        System.out.println(commandeStatus);

        ObservableList<String> listData = FXCollections.observableArrayList(commandeStatus);
        status_cb.setItems(listData);


    }


    @FXML
    private void reservationClearBtn() {
        reservation_nb_per_tv.setText("");
        reservation_customer_tv.setText("");
        reservation_datetime_pk.setValue(null);
    }


    public void tableAddBtn() throws SQLException {

        if (!capacity_tv.getText().matches("\\d*")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter only numbers");
            alert.showAndWait();
            return;
        }
        if (capacity_tv.getText().isEmpty() || desc_tv.getText().isEmpty()) {


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


            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();


            listCmd.clear();
            listCmd = servicecommande.getAll();
            commande_tableView.setItems(listCmd);
        }
    }


    public void categoryDeleteBtn() {

    }

    public void commandesSelectData() {
        System.out.println("hi");

        Commande reservation = commande_tableView.getSelectionModel().getSelectedItem();
        int num = commande_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        commande_adr_dest_tv.setText(reservation.getAdresse_dest());

        commande_prix_total_tv.setText(String.valueOf(reservation.getPrix_total()));
        status_cb.setValue(reservation.getStatus());
        panier_cb.setValue(reservation.getIdPanier());

    }

    public void livSelectData() {

        Livraison livraison = liv_tableView.getSelectionModel().getSelectedItem();
        int num = liv_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        adresse_depart_tv.setText(livraison.getAdresse_depart());
        adresse_dest_tv.setText(livraison.getAdresse_arrive());
        etat_cb.setValue(livraison.getEtat());
        commande_cb.setValue(livraison.getCommandeId());
        liv_date_pk.setValue(LocalDate.parse(livraison.getDate_reception()));



    }


    public void reservationUpdateBtn() throws SQLException {


    }


    public void reservationAddBtn() throws SQLException {

    }


    public void reservationDeleteBtn(ActionEvent event) {


    }


    public void tableUpdateBtn() {


        if (!capacity_tv.getText().matches("\\d*")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter only numbers");
            alert.showAndWait();
            return;
        }
        if (capacity_tv.getText().isEmpty() || desc_tv.getText().isEmpty()) {


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


                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();

                listCmd.clear();
                listCmd = servicecommande.getAll();
                commande_tableView.setItems(listCmd);
            }
        }
    }


    public void tableClearBtn() {
        commande_adr_dest_tv.setText("");
        panier_cb.setValue(0);
        commande_prix_total_tv.setText("");
    }

    public void tableDeleteBtn() {

        int id = tables_tableview.getSelectionModel().getSelectedItem().getId();
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

                    listCmd.clear();
                    listCmd = servicecommande.getAll();
                    commande_tableView.setItems(listCmd);
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


    public void commandeAddBtn() {
        if (commande_adr_dest_tv.getText().isEmpty() || commande_prix_total_tv.getText().isEmpty()
                || panier_cb.getValue() == null
        ) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        } else {
            Commande commande = new Commande();
            commande.setDate(Date.valueOf(LocalDate.now())); // Set the date to the current system date
            commande.setAdresse_dest(commande_adr_dest_tv.getText());
            commande.setPrix_total(currentPrixTotal);
            commande.setIdPersonne(1);
            System.out.println("select panier id =" + selected);
            commande.setIdPanier(selected);
            commande.setStatus(selectStatus);


            //Add table:
            servicecommande.create(commande);


            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();


            listCmd.clear();
            listCmd = servicecommande.getAll();
            commande_tableView.setItems(listCmd);
        }
    }

    public void commandeUpdateBtn() {
        if (commande_adr_dest_tv.getText().isEmpty() || commande_prix_total_tv.getText().isEmpty()
                || panier_cb.getValue() == null || status_cb.getValue() == null
        ) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE commande ID: " +

                    commande_tableView.getSelectionModel().getSelectedItem().getId() + "?");

            int idcommande = commande_tableView.getSelectionModel().getSelectedItem().getId();
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                Commande commande = new Commande();
                commande.setDate(Date.valueOf(LocalDate.now())); // Set the date to the current system date
                commande.setAdresse_dest(commande_adr_dest_tv.getText());
                commande.setPrix_total(currentPrixTotal);
                commande.setStatus(selectStatus);
                commande.setIdPersonne(1);
                System.out.println("select panier id =" + selected);
                commande.setIdPanier(selected);


                //Add table:
                servicecommande.update(idcommande, commande);


                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Successfully updated!");
                alert.showAndWait();


                listCmd.clear();
                listCmd = servicecommande.getAll();
                commande_tableView.setItems(listCmd);
            }
        }

    }

    public void commandeClearBtn() {
        commande_adr_dest_tv.setText("");
        panier_cb.setValue(null);
        status_cb.setValue(null);
        commande_prix_total_tv.setText("");

    }

    public void commandeDeleteBtn(ActionEvent event) {
        int id = commande_tableView.getSelectionModel().getSelectedItem().getId();
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
            alert.setContentText("Are you sure you want to DELETE commande ID: " + id + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                try {
                    // Then delete the product record

                    servicecommande.delete(id);


                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    listCmd.clear();
                    listCmd = servicecommande.getAll();
                    commande_tableView.setItems(listCmd);
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

    private ObservableList<Commande> cmdListData = FXCollections.observableArrayList();

    public void commandesList() {

        List<Integer> commandes = new ArrayList<>();
        System.out.println("here");

        // cattegory dispaly


        for (Commande data : servicecommande.getAll()) {
            commandes.add(data.getId());
        }


        ObservableList listData = FXCollections.observableArrayList(commandes);
        commande_cb.setItems(listData);
    }



    public void etatListData() {

        List<String> etats = new ArrayList<>();
        System.out.println("here");

        // cattegory dispaly

        etats.add("en cours");
        etats.add("confimé");

        ObservableList listData = FXCollections.observableArrayList(etats);
        etat_cb.setItems(listData);
    }

    int selectCmd = 0;
    String selectEtat = "";

    public void liveAddBtn() {
        if (adresse_dest_tv.getText().isEmpty() || adresse_depart_tv.getText().isEmpty()
                || etat_cb.getValue() == null || commande_cb.getValue() == null
                || liv_date_pk.getValue() == null
        ) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        } else {
            Livraison livraison = new Livraison();
            livraison.setAdresse_arrive(adresse_dest_tv.getText());
            livraison.setAdresse_depart(adresse_depart_tv.getText());
            livraison.setDate_reception(String.valueOf(liv_date_pk.getValue()));
            livraison.setPersonneId(1);
            livraison.setDate_reception(liv_date_pk.getValue().toString());

            etat_cb.setOnAction(e -> {
                selectEtat = etat_cb.getValue();


            });

            livraison.setCommandeId(selectCmd);
            livraison.setEtat(selectEtat);

            livraisonService.create(livraison);


            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();


            listLiv.clear();
            listLiv = livraisonService.getAll();
            liv_tableView.setItems(listLiv);
        }
    }

    public void liveUpdateBtn(ActionEvent event) {
        if (adresse_depart_tv.getText().isEmpty() || adresse_dest_tv.getText().isEmpty()
                || commande_cb.getValue() == null || etat_cb.getValue() == null || liv_date_pk.getValue() == null
        ) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        }



        Livraison livraison = liv_tableView.getSelectionModel().getSelectedItem();
        livraison.setAdresse_arrive(adresse_dest_tv.getText());
        livraison.setAdresse_depart(adresse_depart_tv.getText());
        livraison.setDate_reception(String.valueOf(liv_date_pk.getValue()));
        livraison.setPersonneId(1);
        livraison.setDate_reception(liv_date_pk.getValue().toString());


        etat_cb.setOnAction(e -> {
            selectEtat = etat_cb.getValue();


        });

        livraison.setCommandeId(selectCmd);
        livraison.setEtat(selectEtat);



        livraisonService.update(liv_tableView.getSelectionModel().getSelectedItem().getId(),livraison);


        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Successfully updated!");
        alert.showAndWait();


        listLiv.clear();
        listLiv = livraisonService.getAll();
        liv_tableView.setItems(listLiv);


}

    public void liveClearBtn(ActionEvent event) {
        liv_date_pk.setValue(null);
        adresse_dest_tv.setText("");
        adresse_depart_tv.setText("");
        etat_cb.setValue("");
        commande_cb.setValue(0);
    }

    public void livDeleteBtn(ActionEvent event) {

        int id = liv_tableView.getSelectionModel().getSelectedItem().getId();
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
            alert.setContentText("Are you sure you want to DELETE livraison ID: " + id + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                try {
                    // Then delete the product record

                    livraisonService.delete(id);


                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    livListData.clear();
                    livListData = livraisonService.getAll();
                    liv_tableView.setItems(livListData);
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

    public  void liveSelectData() {}
}