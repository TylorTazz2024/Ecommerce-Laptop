package za.ac.cput.util;

import java.util.UUID;

public class Helper {

    // Check if a string is null or empty
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    // Generate a unique ID as an int (hash of UUID)
    public static int generateId() {
        return Math.abs(UUID.randomUUID().hashCode());
    }

    // Validate that rating is between 1 and 5
    public static boolean isValidRating(String rating) {
        if (isNullOrEmpty(rating)) return false;
        try {
            int value = Integer.parseInt(rating);
            return value >= 1 && value <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Sanitize comments to avoid empty/whitespace input
    public static String sanitizeComment(String comment) {
        if (isNullOrEmpty(comment)) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }
        return comment.trim();
    }

    // Basic phone number check (10 digits, SA style)
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    // Basic email format validation
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
    }
}
