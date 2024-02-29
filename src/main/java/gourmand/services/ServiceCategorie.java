package gourmand.services;


import gourmand.entity.Categorie;
import gourmand.utils.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServiceCategorie implements IService<Categorie> {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    ObservableList<Categorie> categories = FXCollections.observableArrayList();

    Connection myConnex;
    Statement ste;

    public ServiceCategorie() {
        try {
            con = Database.getInstance().getConn();
            ste = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @Override
    public void create(Categorie Categorie) {
        String req = "INSERT INTO categorie(nom) VALUES (?)";
        try {
            ps = con.prepareStatement(req);
            ps.setString(1, Categorie.getNom());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Categorie getById(int id) {
        Categorie Categorie = null;
        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM categorie WHERE id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Categorie = new Categorie(
                        rs.getInt("id"),
                        rs.getString("nom")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Categorie;
    }

    @Override
    public ObservableList<Categorie> getAll() {
        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM categorie");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String nom = res.getString("nom");
                categories.add(new Categorie(id, nom));
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    @Override
    public void update(int id ,Categorie Categorie) {
        try {
            PreparedStatement pre = con.prepareStatement("UPDATE categorie SET nom = ? WHERE id = ?");
            pre.setString(1, Categorie.getNom());
            pre.setInt(2, id);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            String req2 = "DELETE FROM categorie WHERE id=?";
            ps = con.prepareStatement(req2);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
