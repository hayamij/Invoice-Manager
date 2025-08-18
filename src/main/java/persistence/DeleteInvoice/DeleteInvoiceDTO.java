package persistence.DeleteInvoice;

public class DeleteInvoiceDTO {
    public String id;

    // Constructor
    public DeleteInvoiceDTO() {}

    public DeleteInvoiceDTO(String id) {
        this.id = id;
    }
    // Getters
    public String getId() { return id; }
}
