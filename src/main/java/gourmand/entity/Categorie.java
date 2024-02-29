/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmand.entity;


public class Categorie {
      Integer id;
      int idpro;
      String nom;

    public Categorie(Integer id , String nom) {
       this.id = id;
        this.nom = nom;
    }

    public Categorie() {
    }

    public Categorie(int id, int idpro, String nom) {
        this.id = id;
        this.idpro = idpro;
        this.nom = nom;
    }

    public Categorie(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }


    
    
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdpro() {
        return idpro;
    }

    public void setIdpro(int idpro) {
        this.idpro = idpro;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


   
      
      
    
    
}
