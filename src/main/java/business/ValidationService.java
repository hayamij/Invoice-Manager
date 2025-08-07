package business;

/**
 * Validation Service for business rules with embedded ValidationResult
 * Implements Single Responsibility Principle for validation logic
 */
public class ValidationService {
    
    /**
     * Result object for validation operations
     */
    public static class ValidationResult {
        private final boolean valid;
        private final String errorMessage;
        
        private ValidationResult(boolean valid, String errorMessage) {
            this.valid = valid;
            this.errorMessage = errorMessage;
        }
        
        public static ValidationResult success() {
            return new ValidationResult(true, null);
        }
        
        public static ValidationResult fail(String errorMessage) {
            return new ValidationResult(false, errorMessage);
        }
        
        public boolean isValid() {
            return valid;
        }
        
        public String getErrorMessage() {
            return errorMessage;
        }
        
        @Override
        public String toString() {
            return "ValidationResult{" +
                    "valid=" + valid +
                    ", errorMessage='" + errorMessage + '\'' +
                    '}';
        }
    }
    
    /**
     * Validate invoice request
     */
    public static ValidationResult validateInvoiceRequest(InvoiceRequest request) {
        if (request == null) {
            return ValidationResult.fail("Invoice request cannot be null");
        }
        
        // Validate customer
        if (request.getCustomer() == null || request.getCustomer().trim().isEmpty()) {
            return ValidationResult.fail("Customer name is required");
        }
        
        if (request.getCustomer().length() > 255) {
            return ValidationResult.fail("Customer name too long (max 255 characters)");
        }
        
        // Validate room ID
        if (request.getRoomId() == null || request.getRoomId().trim().isEmpty()) {
            return ValidationResult.fail("Room ID is required");
        }
        
        // Validate unit price
        if (request.getUnitPrice() <= 0) {
            return ValidationResult.fail("Unit price must be positive");
        }
        
        if (request.getUnitPrice() > 1000000) {
            return ValidationResult.fail("Unit price too high (max 1,000,000)");
        }
        
        // Validate type
        if (request.getType() == null || 
            (!request.getType().equals("hourly") && !request.getType().equals("daily"))) {
            return ValidationResult.fail("Type must be 'hourly' or 'daily'");
        }
        
        // Validate quantity
        if (request.getQuantity() <= 0) {
            return ValidationResult.fail("Quantity must be positive");
        }
        
        // Type-specific validation
        if ("hourly".equals(request.getType())) {
            if (request.getQuantity() > 30) {
                return ValidationResult.fail("Hours cannot exceed 30");
            }
        } else if ("daily".equals(request.getType())) {
            if (request.getQuantity() > 365) {
                return ValidationResult.fail("Days cannot exceed 365");
            }
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Validate customer name
     */
    public static boolean isValidCustomerName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() <= 255;
    }
    
    /**
     * Validate room ID
     */
    public static boolean isValidRoomId(String roomId) {
        return roomId != null && !roomId.trim().isEmpty() && roomId.length() <= 50;
    }
    
    /**
     * Validate pricing
     */
    public static boolean isValidPricing(double unitPrice, int quantity) {
        return unitPrice > 0 && quantity > 0 && unitPrice <= 1000000;
    }
    
    /**
     * Validate invoice type
     */
    public static boolean isValidType(String type) {
        return "hourly".equals(type) || "daily".equals(type);
    }
}
