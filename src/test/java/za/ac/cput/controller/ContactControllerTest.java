
package za.ac.cput.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
      import za.ac.cput.domain.Contact;
import za.ac.cput.factory.ContactFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContactControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private Contact contact;
    private final String BASE_URL = "/contact";

    @BeforeEach
    void setUp() {
        contact = ContactFactory.createContact("john@example.com", "0812345678");
        ResponseEntity<Contact> response = restTemplate.postForEntity(BASE_URL + "/create", contact, Contact.class);
        contact = response.getBody();
    }

    @Test
    void create() {
        assertNotNull(contact);
        assertEquals("john@example.com", contact.getEmail());
    }

    @Test
    void read() {
        ResponseEntity<Contact> response = restTemplate.getForEntity(BASE_URL + "/read/" + contact.getContactID(), Contact.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contact.getEmail(), response.getBody().getEmail());
    }

    @Test
    void update() {
        Contact updated = new Contact.Builder()
                .setContactID(contact.getContactID())
                .setEmail(contact.getEmail())
                .setPhoneNumber("0899999999")
                .build();

        HttpEntity<Contact> request = new HttpEntity<>(updated);
        ResponseEntity<Contact> response = restTemplate.exchange(BASE_URL + "/update", HttpMethod.PUT, request, Contact.class);

        assertEquals("0899999999", response.getBody().getPhoneNumber());
    }

    @Test
    void delete() {
        restTemplate.delete(BASE_URL + "/delete/" + contact.getContactID());

        ResponseEntity<Contact> response = restTemplate.getForEntity(BASE_URL + "/read/" + contact.getContactID(), Contact.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody()); // service returns null if not found
    }

    @Test
    void getAll() {
        ResponseEntity<Contact[]> response = restTemplate.getForEntity(BASE_URL + "/all", Contact[].class);
        List<Contact> contacts = Arrays.asList(response.getBody());
        assertFalse(contacts.isEmpty());
    }
}