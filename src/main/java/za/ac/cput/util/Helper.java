package za.ac.cput.util;

public class Helper {

    public static boolean isNullOrEmpty(String s) {
        return (s == null || s.isEmpty());
    }

    public static String generateId() {
        return java.util.UUID.randomUUID().toString();
    }
}
