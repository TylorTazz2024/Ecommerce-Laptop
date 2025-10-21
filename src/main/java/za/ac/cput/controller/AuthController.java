package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Contact;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;
import za.ac.cput.factory.ContactFactory;
import za.ac.cput.security.JwtTokenUtil;
import za.ac.cput.security.JwtUserDetailsService;
import za.ac.cput.service.IUserService;
import za.ac.cput.service.IContactService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final IContactService contactService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil,
                          JwtUserDetailsService userDetailsService,
                          IUserService userService,
                          PasswordEncoder passwordEncoder,
                          IContactService contactService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.contactService = contactService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Get user details for response
        User user = userService.findByEmail(authenticationRequest.getEmail());

        return ResponseEntity.ok(new JwtResponse(token, user.getUserID(), user.getFirstName(),
                user.getLastName(), user.getContact().getEmail(), user.getRole()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        try {
            // Check if user already exists
            if (userService.findByEmail(request.getEmail()) != null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
            }

            // Create and save contact first
            Contact contact = ContactFactory.createContact(
                    request.getEmail(),
                    request.getPhoneNumber()
            );
            Contact savedContact = contactService.create(contact);

            // Create user with saved contact
            User user = new User.Builder()
                    .setFirstName(request.getFirstName())
                    .setLastName(request.getLastName())
                    .setPassword(passwordEncoder.encode(request.getPassword()))
                    .setContact(savedContact)
                    .setRole(request.getRole() != null ? request.getRole() : Role.CUSTOMER)
                    .build();

            userService.save(user);

            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Registration failed: " + e.getMessage()));
        }
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    // DTO classes
    public static class JwtRequest {
        private String email;
        private String password;

        public JwtRequest() {}

        public JwtRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class JwtResponse {
        private final String token;
        private final Long userId;
        private final String firstName;
        private final String lastName;
        private final String email;
        private final Role role;

        public JwtResponse(String token, Long userId, String firstName, String lastName, String email, Role role) {
            this.token = token;
            this.userId = userId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.role = role;
        }

        public String getToken() { return token; }
        public Long getUserId() { return userId; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getEmail() { return email; }
        public Role getRole() { return role; }
    }

    public static class UserRegistrationRequest {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String password;
        private Role role;

        // Getters and setters
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public Role getRole() { return role; }
        public void setRole(Role role) { this.role = role; }
    }

    public static class MessageResponse {
        private String message;

        public MessageResponse(String message) {
            this.message = message;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}