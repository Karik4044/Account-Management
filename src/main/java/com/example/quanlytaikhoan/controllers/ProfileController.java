package com.example.quanlytaikhoan.controllers;

import com.example.quanlytaikhoan.Models.DAOList.DAOUser;
import com.example.quanlytaikhoan.Models.Omodels.User;
import com.example.quanlytaikhoan.controllers.Alter.CheckData;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import com.example.quanlytaikhoan.Models.DAOList.DAOProfile;
import com.example.quanlytaikhoan.Models.Omodels.Profile;

//Lớp này quản lý thông tin cá nhân của người dùng, bao gồm việc hiển thị, cập nhật và xóa thông tin cá nhân.
public class ProfileController {

    @FXML
    private AnchorPane Switch2;

    @FXML
    private ChoiceBox<String> genderprofile;

    //User Account
    @FXML
    private Label idlabel;

    @FXML
    private Label unamelabel;

    @FXML
    private Label maillabel;

    @FXML
    private Label datelabel;

    //User Information
    @FXML
    private Label namelabel;

    @FXML
    private Label genderlabel;

    @FXML
    private Label telelabel;

    @FXML
    private Label addresslabel;

    @FXML
    private Label profilelabel;

    @FXML
    private Label UpdateLabel;

    @FXML
    private TextField idprofile;

    @FXML
    private TextField userprofile;

    @FXML
    private TextField teleprofile;

    @FXML
    private TextArea addressprofile;

    @FXML
    private TableView<User> status_table = new TableView<>();

    @FXML
    private TableColumn<User, String> statusid = new TableColumn<>("ID");

    @FXML
    private TableColumn<User, Boolean> status_info = new TableColumn<>("Information");


    @FXML
    public Pane userprofilepane;

    private final DAOUser DAOUser = new DAOUser();
    private String[] gender = {"Male", "Female", "Other"};
    private final CheckData checkData = new CheckData();
    private Dashboardcontroller dashboardcontroller;

    // Only call UserProfile() once in initialize()
    public void initialize() {
        UserProfile();
        statusid.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject().asString());
        status_info.setCellValueFactory(cellData -> {
            DAOProfile DAOProfile = new DAOProfile();
            Profile profile = DAOProfile.getProfileByAccountId(cellData.getValue().getId());
            cellData.getValue().InfoProperty().set(profile != null);
            return cellData.getValue().InfoProperty();
        });

