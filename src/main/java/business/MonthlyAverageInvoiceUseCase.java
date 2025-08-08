package business;

import persistence.InvoiceDAO;
import persistence.InvoiceDTO;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class MonthlyAverageInvoiceUseCase {
    private final InvoiceDAO invoiceDAO;

    public MonthlyAverageInvoiceUseCase(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public double getMonthlyAverage(int month, int year) {
        List<InvoiceDTO> invoices = invoiceDAO.getAll();
        double total = 0;
        int count = 0;
        for (InvoiceDTO invoice : invoices) {
            java.util.Date date = invoice.getDate();
            if (date != null) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(date);
                int invoiceMonth = cal.get(java.util.Calendar.MONTH) + 1;
                int invoiceYear = cal.get(java.util.Calendar.YEAR);
                if (invoiceMonth == month && invoiceYear == year) {
                    total += invoice.getUnitPrice(); // hoặc invoice.getTotal() nếu có
                    count++;
                }
            }
        }
        return count > 0 ? total / count : 0;
    }

    public List<MonthYear> getAvailableMonthsYears() {
        List<InvoiceDTO> invoices = invoiceDAO.getAll();
        Set<MonthYear> result = new HashSet<>();
        for (InvoiceDTO invoice : invoices) {
            java.util.Date date = invoice.getDate();
            if (date != null) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(date);
                int month = cal.get(java.util.Calendar.MONTH) + 1;
                int year = cal.get(java.util.Calendar.YEAR);
                result.add(new MonthYear(month, year));
            }
        }
        return new ArrayList<>(result);
    }

    public List<MonthlyAverage> getAllMonthlyAverages() {
        List<InvoiceDTO> invoices = invoiceDAO.getAll();
        Map<MonthYear, List<Double>> map = new HashMap<>();
        for (InvoiceDTO invoice : invoices) {
            java.util.Date date = invoice.getDate();
            if (date != null) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(date);
                int month = cal.get(java.util.Calendar.MONTH) + 1;
                int year = cal.get(java.util.Calendar.YEAR);
                MonthYear key = new MonthYear(month, year);
                map.putIfAbsent(key, new ArrayList<>());
                map.get(key).add(invoice.getUnitPrice()); // hoặc getTotal() nếu có
            }
        }
        List<MonthlyAverage> result = new ArrayList<>();
        for (Map.Entry<MonthYear, List<Double>> entry : map.entrySet()) {
            double sum = 0;
            for (double v : entry.getValue()) sum += v;
            double avg = entry.getValue().size() > 0 ? sum / entry.getValue().size() : 0;
            result.add(new MonthlyAverage(entry.getKey().month, entry.getKey().year, avg));
        }
        return result;
    }

    public static class MonthYear {
        public final int month;
        public final int year;
        public MonthYear(int month, int year) {
            this.month = month;
            this.year = year;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MonthYear that = (MonthYear) o;
            return month == that.month && year == that.year;
        }
        @Override
        public int hashCode() {
            return 31 * month + year;
        }
        @Override
        public String toString() {
            return month + "/" + year;
        }
    }

    public static class MonthlyAverage {
        public final int month;
        public final int year;
        public final double average;
        public MonthlyAverage(int month, int year, double average) {
            this.month = month;
            this.year = year;
            this.average = average;
        }
        public int getMonth() { return month; }
        public int getYear() { return year; }
        public double getAverage() { return average; }
        @Override
        public String toString() {
            return "Tháng " + month + "/" + year + ": " + average;
        }
    }
}
