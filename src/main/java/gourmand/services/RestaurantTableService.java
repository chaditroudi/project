package gourmand.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gourmand.entity.Reservation;
import gourmand.entity.restaurantTable;
import gourmand.utils.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class RestaurantTableService implements IService<restaurantTable> {
    private Connection connection;

    Statement ste;

    PreparedStatement preparedStatement;

    public RestaurantTableService() {
        try {
            connection = Database.getInstance().getConn();
            ste = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void create(restaurantTable table) {
        String query = "INSERT INTO restaurant_table (capacity, available,description) VALUES (?, ?,?)";
try{
    preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, table.getCapacity());
    preparedStatement.setBoolean(2, table.isAvailable());
    preparedStatement.setString(3, table.getDescription());

    preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public restaurantTable getById(int id) {
        restaurantTable table = null;
        String query = "SELECT * FROM restaurant_table WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                table = new restaurantTable(
                        resultSet.getInt("id"),
                        resultSet.getInt("capacity"),
                        resultSet.getBoolean("available"),
                        resultSet.getInt("reservationId")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return table;
    }

    @Override
    public ObservableList<restaurantTable> getAll() {

        ObservableList<restaurantTable> tables = FXCollections.observableArrayList();

        String query = "SELECT * FROM restaurant_table";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                restaurantTable table = new restaurantTable(
                        resultSet.getInt("id"),
                        resultSet.getInt("capacity"),
                        resultSet.getBoolean("available"),
                        resultSet.getString("description"),
                        resultSet.getInt("reservationId")


                        );
                tables.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    @Override
    public void update(int id,restaurantTable table) {
        String query = "UPDATE restaurant_table SET capacity = ?, available = ?  ,description = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, table.getCapacity());
            statement.setBoolean(2, table.isAvailable());

            System.out.println("data after updated req sql of reservationId="+table.getReservationId());
            statement.setString(3,table.getDescription());
            statement.setInt(4, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM restaurant_table WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public List<restaurantTable> getTablesDispo() {
        List<restaurantTable> tables = new ArrayList<>();
        String query = "SELECT * FROM restaurant_table";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {

                if(resultSet.getBoolean("available") ) {
                    restaurantTable table = new restaurantTable(
                            resultSet.getInt("id"),
                            resultSet.getInt("capacity"),
                            resultSet.getBoolean("available"),
                            resultSet.getInt("reservationId")

                    );
                    tables.add(table);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }


}
