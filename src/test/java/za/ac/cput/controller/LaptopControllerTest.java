package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Laptop;
import za.ac.cput.factory.LaptopFactory;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LaptopControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/laptops";
    private static Laptop laptop1;
    private static Laptop laptop2;

    @BeforeAll
    static void setUp() {
        laptop1 = LaptopFactory.createLaptop(
                101,
                "Dell",
                "Inspiron 15",
                "Intel i5, 8GB RAM, 256GB SSD",
                7500.00,
                "Used"
        );
        assertNotNull(laptop1);
        System.out.println("Laptop 1: " + laptop1);

        laptop2 = LaptopFactory.createLaptop(
                1,
                "HP",
                "Laptop 14",
                "Intel i7, 16GB RAM, 512GB SSD",
                9500.00,
                "Used"
        );
        assertNotNull(laptop2);
        System.out.println("Laptop 2: " + laptop2);
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Laptop> response1 = restTemplate.postForEntity(url, laptop1, Laptop.class);
        assertNotNull(response1);
        assertNotNull(response1.getBody());
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        laptop1 = response1.getBody(); // Update with DB-generated ID if applicable
        System.out.println("Created Laptop 1: " + laptop1);

        ResponseEntity<Laptop> response2 = restTemplate.postForEntity(url, laptop2, Laptop.class);
        assertNotNull(response2);
        assertNotNull(response2.getBody());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        laptop2 = response2.getBody();
        System.out.println("Created Laptop 2: " + laptop2);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = BASE_URL + "/read/" + laptop1.getLaptopID();
        ResponseEntity<Laptop> response = restTemplate.getForEntity(url, Laptop.class);
        assertNotNull(response.getBody());
        assertEquals(laptop1.getLaptopID(), response.getBody().getLaptopID());
        System.out.println("Read Laptop 1: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        String url = BASE_URL + "/update";
        Laptop updatedLaptop = new Laptop.Builder().copy(laptop2)
                .setPrice(9800.00)
                .build();

        ResponseEntity<Laptop> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(updatedLaptop),
                Laptop.class
        );

        assertNotNull(response.getBody());
        Laptop resultLaptop = response.getBody();
        assertEquals(updatedLaptop.getLaptopID(), resultLaptop.getLaptopID());
        assertEquals(9800.00, resultLaptop.getPrice());
        System.out.println("Updated Laptop 2: " + resultLaptop);
    }

    @Test
    @Order(4)
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, null, List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("All laptops: " + response.getBody());
    }

    @Test
    @Order(5)
    void e_delete() {
        String url = BASE_URL + "/delete/" + laptop1.getLaptopID();
        restTemplate.delete(url);
        System.out.println("Deleted Laptop 1 with ID: " + laptop1.getLaptopID());
    }
}


