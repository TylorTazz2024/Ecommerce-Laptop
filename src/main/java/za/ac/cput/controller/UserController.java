package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Role;
import za.ac.cput.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }



    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.name == @userService.read(#id)?.contact?.email")
    public ResponseEntity<User> read(@PathVariable Long id) {
        User user = service.read(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #user.contact.email")
    public ResponseEntity<User> update(@RequestBody User user) {
        return ResponseEntity.ok(service.update(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.name == @userService.read(#id)?.contact?.email")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #email")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.findByEmail(email));
    }

    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> findByRole(@PathVariable Role role) {
        return ResponseEntity.ok(service.findByRole(role));
    }

    // --- Self-service profile endpoints ---
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getMe(Authentication authentication) {
        var user = service.findByEmail(authentication.getName());
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> updateMe(Authentication authentication, @RequestBody User user) {
        var existing = service.findByEmail(authentication.getName());
        if (existing == null) return ResponseEntity.notFound().build();
        // Force the update to apply to the authenticated user ID using builder
        User toUpdate = new User.Builder()
                .setUserID(existing.getUserID())
                .setFirstName(user.getFirstName() != null ? user.getFirstName() : existing.getFirstName())
                .setLastName(user.getLastName() != null ? user.getLastName() : existing.getLastName())
                .setPassword(user.getPassword() != null ? user.getPassword() : existing.getPassword())
                .setRole(user.getRole() != null ? user.getRole() : existing.getRole())
                .setContact(existing.getContact()) // preserve existing contact
                .build();
        // Keep email immutable to principal; allow phone update if provided
        if (user.getContact() != null && user.getContact().getPhoneNumber() != null && toUpdate.getContact() != null) {
            toUpdate.getContact().setPhoneNumber(user.getContact().getPhoneNumber());
        }
        return ResponseEntity.ok(service.update(toUpdate));
    }

    @DeleteMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteMe(Authentication authentication) {
        var user = service.findByEmail(authentication.getName());
        if (user == null) return ResponseEntity.notFound().build();
        service.delete(user.getUserID());
        return ResponseEntity.noContent().build();
    }
}
