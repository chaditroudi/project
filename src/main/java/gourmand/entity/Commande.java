package gourmand.entity;
import java.util.Objects;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

public class Commande {
    private int id;
    private int  idPanier; // Use Integer to handle nullable column
    private Date date;
    private String adresse_dest;
    private double prix_total; // Use Integer to handle nullable column

    private String status;

    private int idPersonne;
    // Constructors, getters, and setters
    // Constructor with all fields
    public Commande(int id, Integer idPanier, Date date, String adresse_dest, double prix_total,Integer idPersonne,String status) {
        this.id = id;
        this.idPanier = idPanier;
        this.status = status;
        this.date = date;
        this.adresse_dest = adresse_dest;
        this.prix_total = prix_total;
        this.idPersonne = idPersonne;
    }

    public Commande() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdPanier() {
        return idPanier;
    }


    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    public int getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAdresse_dest() {
        return adresse_dest;
    }

    public void setAdresse_dest(String adresse_dest) {
        this.adresse_dest = adresse_dest;
    }

    public double getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(double prix_total) {
        this.prix_total = prix_total;
    }
}
