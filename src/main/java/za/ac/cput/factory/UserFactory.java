package za.ac.cput.factory;

import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;

public class UserFactory {

    public static User createUser(String firstName,
                                  String lastName,
                                  String password,
                                  za.ac.cput.domain.Contact contact,
                                  Role role,
                                  java.util.List<za.ac.cput.domain.Order> orders,
                                  java.util.List<za.ac.cput.domain.Review> reviews) {
        if (Helper.isNullOrEmpty(firstName) || Helper.isNullOrEmpty(lastName)
                || Helper.isNullOrEmpty(password) || contact == null || role == null || orders == null || reviews == null) {
            throw new IllegalArgumentException("Invalid or missing values for creating User");
        }
        return new User.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(password)
                .setContact(contact)
                .setRole(role)
                .setOrders(orders)
                .setReviews(reviews)
                .build();
    }
}
