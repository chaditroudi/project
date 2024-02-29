package gourmand.entity;

public class restaurantTable {
    private int id;
    private int capacity;
    private boolean available;

    private  String description;
    private int reservationId; // Foreign key referencing Reservation table

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public restaurantTable(int id, int capacity, boolean available, int reservationId) {
        this.id = id;
        this.capacity = capacity;
        this.available = available;
        this.reservationId = reservationId;
    }

    public restaurantTable(int id, int capacity, boolean available, String description, int reservationId) {
        this.id = id;
        this.capacity = capacity;
        this.available = available;
        this.description = description;
        this.reservationId = reservationId;
    }


    public restaurantTable(int capacity, boolean available, String description, int reservationId) {
        this.capacity = capacity;
        this.available = available;
        this.description = description;
        this.reservationId = reservationId;
    }

    public restaurantTable() {

    }
}
