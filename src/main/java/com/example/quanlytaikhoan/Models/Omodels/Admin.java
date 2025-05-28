package com.example.quanlytaikhoan.Models.Omodels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Lớp này có chức năng lưu trữ thông tin của tài khoản Admin
public class Admin {
    private final IntegerProperty idadmin;
    private final StringProperty usernameadmin;
    private final StringProperty passwordsadmin;
    private final StringProperty mailadmin;

    public Admin(int id, String username, String passwords, String mail) {
        this.idadmin = new SimpleIntegerProperty(id);
        this.usernameadmin = new SimpleStringProperty(username);
        this.passwordsadmin = new SimpleStringProperty(passwords);
        this.mailadmin = new SimpleStringProperty(mail);
    }

    public IntegerProperty idProperty() {
        return idadmin;
    }

    public StringProperty usernameProperty() {
        return usernameadmin;
    }

    public StringProperty passwordProperty() {
        return passwordsadmin;
    }

    public StringProperty mailProperty() {
        return mailadmin;
    }
}
