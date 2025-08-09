package business;

import persistence.InvoiceDAOGateway;
import persistence.InvoiceDTO;

public class AddInvoiceUseCase {
    private final InvoiceDAOGateway invoiceDAO;

    public AddInvoiceUseCase(InvoiceDAOGateway invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public boolean addInvoice(String customer, String room, String type, String unitPriceStr, String hourStr, String dayStr) {
        try {
            if (customer == null || customer.trim().isEmpty() ||
                room == null || room.trim().isEmpty() ||
                type == null || type.trim().isEmpty() ||
                unitPriceStr == null || unitPriceStr.trim().isEmpty()) {
                return false;
            }
            double unitPrice = Double.parseDouble(unitPriceStr);
            int hour = Integer.parseInt(hourStr);
            int day = Integer.parseInt(dayStr);
            double total = 0;
            if ("hourly".equals(type)) {
                HourlyInvoice hi = new HourlyInvoice();
                total = hi.calculateTotal(unitPrice, hour);
                if (total == -1) {
                    throw new IllegalArgumentException("Số giờ không được vượt quá 30");
                }
            } else if ("daily".equals(type)) {
                DailyInvoice di = new DailyInvoice();
                total = di.calculateTotal(unitPrice, day);
                if (total == -1) {
                    throw new IllegalArgumentException("Số ngày phải lớn hơn 0");
                }
            }
            InvoiceDTO invoiceDTO = new InvoiceDTO();
            invoiceDTO.setCustomer(customer);
            invoiceDTO.setRoom_id(room);
            invoiceDTO.setType(type);
            invoiceDTO.setUnitPrice(unitPrice);
            invoiceDTO.setHour(hour);
            invoiceDTO.setDay(day);
            invoiceDTO.setDate(new java.sql.Date(System.currentTimeMillis()));
            // Có thể set thêm total nếu cần
            return invoiceDAO.insertInvoice(invoiceDTO);
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
