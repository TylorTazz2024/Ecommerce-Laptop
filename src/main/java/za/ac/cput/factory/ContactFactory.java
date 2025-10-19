package za.ac.cput.factory;

import za.ac.cput.domain.Contact;
import za.ac.cput.util.Helper;

public class ContactFactory {

    public static Contact createContact(String email, String phoneNumber) {
        if (Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(phoneNumber)) {
            throw new IllegalArgumentException("Email and phone number cannot be empty.");
        }
        
        // More flexible phone number validation - allow international formats
        if (!isValidPhoneFormat(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number format. Use formats like: 0123456789, +27123456789, or +1234567890");
        }

        int contactID = Helper.generateId();

        return new Contact.Builder()
                .setContactID(contactID)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .build();
    }

    // Flexible phone number validation for various formats
    private static boolean isValidPhoneFormat(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        
        String phone = phoneNumber.trim();
        
        // Allow various formats:
        // - 10 digits: 0123456789
        // - International with +: +27123456789, +1234567890 (8-15 digits after +)
        // - Local format: 012-345-6789, 012 345 6789
        return phone.matches("^(\\+\\d{8,15}|\\d{10}|\\d{3}[\\s-]?\\d{3}[\\s-]?\\d{4})$");
    }
}
