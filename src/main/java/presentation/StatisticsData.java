package presentation;

/**
 * Statistics data class for real-time updates
 */
public class StatisticsData {
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
