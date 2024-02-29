package gourmand.services;

import gourmand.entity.Categorie;
import gourmand.utils.Database;
import gourmand.entity.Panier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicePanier {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public ServicePanier() {
        connect = Database.getInstance().getConn();
    }

    public boolean addToPanier(Panier panier) {
        try {
            String insertData = "INSERT INTO panier (productId, quantity,personneId,prix_total) VALUES (?, ?, ?,?)";
            prepare = connect.prepareStatement(insertData);
            prepare.setInt(1, panier.getProductId());
            prepare.setInt(2, panier.getQuantity());
            prepare.setInt(3, panier.getPersonneId());
            prepare.setDouble(4,panier.getPrix_total());
            prepare.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public Panier getById(int id) {
        Panier panier = new Panier();
        try {
            PreparedStatement st = connect.prepareStatement("SELECT * FROM panier WHERE id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                panier.setPrix_total(rs.getDouble("prix_total"));
                panier.setProductId(rs.getInt("productId"));
                panier.setQuantity(rs.getInt("quantity"));
                panier.setPersonneId(rs.getInt("personneId"));


            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return panier;
    }

}
