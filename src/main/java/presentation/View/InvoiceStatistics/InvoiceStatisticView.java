package presentation.View.InvoiceStatistics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import presentation.Controller.InvoiceStatisticController;
import presentation.Controller.InvoiceStatisticController.StatisticResult;

import java.text.DecimalFormat;

public class InvoiceStatisticView {

    @FXML
    private Button monthlyStatsButton;

    @FXML
    private Label totalAmountLabel;

    @FXML
    private Label totalInvoicesLabel;

    private InvoiceStatisticController statisticController;
    private DecimalFormat currencyFormat = new DecimalFormat("#,###.00");

    public void setStatisticController(InvoiceStatisticController controller) {
        this.statisticController = controller;
        updateBasicStatistics();
    }

    public void updateBasicStatistics() {
        if (statisticController != null) {
            try {
                StatisticResult result = statisticController.getBasicStatistics();
                totalInvoicesLabel.setText("Tổng số hóa đơn: " + result.totalInvoices);
                totalAmountLabel.setText("Tổng doanh thu: " + currencyFormat.format(result.totalRevenue) + " VND");
            } catch (Exception e) {
                totalInvoicesLabel.setText("Tổng số hóa đơn: Lỗi");
                totalAmountLabel.setText("Tổng doanh thu: Lỗi");
                System.err.println("Error updating basic statistics: " + e.getMessage());
            }
        }
    }

    public void showMonthlyStatistics() {
        try {
            // Tạo cửa sổ mới để hiển thị thống kê chi tiết
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/stats.fxml"));
            Scene scene = new Scene(loader.load());
            
            // Lấy controller của stats form
            InvoiceStatisticFormView statsFormController = loader.getController();
            if (statsFormController != null && statisticController != null) {
                statsFormController.setStatisticController(statisticController);
                statsFormController.loadStatistics();
            }
            
            Stage stage = new Stage();
            stage.setTitle("Báo cáo thống kê chi tiết");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); // Modal window
            stage.setResizable(false);
            stage.showAndWait();
            
        } catch (Exception e) {
            showErrorAlert("Lỗi hệ thống!", "Không thể mở cửa sổ thống kê: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void statsInvoice(ActionEvent event) {
        if (statisticController != null) {
            // Call the method to show detailed statistics
            showMonthlyStatistics();
        } else {
            showErrorAlert("Lỗi hệ thống!", "Controller thống kê không khả dụng.");
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}