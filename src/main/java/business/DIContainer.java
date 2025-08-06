package business;

import persistence.InvoiceDAO;
import persistence.InvoiceDAOGateway;
import persistence.databaseAuthGateway;
import persistence.databaseKey;

/**
 * Enhanced Dependency Injection Container
 * Manages all application services and dependencies
 */
public class DIContainer {
    
    private static DIContainer instance;
    private InvoiceDAOGateway invoiceDAOGateway;
    private InvoiceListControl invoiceListControl;
    private InvoiceService invoiceService;
    
    private DIContainer() {
        // Private constructor để implement Singleton
    }
    
    public static DIContainer getInstance() {
        if (instance == null) {
            instance = new DIContainer();
        }
        return instance;
    }
    
    /**
     * Tạo và trả về InvoiceDAOGateway
     */
    public InvoiceDAOGateway getInvoiceDAOGateway() {
        if (invoiceDAOGateway == null) {
            databaseAuthGateway authGateway = new databaseKey();
            invoiceDAOGateway = new InvoiceDAO(authGateway);
        }
        return invoiceDAOGateway;
    }
    
    /**
     * Tạo và trả về InvoiceListControl với proper dependency injection
     */
    public InvoiceListControl getInvoiceListControl() {
        if (invoiceListControl == null) {
            invoiceListControl = new InvoiceListControl(getInvoiceDAOGateway());
        }
        return invoiceListControl;
    }
    
    /**
     * Tạo và trả về InvoiceService với proper dependency injection
     */
    public InvoiceService getInvoiceService() {
        if (invoiceService == null) {
            invoiceService = new InvoiceService(getInvoiceDAOGateway());
        }
        return invoiceService;
    }
    
    /**
     * Reset container (useful for testing)
     */
    public void reset() {
        invoiceDAOGateway = null;
        invoiceListControl = null;
        invoiceService = null;
    }
}
