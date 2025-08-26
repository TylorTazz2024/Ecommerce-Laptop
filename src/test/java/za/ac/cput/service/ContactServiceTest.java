package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Contact;
import za.ac.cput.factory.ContactFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContactServiceTest {

    @Autowired
    private ContactService service;

    private Contact contact;

    @BeforeEach
    void setUp() {
        contact = ContactFactory.createContact("john@example.com", "0812345678");
        service.create(contact);
    }

    @Test
    void create() {
        assertNotNull(contact.getContactID());
        assertEquals("john@example.com", contact.getEmail());
    }

    @Test
    void update() {
        Contact updated = new Contact.Builder()
                .setContactID(contact.getContactID())
                .setEmail(contact.getEmail())
                .setPhoneNumber("0899999999")
                .build();

        Contact result = service.update(updated);
        assertEquals("0899999999", result.getPhoneNumber());
    }

    @Test
    void read() {
        Contact found = service.read(contact.getContactID());
        assertNotNull(found);
        assertEquals(contact.getEmail(), found.getEmail());
    }

    @Test
    void delete() {
        boolean deleted = service.delete(contact.getContactID());
        assertTrue(deleted);

        Contact afterDelete = service.read(contact.getContactID());
        assertNull(afterDelete);
    }

    @Test
    void getAllContacts() {
        List<Contact> list = service.getAllContacts();
        assertFalse(list.isEmpty());
    }
}
