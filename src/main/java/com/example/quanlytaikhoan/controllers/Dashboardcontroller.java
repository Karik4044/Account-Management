package com.example.quanlytaikhoan.controllers;

import com.example.quanlytaikhoan.Models.DAOList.DAOUser;
import com.example.quanlytaikhoan.Models.Omodels.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.*;

public class Dashboardcontroller {

    @FXML
    private AnchorPane Switch1;

    @FXML 
    public Pane dashboardpane;

    @FXML
    private TableView<User> recenttable = new TableView<>();

    @FXML
    private TableColumn<User, String> recentusername = new TableColumn<>("Username");

    @FXML
    private TableColumn<User, String> date = new TableColumn<>("Date");

    @FXML 
    public Label totallabel;

    @FXML
    public Label withprofile;

    @FXML
    public LineChart<String, Number> totalchart;

    @FXML
    private BarChart<String, Number> dailychart;

    private final DAOUser DAOUser = new DAOUser();

    public void initialize() {
        DAOUser DAOUser = new DAOUser();

        //Get total user
        int total = DAOUser.getUserCount();
        totallabel.setText(String.valueOf(total));

        //Get total user with info
        int totalwithinfo = DAOUser.getUserWithInfoCount();
        withprofile.setText(String.valueOf(totalwithinfo));

        //Set recent user
        RecentUser();
        recentusername.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        date.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
    
        totalchart();
        dailychart();
    }

    @FXML
    public void RecentUser() {
        ObservableList<User> list = DAOUser.getRecentUser();
        recenttable.setItems(list);
    }

    public void totalchart() {
        ObservableList<User> list = DAOUser.getUserlist();

        // Đếm số tài khoản tạo mới và bị xóa theo ngày
        Map<String, Integer> createdPerDay = new LinkedHashMap<>();
        Map<String, Integer> deletedPerDay = new LinkedHashMap<>();
        for (User user : list) {
            String created = user.getDate();
            if (created.contains(" ")) created = created.split(" ")[0];
            createdPerDay.put(created, createdPerDay.getOrDefault(created, 0) + 1);

            String deleted = user.getDeletedDate();
            if (deleted != null && !deleted.isEmpty()) {
                if (deleted.contains(" ")) deleted = deleted.split(" ")[0];
                deletedPerDay.put(deleted, deletedPerDay.getOrDefault(deleted, 0) + 1);
            }
        }

        // Lấy tất cả ngày xuất hiện (tạo hoặc xóa)
        Set<String> allDates = new TreeSet<>();
        allDates.addAll(createdPerDay.keySet());
        allDates.addAll(deletedPerDay.keySet());

        // Tính tổng dồn
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        int cumulative = 0;
        for (String date : allDates) {
            cumulative += createdPerDay.getOrDefault(date, 0);
            cumulative -= deletedPerDay.getOrDefault(date, 0);
            series.getData().add(new XYChart.Data<>(date, cumulative));
        }
        series.setName("Tổng tài khoản còn lại theo ngày");
        totalchart.getData().clear();
        totalchart.getData().add(series);
    }

    
    public void dailychart() {
        // Count accounts created per day
        ObservableList<User> list = DAOUser.getUserlist();
        Map<String, Integer> createdPerDay = new LinkedHashMap<>();
        for (User user : list) {
            String created = user.getDate();
            if (created.contains(" ")) created = created.split(" ")[0];
            createdPerDay.put(created, createdPerDay.getOrDefault(created, 0) + 1);
        }

        // Plot number of accounts created each day
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : createdPerDay.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        series.setName("Số tài khoản tạo mới mỗi ngày");
        dailychart.getData().clear();
        dailychart.getData().add(series);
    }
}

