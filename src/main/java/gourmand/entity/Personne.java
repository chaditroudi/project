package gourmand.entity;


public class Personne {
    private int id;

    private String nom;
    private  String prenom;
    private static int age;
    private static String adresse;
    private static String email;
    private static String password;
    private String token;
    private  String role;
    private static int num_tel;




    public static int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public static String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Personne() {

    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", role='" + role + '\'' +
                ", num_tel=" + num_tel +
                '}';
    }

    public int getId() {
        return id;
    }

    public static int getAge() {
        return age;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public Personne(int id, String nom, String prenom, int age, String adresse, String email, String password, String token, String role, int num_tel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresse = adresse;
        this.email = email;
        this.password = password;
        this.token = token;
        this.role = role;
        this.num_tel = num_tel;
    }
    public Personne(String nom, String prenom, int age, String adresse, String email, String password, String token, String role, int num_tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresse = adresse;
        this.email = email;
        this.password = password;
        this.token = token;
        this.role = role;
        this.num_tel = num_tel;
    }

}