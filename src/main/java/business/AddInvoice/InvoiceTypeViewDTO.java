package business.AddInvoice;

public class InvoiceTypeViewDTO {
    private String typeName;

    public InvoiceTypeViewDTO(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
