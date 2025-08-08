package business;

import persistence.InvoiceDAO;
import persistence.InvoiceDTO;

public class UpdateInvoiceUseCase {
    private final InvoiceDAO invoiceDAO;

    public UpdateInvoiceUseCase(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public boolean updateInvoice(String id, String customer, String room, String type, String unitPriceStr, String hourStr, String dayStr) {
        try {
            if (id == null || customer == null || room == null || type == null || unitPriceStr == null) {
                return false;
            }
            double unitPrice = Double.parseDouble(unitPriceStr);
            int hour = Integer.parseInt(hourStr);
            int day = Integer.parseInt(dayStr);
            double total = 0;
            if ("hourly".equals(type)) {
                HourlyInvoice hi = new HourlyInvoice();
                total = hi.calculateTotal(unitPrice, hour);
            } else if ("daily".equals(type)) {
                DailyInvoice di = new DailyInvoice();
                total = di.calculateTotal(unitPrice, day);
            }
            if (total == -1) {
                throw new IllegalArgumentException("Không được dùng loại hóa đơn này");
            }
            InvoiceDTO invoiceDTO = new InvoiceDTO();
            invoiceDTO.setId(id);
            invoiceDTO.setCustomer(customer);
            invoiceDTO.setRoom_id(room);
            invoiceDTO.setType(type);
            invoiceDTO.setUnitPrice(unitPrice);
            invoiceDTO.setHour(hour);
            invoiceDTO.setDay(day);
            invoiceDTO.setDate(new java.sql.Date(System.currentTimeMillis()));
            return invoiceDAO.updateInvoice(invoiceDTO);
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
