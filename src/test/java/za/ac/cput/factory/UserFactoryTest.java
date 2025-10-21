//package za.ac.cput.factory;
//
//import org.junit.jupiter.api.Test;
//import za.ac.cput.domain.Role;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserFactoryTest {
//
//    @Test
//    void createUser_success() {
//        User user = UserFactory.createUser(
//                "Bradly",
//                "Jackson",
//                "password123",
//                "bjackson@gmail.com",
//                "0649484181",
//                Role.CUSTOMER
//        );
//
//        assertNotNull(user);
//        assertEquals("Bradly", user.getFirstName());
//        assertEquals("Jackson", user.getLastName());
//        assertEquals(Role.CUSTOMER, user.getRole());
//    }
//
//    @Test
//    void createUser_failure_nullValues() {
//        Exception exception = assertThrows(IllegalArgumentException.class, () ->
//                UserFactory.createUser(null, "Jackson", "pass", "email", "phone", Role.CUSTOMER)
//        );
//        assertTrue(exception.getMessage().contains("Invalid or missing values"));
//    }
//}
