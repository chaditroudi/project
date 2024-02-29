package gourmand.entity;

import java.util.ArrayList;
import java.util.List;

public class Livraison {
    private int id;
    private String adresse_depart;
    private String adresse_arrive;
    private String etat;
    private int personneId; // Changed from id_user
    private int commandeId; // New field for the foreign key
    private String date_reception;

    public Livraison() {
    }

    public Livraison(int id, String etat, String date_reception) {
        this.id = id;
        this.etat = etat;
        this.date_reception = date_reception;
    }

    public Livraison(int id, String adresse_depart, String adresse_arrive, String etat, int personneId, int commandeId, String date_reception) {
        this.id = id;
        this.adresse_depart = adresse_depart;
        this.adresse_arrive = adresse_arrive;
        this.etat = etat;
        this.personneId = personneId;
        this.commandeId = commandeId;
        this.date_reception = date_reception;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_reception() {
        return date_reception;
    }

    public void setDate_reception(String date_reception) {
        this.date_reception = date_reception;
    }

    public String getAdresse_depart() {
        return adresse_depart;
    }

    public void setAdresse_depart(String adresse_depart) {
        this.adresse_depart = adresse_depart;
    }

    public String getAdresse_arrive() {
        return adresse_arrive;
    }

    public void setAdresse_arrive(String adresse_arrive) {
        this.adresse_arrive = adresse_arrive;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getPersonneId() {
        return personneId;
    }

    public void setPersonneId(int personneId) {
        this.personneId = personneId;
    }

    public int getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(int commandeId) {
        this.commandeId = commandeId;
    }
}
