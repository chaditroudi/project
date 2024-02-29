package gourmand.services;

import gourmand.gui.data;
import gourmand.entity.Product;
import gourmand.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductService implements IService<Product> {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public ProductService() {
        // Initialize your database connection here
        connect = Database.getInstance().getConn();
    }

    // Existing functions

    public boolean isProductAvailable(String prodID) {
        try {
            String checkAvailable = "SELECT status FROM product WHERE id = ?";
            prepare = connect.prepareStatement(checkAvailable);
            prepare.setString(1, prodID);
            result = prepare.executeQuery();

            if (result.next()) {
                String status = result.getString("status");
                return status.equals("Available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getProductStock(String prodID) {
        try {
            String checkStock = "SELECT stock FROM product WHERE id = ?";
            prepare = connect.prepareStatement(checkStock);
            prepare.setString(1, prodID);
            result = prepare.executeQuery();

            if (result.next()) {
                return result.getInt("stock");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateProductStock(String prodID, int newStock) {
        try {
            String updateStock = "UPDATE product SET stock = ? WHERE id = ?";
            prepare = connect.prepareStatement(updateStock);
            prepare.setInt(1, newStock);
            prepare.setString(2, prodID);
            prepare.executeUpdate();
            System.out.println("update stock == " + prepare.executeUpdate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertCustomerOrder(String customerID, String prodID, String prodName, String type, int quantity, double price, String image, String username) {
        try {
            String insertData = "INSERT INTO customer (id, id, prod_name, type, quantity, price, date, image, em_username) VALUES(?,?,?,?,?,?,?,?,?)";
            prepare = connect.prepareStatement(insertData);
            prepare.setString(1, customerID);
            prepare.setString(2, prodID);
            prepare.setString(3, prodName);
            prepare.setString(4, type);
            prepare.setInt(5, quantity);
            prepare.setDouble(6, price);
            java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
            prepare.setDate(7, sqlDate);
            prepare.setString(8, image);
            prepare.setString(9, username);
            prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public Product get(String prodID) {
        try {
            String selectData = "SELECT * FROM product WHERE id = ?";
            prepare = connect.prepareStatement(selectData);

            prepare.setString(1, prodID);

            result = prepare.executeQuery();

            if (result.next()) {
                Product product = new Product();
                product.setProductName(result.getString("prod_name"));
                product.setType(result.getString("type"));
                product.setStock(result.getInt("stock"));
                product.setPrice(result.getDouble("price"));
                product.setStatus(result.getString("status"));
                product.setImage(result.getString("image"));
                return product;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Product product) throws SQLException {
        // CHECK PRODUCT ID

        String checkProdID = "SELECT id FROM product WHERE id = '"
                + product.getProductId()+ "'";

        prepare = connect.prepareStatement(checkProdID);


        try {


            result = prepare. executeQuery(checkProdID);


                String insertData = "INSERT INTO product "
                        + "(prod_name, type, stock, price, status, image, date) "
                        + "VALUES(?,?,?,?,?,?,?)";

                prepare = connect.prepareStatement(insertData);
                prepare.setString(1, product.getProductName());
                prepare.setString(2, product.getType());
                prepare.setInt(3, product.getStock());
                prepare.setDouble(4, product.getPrice());
            prepare.setString(5, product.getStatus());


                String path = data.path;
                path = path.replace("\\", "\\\\");

                prepare.setString(6, path);

                // TO GET CURRENT DATE
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(7, String.valueOf(sqlDate));

                prepare.executeUpdate();



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Product getById(int id) {
        Product product = new Product();
        try {
            String selectAllData = "SELECT * FROM product where id=?";

            prepare = connect.prepareStatement(selectAllData);

            prepare.setInt(1,id);
            result = prepare.executeQuery();

            while (result.next()) {
                product.setId(result.getInt("id"));
                product.setId(result.getInt("id"));
                product.setProductName(result.getString("prod_name"));
                product.setType(result.getString("type"));
                product.setStock(result.getInt("stock"));
                product.setPrice(result.getDouble("price"));
                product.setStatus(result.getString("status"));
                product.setImage(result.getString("image"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try {
            String selectAllData = "SELECT * FROM product";
            prepare = connect.prepareStatement(selectAllData);
            result = prepare.executeQuery();

            while (result.next()) {
                Product product = new Product();
                product.setId(result.getInt("id"));
                product.setId(result.getInt("id"));
                product.setProductName(result.getString("prod_name"));
                product.setType(result.getString("type"));
                product.setStock(result.getInt("stock"));
                product.setPrice(result.getDouble("price"));
                product.setStatus(result.getString("status"));
                product.setImage(result.getString("image"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void update(int prodID, Product updatedProduct) {

        try {
            String path = data.path;
            path = path.replace("\\", "\\\\");

            String updateData = "UPDATE product SET "
                    + "prod_name = ?, "
                    + "type = ?, "
                    + "stock = ?, "
                    + "price = ?, "
                    + "status = ?, "
                    + "image = ?, "
                    + "date = ? WHERE id = ?";
            prepare = connect.prepareStatement(updateData);

            // Set values for each parameter
            prepare.setString(1, updatedProduct.getProductName());
            prepare.setString(2, updatedProduct.getType());
            prepare.setInt(3, updatedProduct.getStock());
            prepare.setDouble(4, updatedProduct.getPrice());
            prepare.setString(5, updatedProduct.getStatus());
            prepare.setString(6, path);
            prepare.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
            prepare.setInt(8, data.id);

            // Execute the update query
            prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void delete(int id) {

    }

}
