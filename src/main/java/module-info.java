module gourmand {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires java.sql;
    opens gourmand.gui;
    opens gourmand.entity;
    opens gourmand.services;
    opens gourmand;
    opens gourmand.utils;
}