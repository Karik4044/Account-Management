package com.example.quanlytaikhoan.Models.Omodels;

import javafx.beans.property.*;

// Lớp này có chức năng lưu trữ thông tin của tài khoản User
public class User {
    private final IntegerProperty id;
    private final StringProperty username;
    private final StringProperty passwords;
    private final StringProperty mail;
    private final StringProperty dates;
    private final StringProperty deletedate;
    private final BooleanProperty info;

    public User(String id, boolean info) {
        this.id = new SimpleIntegerProperty(Integer.parseInt(id));
        this.username = new SimpleStringProperty("");
        this.passwords = new SimpleStringProperty("");
        this.mail = new SimpleStringProperty("");
        this.dates = new SimpleStringProperty("");
        this.deletedate = new SimpleStringProperty("");
        this.info = new SimpleBooleanProperty(info);
    }

    public User(String username, String dates) {
        this.id = new SimpleIntegerProperty(0); // default
        this.username = new SimpleStringProperty(username);
        this.passwords = new SimpleStringProperty("");
        this.mail = new SimpleStringProperty("");
        this.dates = new SimpleStringProperty(dates);
        this.deletedate = new SimpleStringProperty("");
        this.info = new SimpleBooleanProperty(false); // default
    }

    public User(int id, String username, String passwords, String mail, String dates) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.passwords = new SimpleStringProperty(passwords);
        this.mail = new SimpleStringProperty(mail);
        this.dates = new SimpleStringProperty(dates);
        this.deletedate = new SimpleStringProperty("");
        this.info = new SimpleBooleanProperty(false); // default
    }

    public User(int id, String username, String passwords, String mail, String dates, String deletedate) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.passwords = new SimpleStringProperty(passwords);
        this.mail = new SimpleStringProperty(mail);
        this.dates = new SimpleStringProperty(dates);
        this.deletedate = new SimpleStringProperty(deletedate);
        this.info = new SimpleBooleanProperty(false); // default
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return passwords;
    }

    public StringProperty mailProperty() {
        return mail;
    }

    public StringProperty dateProperty() {
        return dates;
    }

    public BooleanProperty InfoProperty() {
        return info;
    }

    public void setPasswords(String passwords) {
        this.passwords.set(passwords);
    }

    public String getDate() {
        return dates.get();
    }

    // Thêm getter
    public String getDeletedDate() {
        return deletedate.get();
    }

    // Thêm property nếu cần
    public StringProperty deletedDateProperty() {
        return deletedate;
    }

    public int getId() {
        return id.get();
    }

    public String getUsername() {
        return username.get();
    }

   public String getPasswords() {
       return passwords.get();
   }

//    public StringProperty passwordsProperty() {
//        return passwords;
//    }

    public String getMail() {
        return mail.get();
    }

    public String getDates() {
        return dates.get();
    }

//    public StringProperty datesProperty() {
//        return dates;
//    }
//
//    public String getDeletedate() {
//        return deletedate.get();
//    }
//
//    public StringProperty deletedateProperty() {
//        return deletedate;
//    }
//
//    public boolean isInfo() {
//        return info.get();
//    }
//
//    public BooleanProperty infoProperty() {
//        return info;
//    }
}
