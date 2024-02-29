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
import gourmand.services.ProductService;
import gourmand.services.ServiceCategorie;
import gourmand.utils.Database;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class mainFormController implements Initializable {

    public Button categorie_addBtn;
    public Button categorie_updateBtn;
    public Button categorie_clearBtn;
    public Button categorie_deleteBtn;
    @FXML
    private AnchorPane main_form;


    @FXML
    private TextField cat_nom;

    @FXML
    private Label username;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button menu_btn;

    @FXML
    private Button customers_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private TableView<Product> inventory_tableView;
    private TableView<Categorie> category_tableView;


    @FXML
    private TableColumn<Product, String> inventory_col_productID;

    @FXML
    private TableColumn<Product, String> inventory_col_productName;

    @FXML
    private TableColumn<Product, String> inventory_col_type;

    @FXML
    private TableColumn<Product, String> inventory_col_stock;

    @FXML
    private TableColumn<Product, String> inventory_col_price;

    @FXML
    private TableColumn<Product, String> inventory_col_status;

    @FXML
    private TableColumn<Product, String> inventory_col_date;

    @FXML
    private ImageView inventory_imageView;

    @FXML
    private Button inventory_importBtn;

    @FXML
    private Button inventory_addBtn;

    @FXML
    private Button inventory_updateBtn;

    @FXML
    private Button inventory_clearBtn;

    @FXML
    private Button inventory_deleteBtn;


    @FXML
    private TextField inventory_productName;

    @FXML
    private TextField inventory_stock;

    @FXML
    private TextField inventory_price;

    @FXML
    private ComboBox<?> inventory_status;

    @FXML
    private ComboBox<?> inventory_type;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private TableView<Product> menu_tableView;

    @FXML
    private TableColumn<Product, String> menu_col_productName;

    @FXML
    private TableColumn<Product, String> menu_col_quantity;

    @FXML
    private TableColumn<Product, String> menu_col_price;

    @FXML
    private Label menu_total;

    @FXML
    private TextField menu_amount;

    @FXML
    private Label menu_change;

    @FXML
    private Button menu_payBtn;

    @FXML
    private Button menu_removeBtn;

    @FXML
    private Button menu_receiptBtn;


    @FXML
    private AnchorPane customers_form;


    @FXML
    private AnchorPane cat_form;

    @FXML
    private TableView<Categorie> categorie_tableView;


    @FXML
    private TableColumn<Categorie, String> cat_col_nom;



    @FXML
    private Button cat_btn;

    @FXML
    private Label dashboard_NSP;

    @FXML
    private AreaChart<?, ?> dashboard_incomeChart;

    @FXML
    private BarChart<?, ?> dashboard_CustomerChart;

    private Alert alert;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private ProductService serviceProduct = new ProductService();

    private Image image;

    private ObservableList<Product> cardListData = FXCollections.observableArrayList();

    public void inventoryAddBtn() throws SQLException {

        if (
              inventory_productName.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || data.path == null) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            Product product = new Product();
            product.setProductName(inventory_productName.getText());
            product.setType((String) inventory_type.getSelectionModel().getSelectedItem());
            product.setStock(Integer.parseInt(inventory_stock.getText()));
            product.setPrice(Double.parseDouble(inventory_price.getText()));
            product.setStatus((String) inventory_status.getSelectionModel().getSelectedItem());

            //Add product:
            serviceProduct.create(product);


            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();

            inventoryShowData();
            inventoryClearBtn();

        }
    }


    public void inventoryUpdateBtn() {

        if (
                inventory_productName.getText().isEmpty()
                        || inventory_type.getSelectionModel().getSelectedItem() == null
                        || inventory_stock.getText().isEmpty()
                        || inventory_price.getText().isEmpty()
                        || inventory_status.getSelectionModel().getSelectedItem() == null
                        || data.path == null || data.id == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {


            try {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE PRoduct ID: " +
                        inventory_tableView.getSelectionModel().getSelectedItem().getId()
                        + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    Product product = new Product();
                    product.setProductName(inventory_productName.getText());
                    product.setType((String) inventory_type.getSelectionModel().getSelectedItem());
                    product.setStock(Integer.parseInt(inventory_stock.getText()));
                    product.setPrice(Double.parseDouble(inventory_price.getText()));
                    product.setStatus((String) inventory_status.getSelectionModel().getSelectedItem());


                    serviceProduct.update(data.id, product);
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inventoryDeleteBtn() {
        if (data.id == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Product ID: " +

                    inventory_tableView.getSelectionModel().getSelectedItem().getId() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                try {
                    // Delete associated records in the panier table first
                    String deletePanierData = "DELETE FROM panier WHERE productId = ?";
                    prepare = connect.prepareStatement(deletePanierData);
                    prepare.setInt(1, data.id);
                    prepare.executeUpdate();

                    // Then delete the product record
                    String deleteData = "DELETE FROM product WHERE id = ?";
                    prepare = connect.prepareStatement(deleteData);
                    prepare.setInt(1, data.id);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();

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

    public void inventoryClearBtn() {

        inventory_productName.setText("");
        inventory_type.getSelectionModel().clearSelection();
        inventory_stock.setText("");
        inventory_price.setText("");
        inventory_status.getSelectionModel().clearSelection();
        data.path = "";
        data.id = 0;
        inventory_imageView.setImage(null);

    }

    public void inventoryImportBtn() {

        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*png", "*jpg"));

        File file = openFile.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 120, 127, false, true);

            inventory_imageView.setImage(image);
        }
    }

    // MERGE ALL DATAS
    public ObservableList<Product> inventoryDataList() {

        ObservableList<Product> listData = FXCollections.observableArrayList();

        for(Product p : serviceProduct.getAll())
        {

            listData.add(p);
        }

        return listData;


    }
    // TO SHOW DATA ON OUR TABLE
    private ObservableList<Product> inventoryListData;

    public void inventoryShowData() {
        inventoryListData = inventoryDataList();

        inventory_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_tableView.setItems(inventoryListData);

    }

    public void inventorySelectData() {

        Product prodData = inventory_tableView.getSelectionModel().getSelectedItem();
        int num = inventory_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        inventory_productName.setText(prodData.getProductName());
        inventory_stock.setText(String.valueOf(prodData.getStock()));
        inventory_price.setText(String.valueOf(prodData.getPrice()));

        data.path = prodData.getImage();

        String path = "File:" + prodData.getImage();
        data.date = String.valueOf(prodData.getDate());
        data.id = prodData.getId();

        image = new Image(path, 120, 127, false, true);
        inventory_imageView.setImage(image);
    }


    public void inventoryTypeList() {

        List<String> typeL = new ArrayList<>();

        // cattegory dispaly



        for (Categorie data : serviceCategorie.getAll()) {
            typeL.add(data.getNom());
        }

        ObservableList listData = FXCollections.observableArrayList(typeL);
        inventory_type.setItems(listData);
    }

    private String[] statusList = {"Available", "Unavailable"};

    public void inventoryStatusList() {

        List<String> statusL = new ArrayList<>();

        for (String data : statusList) {
            statusL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(statusL);
        inventory_status.setItems(listData);

    }

    public ObservableList<Product> menuGetData() {

        ObservableList<Product> listData = FXCollections.observableArrayList();

        for(Product p : serviceProduct.getAll()){
            listData.add(p);
        }
        
        return listData;
    }

    public void menuDisplayCard() {

        cardListData.clear();
        cardListData.addAll(menuGetData());

        int row = 0;
        int column = 0;

        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();

        for (int q = 0; q < cardListData.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("cardProduct.fxml"));
                AnchorPane pane = load.load();
                cardProductController cardC = load.getController();
                cardC.setData(cardListData.get(q));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                menu_gridPane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public ObservableList<Product> menuGetOrder() {
//        customerID();
//        ObservableList<Product> listData = FXCollections.observableArrayList();
//
//        String sql = "SELECT * FROM customer WHERE customer_id = " + cID;
//
//        connect = Database.getInstance().getConn();
//
//
//        try {
//
//            prepare = connect.prepareStatement(sql);
//            result = prepare.executeQuery();
//
//            Product prod;
//
//            while (result.next()) {
//                prod = new Product(result.getInt("id"),
//                        result.getString("prod_name"),
//                        result.getString("type"),
//                        result.getInt("quantity"),
//                        result.getDouble("price"),
//                        result.getString("image"),
//                        result.getDate("date"));
//                listData.add(prod);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return listData;
//    }

    private ObservableList<Product> menuOrderListData;

//    public void menuShowOrderData() {
//        menuOrderListData = menuGetOrder();
//
//        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
//        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
//
//        menu_tableView.setItems(menuOrderListData);
//    }

    private int getid;

    public void menuSelectOrder() {
        Product prod = menu_tableView.getSelectionModel().getSelectedItem();
        int num = menu_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        // TO GET THE ID PER ORDER
        getid = prod.getId();

    }

    private double totalP;




    private double amount;
    private double change;




    public void menuReceiptBtn() {

        if (totalP == 0 || menu_amount.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please order first");
            alert.showAndWait();
        } else {
            HashMap map = new HashMap();
            map.put("getReceipt", (cID - 1));

            try {

                // JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\WINDOWS 10\\Documents\\NetBeansProjects\\cafeShopManagementSystem\\src\\cafeshopmanagementsystem\\report.jrxml");
                //JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                //JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);

                //JasperViewer.viewReport(jPrint, false);

                menuRestart();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void menuRestart() {
        totalP = 0;
        change = 0;
        amount = 0;
        menu_total.setText("$0.0");
        menu_amount.setText("");
        menu_change.setText("$0.0");
    }

    private int cID;






    public void switchForm(ActionEvent event) {



         if (event.getSource() == inventory_btn) {
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            customers_form.setVisible(false);
             menu_form.setVisible(false);


             cat_form.setVisible(false);


            inventoryTypeList();
            inventoryStatusList();
            inventoryShowData();

        } else if (event.getSource() == cat_btn) {
             categorie_tableView.refresh();
             listCat = serviceCategorie.getAll();
             inventoryTypeList();

             categorie_tableView.setItems(listCat);
             cat_form.setVisible(true);

             inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(false);


        } else if (event.getSource() == menu_btn) {
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
            customers_form.setVisible(false);
            cat_form.setVisible(false);
             inventoryTypeList();


             menuDisplayCard();


        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       connect = Database.getInstance().getConn();


        inventory_form.setVisible(true);


        inventoryTypeList();
        inventoryStatusList();
        inventoryShowData();
        categoriesShowData();

        menuDisplayCard();







    }


// CATEGORIES CRUD :



    ObservableList<Categorie> listCat = FXCollections.observableArrayList();

    ServiceCategorie serviceCategorie = new ServiceCategorie();

    public void categoriesShowData() {

        listCat.clear();

        categorie_tableView.setItems(null);

        listCat = serviceCategorie.getAll();


        cat_col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));


        categorie_tableView.setItems(listCat);






    }



    private void categorieClearBtn() {
        cat_nom.setText("");
    }


    public void categoryAddBtn() throws SQLException {

        if (cat_nom.getText().isEmpty()) {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            Categorie categorie = new Categorie();
            categorie.setNom(cat_nom.getText());


            //Add product:
            serviceCategorie.create(categorie);


            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();

            listCat.clear();
            listCat = serviceCategorie.getAll();
            categorie_tableView.setItems(listCat);
        }
    }




    public void categoryDeleteBtn() {

        int id =                 categorie_tableView.getSelectionModel().getSelectedItem().getId();
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
            alert.setContentText("Are you sure you want to DELETE Category ID: " + id + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                try {
                    // Then delete the product record

                    serviceCategorie.delete(id);




                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    listCat.clear();
                    listCat = serviceCategorie.getAll();
                    categorie_tableView.setItems(listCat);

                    // TO CLEAR YOUR FIELDS
                    categorieClearBtn();

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

    public void categoriesSelectData() {
        System.out.println("hi");

        Categorie categorie = categorie_tableView.getSelectionModel().getSelectedItem();
        int num = categorie_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        cat_nom.setText(categorie.getNom());
         }



    public void categoryUpdateBtn() {

        if (
                cat_nom.getText().isEmpty()
                       ) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {


            try {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE categorie ID: " + categorie_tableView.getSelectionModel().getSelectedItem().getId() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                        Categorie categorie = new Categorie();
                        categorie.setNom(cat_nom.getText());

                        serviceCategorie.update(categorie_tableView.getSelectionModel().getSelectedItem().getId(),categorie);
                        alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    listCat.clear();
                    listCat = serviceCategorie.getAll();
                    categorie_tableView.setItems(listCat);

                    // TO CLEAR YOUR FIELDS
                    categorieClearBtn();
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





}