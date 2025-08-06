package business;

import persistence.InvoiceDAO;
import persistence.InvoiceDAOGateway;
import persistence.databaseAuthGateway;
import persistence.databaseKey;

/**
 * Dependency Injection Container
 * Giải quyết vi phạm DIP và tách rời dependencies
 */
public class DIContainer {
    
    private static DIContainer instance;
    private InvoiceDAOGateway invoiceDAOGateway;
    private InvoiceListControl invoiceListControl;
    
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
     * Reset container (useful for testing)
     */
    public void reset() {
        invoiceDAOGateway = null;
        invoiceListControl = null;
    }
}
