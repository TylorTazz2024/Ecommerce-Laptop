package za.ac.cput.service;

import za.ac.cput.domain.User;
import za.ac.cput.domain.Role;
import java.util.List;

public interface IUserService extends IService<User, Integer> {
    User findByEmail(String email);
    List<User> findByRole(Role role);
}
