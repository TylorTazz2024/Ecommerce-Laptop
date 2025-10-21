package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Contact;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;
import za.ac.cput.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements IUserService {
    
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User login(String email, String password) {
        return repository.findByContact_EmailAndPassword(email, password);
    }

    @Override
    public User save(User user) {
        // JPA will cascade and save Contact automatically due to CascadeType.ALL
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        User existingUser = repository.findById(user.getUserID()).orElse(null);
        if (existingUser != null) {
            // Update user fields
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setPassword(user.getPassword());
            existingUser.setRole(user.getRole());

            // Update contact fields (do not create new Contact)
            Contact existingContact = existingUser.getContact();
            Contact newContact = user.getContact();
            if (existingContact != null && newContact != null) {
                existingContact.setEmail(newContact.getEmail());
                existingContact.setPhoneNumber(newContact.getPhoneNumber());
            }
            // Save user with updated contact
            return repository.save(existingUser);
        }
        return null;
    }

    @Override
    public User read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw ex; // handled globally to return 409 with friendly message
        }
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByContact_Email(email);
    }

    @Override
    public List<User> findByRole(Role role) {
        return repository.findByRole(role);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }
}
