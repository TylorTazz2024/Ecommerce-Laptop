package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    @Test
    void createUser_success() {
        User user = UserFactory.createUser(
                "Bradly",
                "Jackson",
                "password123",
                Role.CUSTOMER,
                "bjackson@gmail.com",
                "0649484181"
        );

        assertNotNull(user);
        assertEquals("Bradly", user.getFirstName());
        assertEquals("Jackson", user.getLastName());
        assertEquals("bjackson@gmail.com", user.getEmail());
        assertEquals(Role.CUSTOMER, user.getRole());
    }

    @Test
    void createUser_fail_nullFirstName() {
        User user = UserFactory.createUser(
                null,
                "Jackson",
                "password123",
                Role.CUSTOMER,
                "bjackson@gmail.com",
                "0649484181"
        );

        assertNull(user, "User should be null if first name is missing");
    }

    @Test
    void createUser_fail_emptyPassword() {
        User user = UserFactory.createUser(
                "Bradly",
                "Jackson",
                "",
                Role.CUSTOMER,
                "bjackson@gmail.com",
                "0649484181"
        );

        assertNull(user, "User should be null if password is empty");
    }
}