        //Dùng setCellFactory để cập nhật cột trạng thái thông tin
        status_info.setCellFactory(collum -> new TableCell<User, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Yes" : "No");

                    if(item) {
                        setStyle("-fx-text-fill: green;");
                    } else {
                        setStyle("-fx-text-fill: red;");
                    }
                }
            }
        });

        //Gender
        genderprofile.getItems().addAll(gender);

        // Set up the table to show user information
        status_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                showUserInfo(newSel);
                UpdateLabel.setText("");
            }
        });

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quanlytaikhoan/dashboard.fxml"));
            loader.load();
            dashboardcontroller = loader.getController();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Remove any unnecessary calls to UserProfile() elsewhere

    @FXML
    public void UserProfile() {
        ObservableList<User> list = DAOUser.getuserprofile();
        status_table.setItems(list);
    }

    @FXML
    void Addprofile(ActionEvent event) {
        String idText = idprofile.getText();
        String nameuser = userprofile.getText();
        String gender = genderprofile.getValue();
        String tele = teleprofile.getText();
        String address = addressprofile.getText();

        // Validate input data
        if(idText.isEmpty() || nameuser.isEmpty() || gender == null || tele.isEmpty() || address.isEmpty()) {
            profilelabel.setStyle("-fx-text-fill: #FF0000;");
            profilelabel.setText("Please fill in all fields!");
            return;
        }

        if(!checkData.isValidID(idText)) {
            profilelabel.setStyle("-fx-text-fill: #FF0000;");
            profilelabel.setText("Id must have 3 numbers!");
            return;
        }

        if(!checkData.isValidName(nameuser)) {
            profilelabel.setStyle("-fx-text-fill: #FF0000;");
            profilelabel.setText("Name must not contain special characters!");
            return;
        }

        if(!checkData.isValidPhoneNumber(tele)) {
            profilelabel.setStyle("-fx-text-fill: #FF0000;");
            profilelabel.setText("Phone number must have 10 numbers!");
            return;
        }

        int id = Integer.parseInt(idprofile.getText());
        int accountId = id; // Nếu id của USERINFO trùng với ACCOUNTUSER

        DAOProfile DAOProfile = new DAOProfile();
        boolean updated = DAOProfile.updateUserInfo(id, accountId, nameuser, gender, tele, address);


        if (updated) {
            profilelabel.setStyle("-fx-text-fill: green;");
            profilelabel.setText("Update successfully!");
        } else {
            profilelabel.setStyle("-fx-text-fill: red;");
            profilelabel.setText("Update failed!");
        }
        UserProfile();
    }

    @FXML
    void clearprofile(ActionEvent event) {
        idprofile.clear();
        userprofile.clear();
        teleprofile.clear();
        addressprofile.clear();
        genderprofile.setValue(null);
        profilelabel.setText("");
        status_table.getSelectionModel().clearSelection();
        clearlabel();
        clearinfolabel();
    }

    private void clearlabel() {
        idlabel.setText("");
        unamelabel.setText("");
        maillabel.setText("");
        datelabel.setText("");
    }

    @FXML
    void DeleInfo(ActionEvent event) {
        String idText = idlabel.getText();
        if (idText == null || idText.isEmpty()) {
            UpdateLabel.setStyle("-fx-text-fill: red;");
            UpdateLabel.setText("No user selected!");
            return;
        }
        int id = Integer.parseInt(idlabel.getText());
        DAOProfile DAOProfile = new DAOProfile();
        DAOProfile.deleteUserInfo(id);
        UserProfile();
        UpdateLabel.setText("");
        clearinfolabel();
    }

    @FXML
    void updateprofile(ActionEvent event) {
        String idText = idlabel.getText();
        if (idText == null || idText.isEmpty()) {
            UpdateLabel.setStyle("-fx-text-fill: red;");
            UpdateLabel.setText("No user selected!");
            return;
        }
        int id = Integer.parseInt(idText);
        DAOProfile DAOProfile = new DAOProfile();
        Profile profile = DAOProfile.getProfileByAccountId(id);
        if (profile != null) {
            idprofile.setText(String.valueOf(profile.getId()));
            userprofile.setText(profile.getNameuser());
            genderprofile.setValue(profile.getGender());
            teleprofile.setText(profile.getTele());
            addressprofile.setText(profile.getAddress());
            UpdateLabel.setText("");
        } else {
            UpdateLabel.setStyle("-fx-text-fill: red;");
            UpdateLabel.setText("No information found for this user.");
        }
    }

    private void clearinfolabel() {
        namelabel.setText("");
        genderlabel.setText("");
        telelabel.setText("");
        addresslabel.setText("");
        UpdateLabel.setText("");
    }

    private void showUserInfo(User user) {
        // Lấy thông tin ACCOUNTUSER
        idlabel.setText(String.valueOf(user.getId()));
        unamelabel.setText(user.getUsername());
        maillabel.setText(user.getMail());
        datelabel.setText(user.getDates());

        // Lấy thông tin USERINFO (nếu có)
        DAOProfile DAOProfile = new DAOProfile();
        Profile profile = DAOProfile.getProfileByAccountId(user.getId());
        if (profile != null) {
            namelabel.setText(profile.getNameuser());
            genderlabel.setText(profile.getGender());
            telelabel.setText(profile.getTele());
            addresslabel.setText(profile.getAddress());
        } else {
            namelabel.setText("");
            genderlabel.setText("");
            telelabel.setText("");
            addresslabel.setText("");
        }
    }
}