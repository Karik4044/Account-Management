package com.example.quanlytaikhoan.controllers.Alter;

import java.util.Random;

//Kiem tra du lieu dau vao
public class CheckData {
    public boolean isValidEmail(String email) {
        if (email.isEmpty()) {
            return false;
        }
        String emailRegex = ".+@.+\\..+"; //Su dung bieu thuc chinh quy de kiem tra dinh dang mail
        return email.matches(emailRegex); //Kiem tra mail co khop voi regex
    }

    public boolean isValidPassword(String password) {
        if(password.isEmpty()) {
            return false;
        }
        return password.length() >= 8;
    }

    public boolean isValidUsername(String username) {
        if (username.isEmpty()) {
            return false;
        }
        String usernameRegex = "^[a-zA-Z][a-zA-Z0-9]*$";
        return username.matches(usernameRegex);
    }

    public boolean isValidName(String name) {
        if (name.isEmpty()) {
            return false;
        }
        String nameRegex = "^[a-zA-Z\\s"
                + "ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúý"
                + "ĂăĐđĨĩŨũƠơƯư"
                + "ẠạẢảẤấẦầẨẩẪẫẬậẮắẰằẲẳẴẵẶặ"
                + "ẸẹẺẻẼẽẾếỀềỂểỄễỆệ"
                + "ỈỉỊịỌọỎỏỐốỒồỔổỖỗỘộỚớỜờỞởỠỡỢợ"
                + "ỤụỦủỨứỪừỬửỮữỰự"
                + "ỲỳỴỵỶỷỸỹ"
                + "]+$";

        return name.matches(nameRegex);
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.isEmpty()) {
            return false;
        }
        String phoneRegex = "^\\d{10}$"; // Kiem tra so
        return phoneNumber.matches(phoneRegex);
    }

    public boolean isValidID(String id) {
        return id.length() == 3 && id.matches("\\d{3}");
    }


    //Chuc nang tao code OTP ngau nhien
    //Random code
    public int random() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }
}
