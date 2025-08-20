package za.ac.cput.factory;

import za.ac.cput.domain.Contact;
import za.ac.cput.util.Helper;

public class ContactFactory {

    public static Contact createContact(String email, String phoneNumber) {
        if (Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(phoneNumber)) {
            throw new IllegalArgumentException("Email and phone number cannot be empty.");
        }
        if (phoneNumber.length() != 10) {
            return null;
        }

        int contactID = Helper.generateId();

        return new Contact.Builder()
                .setContactID(contactID)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .build();
    }
}
