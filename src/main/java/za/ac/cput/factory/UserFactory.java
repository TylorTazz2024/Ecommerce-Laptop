package za.ac.cput.factory;

import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Role;
import za.ac.cput.util.Helper;

public class UserFactory {

    public static User createUser(int userID,
                                  String firstName,
                                  String lastName,
                                  String email,
                                  String phone,
                                  String password,
                                  Role role) {

        if (Helper.isNullOrEmpty(firstName) || Helper.isNullOrEmpty(lastName)
                || Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(phone)
                || Helper.isNullOrEmpty(password) || role == null) {
            return null;
        }

        return new User.Builder()
                .setUserID(userID)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPhone(phone)
                .setPassword(password)
                .setRole(role)
                .build();
    }
}
