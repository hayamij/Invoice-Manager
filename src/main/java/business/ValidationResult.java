package business;

/**
 * Result object for validation operations
 */
public class ValidationResult {
    private final boolean valid;
    private final String errorMessage;
    
    private ValidationResult(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }
    
    /**
     * Create successful validation result
     */
    public static ValidationResult success() {
        return new ValidationResult(true, null);
    }
    
    /**
     * Create failed validation result with error message
     */
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
