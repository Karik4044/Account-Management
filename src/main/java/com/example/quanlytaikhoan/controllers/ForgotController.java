package com.example.quanlytaikhoan.controllers;

import com.example.quanlytaikhoan.Models.DAOList.DAOAdmin;
import com.example.quanlytaikhoan.controllers.Alter.CheckData;
import com.example.quanlytaikhoan.controllers.Alter.Encryption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ForgotController {

    @FXML
    protected PasswordField ChangePassword;

    @FXML
    protected Pane ChangePasswordPane;

    @FXML
    protected TextField ChangePasswordText;

    @FXML
    protected Label Changelabel;

    @FXML
    protected TextField CodeTextField;

    @FXML
    protected PasswordField Comfirmchangedpassword;

    @FXML
    protected TextField ComfirmchangedpasswordText;

    @FXML
    protected Label EnterCodeLabel;

    @FXML
    protected Pane EnterCodePane;

    @FXML
    protected Pane EnterMailPane;

    @FXML
    protected Label Entermaillabel;

    @FXML
    protected Pane ForgotPasswordPane;

    @FXML
    protected AnchorPane Switch5;

    @FXML
    protected Pane Updatedpane;

    @FXML
    protected TextField changemailtextfield;

    @FXML
    protected ImageView showhideicon12;

    @FXML
    protected ImageView showhideicon121;

    private final CheckData checkData = new CheckData();
    private String generate = ""; // Khai bao 1 object de luu gia tri code random

    private final boolean[] isPasswordVisible = {false};

    //Khai báo callback Runnable để controller chính có thể truyền vào.
    private Runnable onBackToLogin;

    //Tạo setter để controller chính có thể truyền callback vào.
    public void setOnBackToLogin(Runnable callback) {
        this.onBackToLogin = callback;
    }

    //Khi người dùng bấm nút "Back to Login", gọi callback này
    @FXML
    void BacktoLogin(ActionEvent event) {
        if (onBackToLogin != null) {
            onBackToLogin.run(); // Gọi hàm do controller chính truyền vào
        }
    }

    @FXML
    void ChangeButton(ActionEvent event) {
        Changelabel.setVisible(true);

        // Get password from the visible field
        String plainPassword = ChangePassword.isVisible() ? ChangePassword.getText() : ChangePasswordText.getText();
        String plainConfirm = Comfirmchangedpassword.isVisible() ? Comfirmchangedpassword.getText() : ComfirmchangedpasswordText.getText();
        String mail = changemailtextfield.getText();

        if (plainPassword.isEmpty() || plainConfirm.isEmpty()) {
            Changelabel.setText("Please fill in all fields!");
            return;
        }
        if (!checkData.isValidPassword(plainPassword)) {
            Changelabel.setText("Password must be at least 8 characters!");
            return;
        }
        if (!plainPassword.equals(plainConfirm)) {
            Changelabel.setText("Password not match!");
            return;
        }

        String encryptedPassword = Encryption.encrypt(plainPassword);

        DAOAdmin DAOAdmin4 = new DAOAdmin();
        boolean changepassword = DAOAdmin4.changePassword(encryptedPassword, mail);

        if (changepassword) {
            Updatedpane.toFront();
        }
        ChangePassword.clear();
        Comfirmchangedpassword.clear();
        ChangePasswordText.clear();
        ComfirmchangedpasswordText.clear();
    }

    @FXML
    void Confirmcode(ActionEvent event) {
        EnterCodeLabel.setVisible(true);
        String code = CodeTextField.getText();
        if(code.length() != 4) {
            EnterCodeLabel.setText("Code must be at least 4 characters!");
        }
        if(code.equals(generate)) {
            ChangePasswordPane.toFront();
        } else {
            EnterCodeLabel.setText("Code not match!");
        }
    }

    @FXML
    void SendCode(ActionEvent event) {
        Entermaillabel.setVisible(true);
        String mail = changemailtextfield.getText();

        if (!checkData.isValidEmail(mail)) {
            Entermaillabel.setText("Invalid email format!");
            return;
        }

        DAOAdmin DAOAdmin3 = new DAOAdmin();
        boolean sendcoded = DAOAdmin3.sendcode(mail);

        if(sendcoded) {
            generate = String.valueOf(checkData.random());
            EnterCodePane.toFront();
            System.out.println("Verify code: " + generate);
        } else {
            Entermaillabel.setText("Account not found!");
        }
    }

    @FXML
    void showhide(ActionEvent event) {
        MainController.toggleVisibility(ChangePassword, ChangePasswordText, showhideicon121, isPasswordVisible);
    }

    @FXML
    void showhide2(ActionEvent event) {
        MainController.toggleVisibility(Comfirmchangedpassword, ComfirmchangedpasswordText, showhideicon12, isPasswordVisible);
    }

}
