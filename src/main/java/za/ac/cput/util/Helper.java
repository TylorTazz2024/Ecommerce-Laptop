package za.ac.cput.util;

import java.util.UUID;

public class Helper {

    public static boolean isNullOrEmpty(String s) {
        if (s.isEmpty() || s == null)
            return true;
        return false;
    }
    public static int generateId() {
        return UUID.randomUUID().hashCode();
    }
}
