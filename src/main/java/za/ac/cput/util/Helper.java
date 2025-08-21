package za.ac.cput.util;

public class Helper {


    public static boolean isNullOrEmpty(String s) {
        return (s == null || s.trim().isEmpty());
    }


    public static boolean isValidRating(String rating) {
        if (isNullOrEmpty(rating)) {
            return false;
        }
        try {
            int value = Integer.parseInt(rating);
            return value >= 1 && value <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }

   
    public static String sanitizeComment(String comment) {
        if (isNullOrEmpty(comment)) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }
        return comment.trim();
    }
}
