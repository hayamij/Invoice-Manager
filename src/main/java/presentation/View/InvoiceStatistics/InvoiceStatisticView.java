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
import presentation.Model.InvoiceStatisticModel;
import presentation.Subscriber;

import java.text.DecimalFormat;

public class InvoiceStatisticView implements Subscriber {

    @FXML
    private Button monthlyStatsButton;

    @FXML
    private Label totalAmountLabel;

    @FXML
    private Label totalInvoicesLabel;

    private InvoiceStatisticModel statisticModel;
    private DecimalFormat currencyFormat = new DecimalFormat("#,###.00");

    public void setStatisticModel(InvoiceStatisticModel model) {
        this.statisticModel = model;
        if (statisticModel != null) {
            statisticModel.subscribe(this);
            updateBasicStatistics();
        }
    }

    public void updateBasicStatistics() {
        if (statisticModel != null) {
            try {
                totalInvoicesLabel.setText("Tổng số hóa đơn: " + statisticModel.getTotalInvoices());
                totalAmountLabel.setText("Tổng doanh thu: " + currencyFormat.format(statisticModel.getTotalRevenue()) + " VND");
            } catch (Exception e) {
                totalInvoicesLabel.setText("Tổng số hóa đơn: Lỗi");
                totalAmountLabel.setText("Tổng doanh thu: Lỗi");
                System.err.println("Error updating basic statistics: " + e.getMessage());
            }
        }
    }

    public void showMonthlyStatistics() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/stats.fxml"));
            Scene scene = new Scene(loader.load());
            InvoiceStatisticFormView statsFormController = loader.getController();
            if (statsFormController != null && statisticModel != null) {
                statsFormController.setStatisticModel(statisticModel);
                statsFormController.loadStatistics();
            }
            Stage stage = new Stage();
            stage.setTitle("Báo cáo thống kê chi tiết");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            showErrorAlert("Lỗi hệ thống!", "Không thể mở cửa sổ thống kê: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void statsInvoice(ActionEvent event) {
        if (statisticModel != null) {
            showMonthlyStatistics();
        } else {
            showErrorAlert("Lỗi hệ thống!", "Model thống kê không khả dụng.");
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void update() {
        updateBasicStatistics();
    }
}