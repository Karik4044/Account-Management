package com.example.quanlytaikhoan.controllers;

import com.example.quanlytaikhoan.Models.Omodels.Admin;
import com.example.quanlytaikhoan.Models.Omodels.User;
import com.example.quanlytaikhoan.Models.DAOList.DAOUser;
import com.example.quanlytaikhoan.Models.DAOList.DAOAdmin;
import com.example.quanlytaikhoan.controllers.Alter.CheckData;
import com.example.quanlytaikhoan.controllers.Alter.Encryption;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController {
    //Dashboard
    private Dashboardcontroller dashboardController;
    private Pane dashboardPane;

    //Profile
    private ProfileController profileController;
    private Pane userprofilepane;

    //ForgotPassword
    private ForgotController forgotController;
    private Pane ForgotPasswordPane;

    //Account
    @FXML
    private Pane AccountListPane;

    //Icon an hien mat khau
    @FXML
    private ImageView  showhideicon, showhideicon1, showhideiconAc;

    @FXML
    protected PasswordField passwordLogin;

    @FXML
    private PasswordField RegisterPasswordField;

    @FXML
    protected TextField usernameLogin;

    @FXML
    protected TextField passwordLoginText;

    @FXML
    private TextField RegisterUserNameTextField;

    @FXML
    private TextField RegisterPasswordTextField;

    @FXML
    private TextField RegisterEmailTextField;

    @FXML
    private TextField IDTextField;

    @FXML
    private TextField UsernameTextField;

    @FXML
    private TextField PasswordTextField;

    @FXML
    private TextField MailTextField;

    @FXML
    private TextField SearchTextField;

    @FXML
    private TextField DeleteTextField;

    @FXML
    protected Label LoginLabel;

    @FXML
    private Label RegistedLabel;

    @FXML
    private Label ButtontoRegister = new Label();

    @FXML
    private Label ButtontoLogin = new Label();

    @FXML
    public Label NameUser1 = new Label();

    @FXML
    public Label WelcomeLabel = new Label();

    @FXML
    private Label NotifyLabel;

    @FXML
    private Label UpdateLabel;

    @FXML
    private Label InsertLabel;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btndashboard;

    @FXML
    private Button btnuser;

    @FXML
    private Button btnprofile;

    @FXML
    private VBox Vboxfunc;

    @FXML
    private Pane RegisterPane;

    @FXML
    protected Pane LoginPane;

    @FXML
    public Pane MainPane;

    @FXML
    private Pane functionpane;

    @FXML
    public Pane OperationPane;

    @FXML
    private Pane PanetoLogout;

    @FXML
    private StackPane Stackmainpane;

    //User list
    @FXML
    private TableView<User> userListTable = new TableView<>();

    @FXML
    private TableColumn<User, Integer> IdCollum = new TableColumn<>("ID");

    @FXML
    private TableColumn<User, String> UsernameCollum = new TableColumn<>("Username");

    @FXML
    private TableColumn<User, String> PasswordCollum = new TableColumn<>("Password");

    @FXML
    private TableColumn<User, String> MailCollum = new TableColumn<>("Mail");


    //AdminList
    @FXML
    private TableView<Admin> AdminUser = new TableView<>();

    @FXML
    private TableColumn<Admin, Integer> IdCollummAdmin = new TableColumn<>("ID");

    @FXML
    private TableColumn<Admin, String> UsernameCollumAdmin = new TableColumn<>("Username");

    @FXML
    private TableColumn<Admin, String> PasswordCollumAdmin = new TableColumn<>("Password");

    @FXML
    private TableColumn<Admin, String> MailCollumAdmin = new TableColumn<>("Mail");


    //Database
    @FXML
    private URL url;

    @FXML
    private ResourceBundle rb;

    //Khai bao bien
    public static String currentusername;

    private final CheckData checkData = new CheckData(); //Tao 1 object cho Lop CheckData
    private DAOUser DAOUser;
    private DAOAdmin DAOAdmin;

    //Kiem tra Password co an hien hay khong
    private final boolean[] isPasswordVisible = {false};
    private boolean areTablePasswordVisible = false;

    private ObservableList<User> observableList; // Danh sach nguoi dung chua du lieu tu CSDL
    private FilteredList<User> filteredUserList; // Danh sach nguoi dung da loc

    //Chuc nang của userlist
    public void initialize() {

        //Khai bao DBUser
        DAOUser = new DAOUser();
        //Khoi tao danh sach nguoi dung
        observableList = FXCollections.observableArrayList();

        //Tao FilteredList cho userListTable
        filteredUserList = new FilteredList<>(observableList, _ -> true);
        userListTable.setItems(filteredUserList);

        //Khai bao cac cot trong bang userListTable
        UserList();
        IdCollum.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        UsernameCollum.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        
        // Sử dụng cellFactory thay vì cellValueFactory để ẩn/hiện mật khẩu
        PasswordCollum.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        PasswordCollum.setCellFactory(_ -> new TableCell<>() {
            @Override
            protected void updateItem(String password, boolean empty) {
                super.updateItem(password, empty);
                if (empty || password == null) {
                    setText(null);
                } else {
                    if (areTablePasswordVisible) {
                        // Decrypt AES cho User
                        String decryptedPassword = Encryption.decryptAES(password);
                        setText(decryptedPassword);
                    } else {
                        setText("******"); // Hiển thị dấu sao nếu mật khẩu không được hiển thị
                    }
                }
            }
        });

        MailCollum.setCellValueFactory(cellData -> cellData.getValue().mailProperty());

        // Khai bao DBAdmin
        DAOAdmin = new DAOAdmin();
        AdminList();
        IdCollummAdmin.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        UsernameCollumAdmin.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        PasswordCollumAdmin.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        MailCollumAdmin.setCellValueFactory(cellData -> cellData.getValue().mailProperty());
        AdminUser.setVisible(false);
        LoginLabel.setVisible(true);
        RegistedLabel.setVisible(true);
        Vboxfunc.setVisible(false);



        //Lang nghe su kien thay doi du lieu trong TextField tim kiem
        SearchTextField.textProperty().addListener((_, _, newValue) -> filteredUserList(newValue));

        //Nut chuyen den pane Login
        ButtontoLogin.setOnMouseClicked(mouseEvent -> {
            try {
                HandleLabelClicks(mouseEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ButtontoLogin.setStyle("-fx-cursor: hand;");

        //Nut chuyen den pane Register
        ButtontoRegister.setOnMouseClicked(mouseEvent -> {
            try {
                handleLabelClick(mouseEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ButtontoRegister.setStyle("-fx-cursor: hand;");

        //Dashboard
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/quanlytaikhoan/dashboard.fxml"));
            fxmlLoader.load();
            dashboardController = fxmlLoader.getController(); // <-- Fix: assign to the field
            dashboardPane = dashboardController.dashboardpane;
            Stackmainpane.getChildren().add(dashboardPane);
            dashboardPane.toBack();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Profile
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/quanlytaikhoan/profile.fxml"));
            fxmlLoader.load();
            profileController = fxmlLoader.getController();
            userprofilepane = profileController.userprofilepane;
            Stackmainpane.getChildren().add(userprofilepane);
            userprofilepane.toBack();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Nut chuyen den pane ForgotPassword
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/quanlytaikhoan/forgot.fxml"));
            fxmlLoader.load();
            forgotController = fxmlLoader.getController();
            ForgotPasswordPane = forgotController.ForgotPasswordPane;
            Stackmainpane.getChildren().add(ForgotPasswordPane);
            ForgotPasswordPane.toBack();
        } catch (IOException e) {
            e.printStackTrace();
        }

        forgotController.setOnBackToLogin(() -> {
            usernameLogin.clear();
            passwordLogin.clear();
            passwordLoginText.clear();
            LoginLabel.setText("");
            LoginPane.toFront();
        });
    }

    //Chuc nang button chinh
    @FXML
    private void HandleClicks(ActionEvent e){
        if(e.getSource() == btnRegister) {
            resetButtonStyles();
            btnRegister.setStyle("-fx-background-color: #fd5a5a; -fx-text-fill: #ffffff; -fx-background-radius: 15px" );
            LoginLabel.setVisible(false);
            RegistedLabel.setVisible(false);
            RegisterUserNameTextField.clear();
            RegisterPasswordField.clear();
            RegisterEmailTextField.clear();
            RegisterPane.toFront();
            OperationPane.toBack();
        }
        if(e.getSource() == btnLogin) {
            resetButtonStyles();
            btnLogin.setStyle("-fx-background-color: #8bff46; -fx-text-fill: #777777; -fx-background-radius: 15px" );
            LoginLabel.setVisible(false);
            RegistedLabel.setVisible(false);
            usernameLogin.clear();
            passwordLogin.clear();
            LoginPane.toFront();
            OperationPane.toBack();
        }
        if(e.getSource() == btnLogout) {
            PanetoLogout.toFront();
        }

        boolean isLoggedIn = !WelcomeLabel.getText().isEmpty() && !NameUser1.getText().isEmpty();

        if (isLoggedIn) {
            if (e.getSource() == btndashboard) {
                resetButtonStyles();
                btndashboard.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black; -fx-background-radius: 15px" );
                dashboardPane.toFront();
                dashboardController.withprofile.setText(String.valueOf(DAOUser.getUserWithInfoCount()));
                OperationPane.toBack();
            }
            if (e.getSource() == btnuser) {
                resetButtonStyles();
                btnuser.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black; -fx-background-radius: 15px" );
                AccountListPane.toFront();
                OperationPane.toFront();
            }
            if (e.getSource() == btnprofile) {
                resetButtonStyles();
                btnprofile.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black; -fx-background-radius: 15px" );
                OperationPane.toBack();
                userprofilepane.toFront();
            }
        }
    }

    //Dua style ve mac dinh
    private void resetButtonStyles() {
        btndashboard.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-radius: 15; " +
                        "-fx-border-color: #FFFFFF; " +
                        "-fx-text-fill: white;"
        );
        btnuser.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-radius: 15; " +
                        "-fx-border-color: #FFFFFF; " +
                        "-fx-text-fill: white;"
        );
        btnprofile.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-radius: 15; " +
                        "-fx-border-color: #FFFFFF; " +
                        "-fx-text-fill: white;"
        );
        btnRegister.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-radius: 15; " +
                        "-fx-border-color: #FFFFFF; " +
                        "-fx-text-fill: white;"
        );
        btnLogin.setStyle(
                "-fx-background-color:  #FF8600; " +
                        "-fx-background-radius: 15; "
        );
    }

    //An hien Mat khau
    @FXML
    private void showhide(ActionEvent e) {

        toggleVisibility(passwordLogin, passwordLoginText, showhideicon, isPasswordVisible);
        toggleVisibility(RegisterPasswordField, RegisterPasswordTextField, showhideicon1, isPasswordVisible);
    }

    @FXML
    private void showhideAc(ActionEvent e) {
        areTablePasswordVisible = !areTablePasswordVisible;
        showhideiconAc.setImage(new Image(areTablePasswordVisible ? "hide.png" : "view.png"));
        userListTable.refresh(); // Refresh bảng để cập nhật trạng thái ẩn hiện mật khẩu
    }

    //Chuc nang an hien mat khau
    public static void toggleVisibility(PasswordField pwf, TextField tfield, ImageView icon, boolean[] isPasswordVisible) {
        isPasswordVisible[0] = !isPasswordVisible[0]; //Dat lai thanh true
        if (pwf.isVisible()) {
            tfield.setText(pwf.getText());
            pwf.setVisible(false);
            pwf.setManaged(false);// Layout khong chiem cho

            tfield.setVisible(true);
            tfield.setManaged(true);
            icon.setImage(new Image("hide.png"));
        } else {
            pwf.setText(tfield.getText());
            tfield.setVisible(false);
            tfield.setManaged(false);

            pwf.setVisible(true);
            pwf.setManaged(true);
            icon.setImage(new Image("view.png"));
        }
    }


    //Chuc nang logout
    @FXML
    private void OutClickYes(ActionEvent event) {
        resetButtonStyles();
        btnLogin.toFront();
        WelcomeLabel.setText("");
        NameUser1.setText("");
        Vboxfunc.setVisible(false);
        LoginLabel.setVisible(false);
        RegistedLabel.setVisible(false);
        usernameLogin.clear();
        passwordLogin.clear();
        passwordLoginText.clear();
        RegisterUserNameTextField.clear();
        RegisterPasswordField.clear();
        RegisterEmailTextField.clear();
        MainPane.toFront();
        OperationPane.toBack();
        PanetoLogout.toBack();
    }

    @FXML
    private void OutClickNo(ActionEvent event) {
        PanetoLogout.toBack();
    }

    //Nut back
    @FXML
    private void Back(ActionEvent e){
        resetButtonStyles();
        if(WelcomeLabel.getText().isEmpty() && NameUser1.getText().isEmpty()) {
            MainPane.toFront();
        } else {
            OperationPane.toFront();
            AccountListPane.toFront();
        }
    }

    //Admin
    //Nut dang ky tai khoan admin
    @FXML
    private void okeregister(ActionEvent event) {
        String username = RegisterUserNameTextField.getText();
        String plainPassword = RegisterPasswordField.getText();
        String mail = RegisterEmailTextField.getText();
        RegistedLabel.setVisible(true);

        if (username.isEmpty() || plainPassword.isEmpty() || mail.isEmpty()) {
            RegistedLabel.setText("Please fill in all fields!");
            return;
        }

        if (!checkData.isValidUsername(username)) {
            RegistedLabel.setText("Please enter a valid username!(No special character)");
            return;
        }

        if (!checkData.isValidEmail(mail) && !checkData.isValidPassword(plainPassword)) {
            RegistedLabel.setText("Invalid password and mail!");
            return;
        }

        if (!checkData.isValidEmail(mail)) {
            RegistedLabel.setText("Invalid mail format!(user@example.com)");
            return;
        }

        if (!checkData.isValidPassword(plainPassword)) {
            RegistedLabel.setText("Password must be at least 8 characters!");
            return;
        }

        // Admin dùng SHA-256 (không thể decrypt)
        String password = Encryption.encrypt(plainPassword);

        DAOAdmin DAOAdmin = new DAOAdmin();
        boolean registered = DAOAdmin.registerUser(username, password, mail);


        if (registered) {
            RegistedLabel.setStyle("-fx-text-fill: #00FF00;");
            RegistedLabel.setText("Account registered successfully!");
        } else {
            RegistedLabel.setStyle("-fx-text-fill: #FF0000;");
            RegistedLabel.setText("Username or email already exists!");
        }
    }

    //Nut chuyen trang login
    @FXML
    private void HandleLabelClicks(MouseEvent mouseEvent) throws IOException {
        if(WelcomeLabel.getText().isEmpty() || NameUser1.getText().isEmpty()) {
            LoginLabel.setVisible(false);
            RegistedLabel.setVisible(false);
            usernameLogin.clear();
            passwordLogin.clear();
            passwordLoginText.clear();
            LoginPane.toFront();
        } else {
            RegistedLabel.setVisible(true);
            RegistedLabel.setText("Please logout first!");
        }
    }

    //Nut chuyen den pane Register
    public void handleLabelClick(MouseEvent ignoredE) throws IOException {
        RegisterUserNameTextField.clear();
        RegisterPasswordField.clear();
        RegisterEmailTextField.clear();
        RegisterPane.toFront();
    }

    //Hien thi ten nguoi dung
    public void displayName(String username) {
        NameUser1.setText(username);
        WelcomeLabel.setText("Welcome");
    }

    //Nut dang nhap
    @FXML
    private void okLogin(ActionEvent e){
        String username = usernameLogin.getText();
        String passwordInput = passwordLogin.isVisible() ? passwordLogin.getText() : passwordLoginText.getText();
        String password = Encryption.encrypt(passwordInput);  // Admin dùng SHA-256
        LoginLabel.setVisible(true);

        if(username.isEmpty() || passwordInput.isEmpty()) {
            LoginLabel.setText("Please fill in all fields!");
            return;
        } else if(!checkData.isValidUsername(username)) {
            LoginLabel.setText("Username not valid!(No special character)");
            return;
        } else if(!checkData.isValidPassword(passwordInput)) {
            LoginLabel.setText("Password must be at least 8 characters!");
            return;
        }

        DAOAdmin DAOAdmin2 = new DAOAdmin();
        boolean login = DAOAdmin2.loginUser(username, password);

        if(login) {
            resetButtonStyles();
            btnuser.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black; -fx-background-radius: 15px" );
            currentusername = username;
            displayName(currentusername);
            Vboxfunc.setVisible(true);
            btnLogout.toFront();
            LoginLabel.setVisible(false);
            InsertLabel.setText("");
            SearchTextField.clear();
            IDTextField.clear();
            UsernameTextField.clear();
            PasswordTextField.clear();
            MailTextField.clear();
            AccountListPane.toFront();
            OperationPane.toFront();
            UserList();
        } else {
            LoginLabel.setText("Wrong username or password!");
            LoginLabel.setVisible(true);
        }
    }


    //Chuc nang quen mat khau
    @FXML
    private void ButtonForgotPassword(ActionEvent e) {
        forgotController.changemailtextfield.clear();
        forgotController.CodeTextField.clear();
        forgotController.ChangePassword.clear();
        forgotController.Comfirmchangedpassword.clear();
        forgotController.Entermaillabel.setVisible(false);
        forgotController.EnterCodeLabel.setVisible(false);
        forgotController.Changelabel.setVisible(false);
        forgotController.EnterMailPane.toFront();
        ForgotPasswordPane.toFront();
    }


    //User
    //Them tai khoan nguoi dung
    @FXML
    private void InsertAccount(ActionEvent e){
        String id = IDTextField.getText();
        String username = UsernameTextField.getText();
        String password = Encryption.encryptAES(PasswordTextField.getText());  // User dùng AES
        String mail = MailTextField.getText();

        if (id.isEmpty() || username.isEmpty() || password.isEmpty() || mail.isEmpty()) {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Please fill in all fields!");
            return;
        }

        if(!checkData.isValidID(id)) {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Id must have 3 numbers!");
            return;
        }

        if(!checkData.isValidUsername(username)) {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Please enter a valid username!(No special character)");
            return;
        }

        if (!checkData.isValidEmail(mail) && !checkData.isValidPassword(password)) {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Invalid password and mail!");
            return;
        }

        if (!checkData.isValidEmail(mail)) {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Invalid mail format!(user@example.com)");
            return;
        }

        if (!checkData.isValidPassword(password)) {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Password must be at least 8 characters!");
            return;
        }

        DAOUser DAOUser1 = new DAOUser();
        boolean insert = DAOUser1.insertUser(id, username, password, mail);

        if(insert) {
            InsertLabel.setStyle("-fx-text-fill: #00FF00");
            InsertLabel.setText("Account added successfully!");
            dashboardController.totallabel.setText(String.valueOf(DAOUser.getUserCount()));
        } else {
            InsertLabel.setStyle("-fx-text-fill: #FF0000");
            InsertLabel.setText("Username or Email already exists!");
        }
        profileController.UserProfile(); //Goi lai phuong thuc moi de lam moi danh sach
        dashboardController.RecentUser(); //Goi lai phuong thuc moi de lam moi danh sach
        dashboardController.totalchart();
        dashboardController.dailychart();
        UserList(); //Goi lai phuong thuc moi de lam moi danh sach
    }

    //Nut cap nhat
    @FXML
    private void updatebutton(ActionEvent e){
        String id = IDTextField.getText();
        String username = UsernameTextField.getText();
        String password = Encryption.encryptAES(PasswordTextField.getText());  // User dùng AES
        String mail = MailTextField.getText();


        //Kiem tra du lieu dau vao
        if (id.isEmpty() || username.isEmpty() || password.isEmpty() || mail.isEmpty()) {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Please fill in all fields!");
            return;
        }

        if(!checkData.isValidID(id)) {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Id must have 3 numbers!");
            return;
        }

        if(!checkData.isValidUsername(username)) {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Please enter a valid username!(No special character)");
            return;
        }

        if (!checkData.isValidEmail(mail) && !checkData.isValidPassword(password)) {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Invalid password and mail!");
            return;
        }

        if (!checkData.isValidEmail(mail)) {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Invalid mail format!(user@example.com)");
            return;
        }

        if (!checkData.isValidPassword(password)) {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Password must be at least 8 characters!");
            return;
        }

        DAOUser DAOUser2 = new DAOUser();
        boolean update = DAOUser2.updateUser(id, username, password, mail);

        if(update) {
            InsertLabel.setStyle("-fx-text-fill: #00FF00;");
            InsertLabel.setText("Update successful!");
        } else {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Update failed. Please try again.");
        }
        UserList();
    }


    @FXML
    private void clearbutton(ActionEvent e){
        IDTextField.clear();
        UsernameTextField.clear();
        PasswordTextField.clear();
        MailTextField.clear();
        SearchTextField.clear();
        DeleteTextField.clear();
        InsertLabel.setText("");
        UserList();
    }

    //Xoa tai khoan nguoi dung
    @FXML
    private void DeleteButton(ActionEvent e) {
        String id = DeleteTextField.getText();

        if (id.isEmpty()) {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Please fill in all fields!");
            return;
        }

        if(!checkData.isValidID(id)) {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Id must have 3 numbers!");
            return;
        }

        DAOUser DAOUser3 = new DAOUser();
        boolean delete = DAOUser3.deleteUser(id);

        if(delete) {
            InsertLabel.setStyle("-fx-text-fill: #00FF00;");
            InsertLabel.setText("Account Deleted");
            dashboardController.totallabel.setText(String.valueOf(DAOUser.getUserCount()));
        } else {
            InsertLabel.setStyle("-fx-text-fill: #FF0000;");
            InsertLabel.setText("Fail to delete!");
        }
        profileController.UserProfile(); //Goi lai phuong thuc moi de lam moi danh sach
        dashboardController.RecentUser(); //Goi lai phuong thuc moi de lam moi danh sach
        dashboardController.totalchart();
        UserList();
    }

    //Danh sach nguoi dung
    @FXML
    void UserList() {
        observableList.setAll(DAOUser.getUserlist());
        // Không cần thay đổi password property ở đây nữa
        filteredUserList(SearchTextField.getText());
    }


    //Danh sach admin
    @FXML
    private void AdminList() {
        AdminUser.setItems(DAOAdmin.getAdminList());
    }

    //Chuc nang loc danh sach nguoi dung theo tu khoa
    private void filteredUserList(String searchText) {
        filteredUserList.setPredicate(user -> {
            if (searchText == null || searchText.trim().isEmpty()) {
                InsertLabel.setText("");
                return true;
            }
            String lowerCaseFilter = searchText.toLowerCase();
            return user.getUsername().toLowerCase().contains(lowerCaseFilter) ||
                    user.getMail().toLowerCase().contains(lowerCaseFilter) ||
                    String.valueOf(user.getId()).contains(lowerCaseFilter);
        });
    }
}