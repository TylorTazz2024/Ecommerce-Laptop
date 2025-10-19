//
//package za.ac.cput.factory;
//
//import org.junit.jupiter.api.Test;
//import za.ac.cput.domain.Contact;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ContactFactoryTest {
//
//    @Test
//    void createContact_success() {
//        Contact contact = ContactFactory.createContact("john@example.com", "0812345678");
//
//        assertNotNull(contact);
//        assertTrue(contact.getContactID() > 0);
//        assertEquals("john@example.com", contact.getEmail());
//        assertEquals("0812345678", contact.getPhoneNumber());
//    }
//
//    @Test
//    void createContact_failure_emptyEmail() {
//        Exception exception = assertThrows(IllegalArgumentException.class, () ->
//                ContactFactory.createContact("", "0812345678")
//        );
//        assertEquals("Email and phone number cannot be empty.", exception.getMessage());
//    }
//
//    @Test
//    void createContact_failure_emptyPhone() {
//        Exception exception = assertThrows(IllegalArgumentException.class, () ->
//                ContactFactory.createContact("john@example.com", "")
//        );
//        assertEquals("Email and phone number cannot be empty.", exception.getMessage());
//    }
//}
