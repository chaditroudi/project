package gourmand.entity;

public class Panier {
    private int id;
    private int personneId;
    private int productId;
    private int quantity;

    private double prix_total;


    public Panier(int id, int personneId, int productId, int quantity,double prix_total) {
        this.personneId = personneId;
        this.productId = productId;
        this.quantity = quantity;
        this.id = id;
        this.prix_total = prix_total;

    }

    public double getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(double prix_total) {
        this.prix_total = prix_total;
    }

    public  Panier(){

    }
    // Implement as needed based on your requirements


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonneId() {
        return personneId;
    }

    public void setPersonneId(int personneId) {
        this.personneId = personneId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
