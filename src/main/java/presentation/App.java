package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// ===== business use cases =====
import business.AddInvoice.AddInvoiceUseCase;
import business.AddInvoice.InvoiceTypeListUseCase;
import business.ShowInvoiceList.ShowInvoiceListUseCase;

// ===== persistence layer (composition root được phép đụng) =====
import persistence.AddInvoiceDAO;
import persistence.InvoiceDAO;
import persistence.InvoiceDAOGateway;
import presentation.view.UIController;
import persistence.AddInvoiceDAOGateway;

public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // 1) Load UI.fxml (đã đổi tên controller sang UIController)
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/presentation/UI.fxml"));
        Parent root = loader.load();
        scene = new Scene(root, 1280, 720);

        stage.setScene(scene);
        stage.setTitle("Invoice Manager");
        stage.show();

        InvoiceDAOGateway invoiceDAOGateway = new InvoiceDAO();
        AddInvoiceDAOGateway addInvoiceDAOGateway = new AddInvoiceDAO();
        ShowInvoiceListUseCase showInvoiceListUseCase = new ShowInvoiceListUseCase(invoiceDAOGateway);
        AddInvoiceUseCase addInvoiceUseCase = new AddInvoiceUseCase(addInvoiceDAOGateway);
        InvoiceTypeListUseCase invoiceTypeListUseCase = new InvoiceTypeListUseCase();

        // 3) Inject dependencies vào UIController và bootstrap
        UIController uiController = loader.getController();
        uiController.setDependencies(showInvoiceListUseCase, addInvoiceUseCase, invoiceTypeListUseCase);
        uiController.bootstrap();
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