package business;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service chuyên dụng cho tính toán thống kê với embedded StatisticsData
 * Áp dụng Single Responsibility Principle
 */
public class StatisticsService {
    
    /**
     * Statistics data class for real-time updates
     */
    public static class StatisticsData {
        private final int totalCount;
        private final double totalRevenue;
        private final double averageRevenue;
        
        public StatisticsData(int totalCount, double totalRevenue) {
            this.totalCount = totalCount;
            this.totalRevenue = totalRevenue;
            this.averageRevenue = totalCount > 0 ? totalRevenue / totalCount : 0.0;
        }
        
        public int getTotalCount() { return totalCount; }
        public double getTotalRevenue() { return totalRevenue; }
        public double getAverageRevenue() { return averageRevenue; }
        
        @Override
        public String toString() {
            return "StatisticsData{" +
                    "totalCount=" + totalCount +
                    ", totalRevenue=" + totalRevenue +
                    ", averageRevenue=" + averageRevenue +
                    '}';
        }
    }
    
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
    
    /**
     * Tính doanh thu trung bình theo tháng
     * Yêu cầu đề bài: "tính trung bình thành tiền theo tháng"
     */
    public static Map<String, Double> calculateMonthlyAverageRevenue(List<Invoice> invoices) {
        return invoices.stream()
            .collect(Collectors.groupingBy(
                invoice -> {
                    LocalDate date = invoice.getDate().toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate();
                    return date.getYear() + "-" + String.format("%02d", date.getMonthValue());
                },
                Collectors.averagingDouble(Invoice::calculateTotal)
            ));
    }
    
    /**
     * Đếm số lượng từng loại thuê phòng
     * Yêu cầu đề bài: "tính tổng số lượng cho từng loại thuê phòng"
     */
    public static Map<String, Long> countByRoomType(List<Invoice> invoices) {
        return invoices.stream()
            .collect(Collectors.groupingBy(
                Invoice::type,
                Collectors.counting()
            ));
    }
}
