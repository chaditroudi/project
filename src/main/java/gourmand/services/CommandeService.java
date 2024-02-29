package gourmand.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gourmand.entity.Commande;
import gourmand.entity.Panier;
import gourmand.entity.Reservation;
import gourmand.utils.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommandeService implements IService<Commande> {
    private final Connection myConnex;

    public CommandeService() {
        this.myConnex = Database.getInstance().getConn();
    }

    @Override
    public void create(Commande c) {
        String req = "INSERT INTO commande (idPanier,adresse_dest, date, prix_total,idPersonne,status) VALUES (?,?, ?, ?,?,?)";
        try (PreparedStatement ps = myConnex.prepareStatement(req, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1,c.getIdPanier());
            ps.setString(2, c.getAdresse_dest());
            ps.setDate(3,c.getDate());
            ps.setDouble(4, c.getPrix_total());
            ps.setInt(5,c.getIdPersonne());
            ps.setString(6,c.getStatus());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating Commande failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    c.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating Commande failed, no ID obtained.");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Commande getById(int id) {
        Commande commande = null;
        String query = "SELECT * FROM commande WHERE id = ?";
        try (PreparedStatement statement = myConnex.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    commande = new Commande(
                            resultSet.getInt("id"),
                            resultSet.getInt("idPanier"),
                            resultSet.getDate("date"),
                            resultSet.getString("adresse_dest"),
                            resultSet.getDouble("prix_total"),
                            resultSet.getInt("idPersonne"),
                            resultSet.getString("status")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return commande;
    }

    @Override
    public ObservableList<Commande> getAll() {

        ObservableList<Commande> commandes = FXCollections.observableArrayList();

        String query = "SELECT * FROM commande";

        try (PreparedStatement statement = myConnex.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Commande commande = new Commande(
                        resultSet.getInt("id"),
                        resultSet.getInt("idPanier"),
                        resultSet.getDate("date"),
                        resultSet.getString("adresse_dest"),
                        resultSet.getDouble("prix_total"),
                        resultSet.getInt("idPersonne"),
                        resultSet.getString("status")


                );

                commandes.add(commande);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return commandes;
    }

    @Override
    public void update(int id, Commande c) {
        String req = "UPDATE commande SET adresse_dest = ?, idPanier=? ,status = ? , date = ? WHERE id = ?";
        try (PreparedStatement ps = myConnex.prepareStatement(req)) {
            ps.setString(1, c.getAdresse_dest());
            ps.setInt(2, c.getIdPanier());
            ps.setString(3,c.getStatus());
            ps.setDate(4,c.getDate());

            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM commande WHERE id = ?";
        try (PreparedStatement ps = myConnex.prepareStatement(req)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public List<Panier> getAllPanier() {
        List<Panier> paniers = new ArrayList<>();
        String query = "SELECT * FROM panier";

        try (PreparedStatement statement = myConnex.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Panier panier = new Panier(
                        resultSet.getInt("id"),
                        resultSet.getInt("personneId"),
                        resultSet.getInt("productId"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("prix_total")



                );

                paniers.add(panier);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePanier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paniers;
    }

}
