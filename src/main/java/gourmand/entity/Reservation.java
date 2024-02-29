package gourmand.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Reservation {
    private int id;
    private String customerName;
    private LocalDateTime dateTime;
    private int numberPersonnes;
    private List<restaurantTable> tables;
    private String status;

    public Reservation(int id, String customerName, LocalDateTime reservationDatetime, int numberPersonnes, String status) {
        this.id = id;
        this.customerName = customerName;
        this.dateTime = reservationDatetime;
        this.numberPersonnes = numberPersonnes;
        this.status = status;
    }

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getNumberPersonnes() {
        return numberPersonnes;
    }

    public void setNumberPersonnes(int numberPersonnes) {
        this.numberPersonnes = numberPersonnes;
    }

    public List<restaurantTable> getTables() {
        return tables;
    }

    public void setTables(List<restaurantTable> tables) {
        this.tables = tables;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", dateTime=" + dateTime +
                ", numberPersonnes=" + numberPersonnes +
                ", tables=" + tables +
                ", status='" + status + '\'' +
                '}';
    }
}
