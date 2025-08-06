package business;

import java.util.List;

/**
 * Service chuyên dụng cho tính toán thống kê
 * Áp dụng Single Responsibility Principle
 */
public class StatisticsService {
    
    /**
     * Tính tổng doanh thu từ danh sách invoices
     */
    public static double calculateTotalRevenue(List<Invoice> invoices) {
        return invoices.stream()
            .mapToDouble(Invoice::calculateTotal)
            .sum();
    }
    
    /**
     * Đếm số lượng invoice
     */
    public static int countInvoices(List<Invoice> invoices) {
        return invoices.size();
    }
    
    /**
     * Tính doanh thu trung bình
     */
    public static double calculateAverageRevenue(List<Invoice> invoices) {
        if (invoices.isEmpty()) {
            return 0.0;
        }
        return calculateTotalRevenue(invoices) / invoices.size();
    }
    
    /**
     * Đếm số invoice theo loại
     */
    public static long countInvoicesByType(List<Invoice> invoices, String type) {
        return invoices.stream()
            .filter(invoice -> invoice.type().toLowerCase().contains(type.toLowerCase()))
            .count();
    }
}
