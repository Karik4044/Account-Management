module com.example.quanlytaikhoan {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;
    requires jdk.jdi;
    requires jakarta.mail;

    opens com.example.quanlytaikhoan to javafx.fxml;
    exports com.example.quanlytaikhoan.View;
    opens com.example.quanlytaikhoan.View to javafx.fxml;
    exports com.example.quanlytaikhoan.controllers;
    opens com.example.quanlytaikhoan.controllers to javafx.fxml;
    exports com.example.quanlytaikhoan.Models.DAOList;
    opens com.example.quanlytaikhoan.Models.DAOList to javafx.fxml;
    exports com.example.quanlytaikhoan.Database;
    opens com.example.quanlytaikhoan.Database to javafx.fxml;
    exports com.example.quanlytaikhoan.Models.Omodels;
    opens com.example.quanlytaikhoan.Models.Omodels to javafx.fxml;
    exports com.example.quanlytaikhoan.controllers.Alter;
    opens com.example.quanlytaikhoan.controllers.Alter to javafx.fxml;
}
