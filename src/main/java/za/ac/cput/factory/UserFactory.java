package za.ac.cput.factory;

import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;

public class UserFactory {

    public static User createUser(String firstName,
                                  String lastName,
                                  String password,
                                  String email,
                                  String phone,
                                  Role role) {

        if (Helper.isNullOrEmpty(firstName) || Helper.isNullOrEmpty(lastName)
                || Helper.isNullOrEmpty(password) || Helper.isNullOrEmpty(email)
                || Helper.isNullOrEmpty(phone)) {
            throw new IllegalArgumentException("Invalid or missing values for creating User");
        }

        return new User.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(password)
                .setEmail(email)
                .setPhone(phone)
                .setRole(role)
                .build();
    }
}
