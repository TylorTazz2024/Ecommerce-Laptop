//package za.ac.cput.controller;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.*;
//import za.ac.cput.domain.Role;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class UserControllerTest {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    private User user;
//    private final String BASE_URL = "/api/users";
//
//    @BeforeEach
//    void setUp() {
//        user = UserFactory.createUser(
//                "Bradly",
//                "Jackson",
//                "password123",
//                "bjackson@gmail.com",
//                "0649484181",
//                Role.CUSTOMER
//        );
//        ResponseEntity<User> response = restTemplate.postForEntity(BASE_URL, user, User.class);
//        user = response.getBody();
//    }
//
//    @Test
//    void create() {
//        assertNotNull(user);
//        assertEquals("Bradly", user.getFirstName());
//    }
//
//    @Test
//    void read() {
//        ResponseEntity<User> response = restTemplate.getForEntity(BASE_URL + "/" + user.getUserID(), User.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(user.getEmail(), response.getBody().getEmail());
//    }
//
//    @Test
//    void update() {
//        User updated = new User.Builder().copy(user).setLastName("Johnson").build();
//        HttpEntity<User> request = new HttpEntity<>(updated);
//        ResponseEntity<User> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, request, User.class);
//        assertEquals("Johnson", response.getBody().getLastName());
//    }
//
//    @Test
//    void delete() {
//        restTemplate.delete(BASE_URL + "/" + user.getUserID());
//        ResponseEntity<User> response = restTemplate.getForEntity(BASE_URL + "/" + user.getUserID(), User.class);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//
//    @Test
//    void findByEmail() {
//        ResponseEntity<User> response = restTemplate.getForEntity(BASE_URL + "/email/" + user.getEmail(), User.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(user.getFirstName(), response.getBody().getFirstName());
//    }
//
//    @Test
//    void findByRole() {
//        ResponseEntity<User[]> response = restTemplate.getForEntity(BASE_URL + "/role/CUSTOMER", User[].class);
//        List<User> users = Arrays.asList(response.getBody());
//        assertFalse(users.isEmpty());
//    }
//}
