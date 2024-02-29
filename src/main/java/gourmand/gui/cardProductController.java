// CardProductController.java
package gourmand.gui;

import java.net.URL;
import java.util.ResourceBundle;

import gourmand.entity.Product;
import gourmand.services.ProductService;
import gourmand.services.ServicePanier;
import gourmand.entity.Panier;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class cardProductController implements Initializable {
    @FXML
    private AnchorPane card_form;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Spinner<Integer> prod_spinner;

    @FXML
    private Button prod_addBtn;

    private ProductService productService;
    private ServicePanier servicePanier = new ServicePanier();
    private Product prodData;
    private Alert alert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productService = new ProductService();
        setQuantity();
    }

    public void setData(Product prodData) {

        this.prodData = prodData;

        prod_name.setText(prodData.getProductName());

        prod_price.setText("$" + String.valueOf(prodData.getPrice()));

        String path = "File:" + prodData.getImage();

        Image image = new Image(path, 190, 94, false, true);

        prod_imageView.setImage(image);

    }

    public void addBtn() {
        int qty = prod_spinner.getValue();
        if (qty <= 0) {
            showAlert("Error", "Invalid Quantity", "Please select a valid quantity.");
            return;
        }

        if (!productService.isProductAvailable(String.valueOf(prodData.getId()))) {
            showAlert("Error", "Product Unavailable", "This product is not available for purchase.");
            return;
        }

        int availableStock = productService.getProductStock(String.valueOf(prodData.getId()));
        if (availableStock < qty) {
            showAlert("Error", "Insufficient Stock", "There is not enough stock for this product.");
            return;
        }

        double totalPrice = qty * prodData.getPrice();

        //static user id ;
        int idUser = 1;

        Panier panier = new Panier();
        panier.setProductId(prodData.getId());
        panier.setPersonneId(idUser);
        panier.setQuantity(qty);

        ProductService productService1 = new ProductService();

        Product p = productService1.getById(panier.getProductId());

        panier.setPrix_total( p.getPrice() * panier.getQuantity()); // PU



        // insert panier
        servicePanier.addToPanier(panier);




     //   productService.insertCustomerOrder(String.valueOf(data.cID), String.valueOf(prodData.getId()), prodData.getProductName(), prodData.getType(), qty, totalPrice, prodData.getImage(), data.username);
        int newStock = availableStock - qty;
        productService.updateProductStock(String.valueOf(prodData.getId()), newStock);

        System.out.println("data of product when we add to cart"+prodData.getId()+","+data.cID);


        showAlert("Success", "Product Added", "The product has been added to your order.");
    }

    private void setQuantity() {
        SpinnerValueFactory<Integer> spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        prod_spinner.setValueFactory(spin);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
