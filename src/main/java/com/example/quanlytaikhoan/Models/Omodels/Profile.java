package com.example.quanlytaikhoan.Models.Omodels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Lớp này có chức năng lưu trữ thông tin của tài khoản Profile
public class Profile {
    private final IntegerProperty id;
    private final IntegerProperty accountId;
    private final StringProperty nameuser;
    private final StringProperty gender;
    private final StringProperty tele;
    private final StringProperty address;

    public Profile(int id, int accountId, String nameuser, String gender, String tele, String address) {
        this.id = new SimpleIntegerProperty(id);
        this.accountId = new SimpleIntegerProperty(accountId);
        this.nameuser = new SimpleStringProperty(nameuser);
        this.gender = new SimpleStringProperty(gender);
        this.tele = new SimpleStringProperty(tele);
        this.address = new SimpleStringProperty(address);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty accountIdProperty() {
        return accountId;
    }

    public StringProperty nameuserProperty() {
        return nameuser;
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public StringProperty teleProperty() {
        return tele;
    }

    public StringProperty addressProperty() {
        return address;
    }

    public int getId() {
        return id.get();
    }

    public int getAccountId() {
        return accountId.get();
    }

    public String getNameuser() {
        return nameuser.get();
    }

    public String getGender() {
        return gender.get();
    }

    public String getTele() {
        return tele.get();
    }

    public String getAddress() {
        return address.get();
    }
}
