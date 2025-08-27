package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.ac.cput.domain.Payment;
import za.ac.cput.factory.PaymentFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/payment";

    private static Payment payment1;
    private static Payment payment2;

    @BeforeAll
    static void setUp() {
        payment1 = PaymentFactory.createPayment(15, 7500.00, "Mastercard", "Successful");
        assertNotNull(payment1);
        System.out.println("Setup Payment 1: " + payment1);

        payment2 = PaymentFactory.createPayment(2, 5000.00, "PayPal", "Pending");
        assertNotNull(payment2);
        System.out.println("Setup Payment 2: " + payment2);
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Payment> response1 = restTemplate.postForEntity(url, payment1, Payment.class);
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody());
        payment1 = response1.getBody(); // update ID if generated
        System.out.println("Created Payment 1: " + payment1);

        ResponseEntity<Payment> response2 = restTemplate.postForEntity(url, payment2, Payment.class);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        payment2 = response2.getBody();
        System.out.println("Created Payment 2: " + payment2);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = BASE_URL + "/read/" + payment1.getPaymentID();
        ResponseEntity<Payment> response = restTemplate.getForEntity(url, Payment.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(payment1.getPaymentID(), response.getBody().getPaymentID());
        System.out.println("Read Payment: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        String url = BASE_URL + "/update";
        Payment updatedPayment = new Payment.Builder()
                .copy(payment2)
                .setStatus("Successful")
                .build();
        ResponseEntity<Payment> response = restTemplate.exchange(url, HttpMethod.PUT,
                new HttpEntity<>(updatedPayment), Payment.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Successful", response.getBody().getStatus());
        System.out.println("Updated Payment: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, null, List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("All Payments: " + response.getBody());
    }

    @Test
    @Order(5)
    void e_delete() {
        String url = BASE_URL + "/delete/" + payment1.getPaymentID();
        restTemplate.delete(url);
        System.out.println("Deleted Payment ID: " + payment1.getPaymentID());
    }
}
