package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;
import za.ac.cput.factory.UserFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService service;

    private User user;

    @BeforeEach
    void setUp() {
        user = UserFactory.createUser(
                "Bradly",
                "Jackson",
                "password123",
                "bjackson@gmail.com",
                "0649484181",
                Role.CUSTOMER
        );
        service.save(user);
    }

    @Test
    void save() {
        assertNotNull(user.getUserID());
        assertEquals("Bradly", user.getFirstName());
    }

    @Test
    void update() {
        User updated = new User.Builder().copy(user)
                .setFirstName("Brad")
                .build();
        User result = service.update(updated);
        assertEquals("Brad", result.getFirstName());
    }

    @Test
    void read() {
        User found = service.read(user.getUserID());
        assertNotNull(found);
        assertEquals(user.getEmail(), found.getEmail());
    }

    @Test
    void delete() {
        service.delete(user.getUserID());
        User deleted = service.read(user.getUserID());
        assertNull(deleted);
    }

    @Test
    void findByEmail() {
        User found = service.findByEmail("bjackson@gmail.com");
        assertNotNull(found);
        assertEquals(user.getLastName(), found.getLastName());
    }

    @Test
    void findByRole() {
        List<User> customers = service.findByRole(Role.CUSTOMER);
        assertFalse(customers.isEmpty());
    }
}
