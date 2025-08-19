package presentation.View.InvoiceStatistics;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import presentation.Controller.InvoiceStatisticController;
import presentation.Controller.InvoiceStatisticController.StatisticResult;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class InvoiceStatisticFormView {

    @FXML
    private TableColumn<MonthlyStatItem, Double> averageColumn;

    @FXML
    private TableView<MonthlyStatItem> averageTable;

    @FXML
    private Label dailyLabel;

    @FXML
    private Label hourlyLabel;

    @FXML
    private TableColumn<MonthlyStatItem, String> monthColumn;

    @FXML
    private TableColumn<MonthlyStatItem, String> yearColumn;

    private InvoiceStatisticController statisticController;
    private DecimalFormat currencyFormat = new DecimalFormat("#,###.00");

    @FXML
    public void initialize() {
        // Thiết lập các cột của bảng
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));
        
        // Định dạng cột average để hiển thị tiền tệ
        averageColumn.setCellFactory(column -> new javafx.scene.control.TableCell<MonthlyStatItem, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(item) + " VND");
                }
            }
        });
    }

    public void setStatisticController(InvoiceStatisticController controller) {
        this.statisticController = controller;
    }

    public void loadStatistics() {
        if (statisticController != null) {
            try {
                // Load basic statistics
                StatisticResult result = statisticController.getBasicStatistics();
                hourlyLabel.setText("Số lượng thuê theo giờ: " + result.hourlyInvoiceCount);
                dailyLabel.setText("Số lượng thuê theo ngày: " + result.dailyInvoiceCount);

                // Load monthly averages
                Map<String, Double> monthlyAverages = statisticController.getAverageTotalByMonthYear();
                loadMonthlyAverageTable(monthlyAverages);

                System.out.println("Statistics loaded successfully");
            } catch (Exception e) {
                hourlyLabel.setText("Số lượng thuê theo giờ: Lỗi");
                dailyLabel.setText("Số lượng thuê theo ngày: Lỗi");
                System.err.println("Error loading statistics: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void loadMonthlyAverageTable(Map<String, Double> monthlyAverages) {
        List<MonthlyStatItem> items = new ArrayList<>();
        
        for (Map.Entry<String, Double> entry : monthlyAverages.entrySet()) {
            String key = entry.getKey(); // Format: MM/yyyy
            String[] parts = key.split("/");
            if (parts.length == 2) {
                String month = parts[0];
                String year = parts[1];
                Double average = entry.getValue();
                
                MonthlyStatItem item = new MonthlyStatItem();
                item.setMonth(month);
                item.setYear(year);
                item.setAverage(average);
                items.add(item);
            }
        }
        
        // Sắp xếp theo năm và tháng
        items.sort((a, b) -> {
            int yearCompare = b.getYear().compareTo(a.getYear()); // Năm mới nhất trước
            if (yearCompare == 0) {
                return b.getMonth().compareTo(a.getMonth()); // Tháng mới nhất trước
            }
            return yearCompare;
        });
        
        ObservableList<MonthlyStatItem> observableItems = FXCollections.observableArrayList(items);
        averageTable.setItems(observableItems);
        
        System.out.println("Loaded " + items.size() + " monthly average items");
    }

    // Inner class cho items trong table
    public static class MonthlyStatItem {
        private String month;
        private String year;
        private Double average;

        public String getMonth() { return month; }
        public void setMonth(String month) { this.month = month; }

        public String getYear() { return year; }
        public void setYear(String year) { this.year = year; }

        public Double getAverage() { return average; }
        public void setAverage(Double average) { this.average = average; }
    }
}