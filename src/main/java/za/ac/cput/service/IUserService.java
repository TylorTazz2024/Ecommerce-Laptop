package za.ac.cput.service;

import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;

import java.util.List;

public interface IUserService extends IService<User, Long> {
    User login(String email, String password);
    User findByEmail(String email);
    List<User> findByRole(Role role);
    List<User> getAll();
}
