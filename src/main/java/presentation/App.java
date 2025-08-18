package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.InvoiceList.InvoiceDAO;

import java.io.IOException;

import business.Controls.ShowInvoiceList.ShowInvoiceListUseCase;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/presentation/InvoiceListUI.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Invoice Manager");

        // Lấy controller thực tế từ FXMLLoader
        InvoiceController invoiceController = fxmlLoader.getController();
        InvoiceDAO DAO = new InvoiceDAO();
        InvoiceViewModel model = new InvoiceViewModel();
        ShowInvoiceListUseCase showInvoiceListUseCase = new ShowInvoiceListUseCase(DAO);

        // Truyền model và usecase vào controller thực tế
        invoiceController.setViewModel(model);
        invoiceController.setShowInvoiceListUseCase(showInvoiceListUseCase);
        invoiceController.execute();
        invoiceController.displayInvoices();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/presentation/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}