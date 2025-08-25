package za.ac.cput.domain;

import jakarta.persistence.*;

/**
 * User entity
 * Author: T. Malifethe (222602511)
 * Date: 25 Aug 2025
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    // ✅ Default constructor for JPA
    protected User() {}

    private User(Builder builder) {
        this.userID = builder.userID;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.password = builder.password;
        this.email = builder.email;
        this.phone = builder.phone;
        this.role = builder.role;
    }

    // --- Getters ---
    public Long getUserID() { return userID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Role getRole() { return role; }

    // --- Builder Pattern ---
    public static class Builder {
        private Long userID;
        private String firstName;
        private String lastName;
        private String password;
        private String email;
        private String phone;
        private Role role;

        public Builder setUserID(Long userID) { this.userID = userID; return this; }
        public Builder setFirstName(String firstName) { this.firstName = firstName; return this; }
        public Builder setLastName(String lastName) { this.lastName = lastName; return this; }
        public Builder setPassword(String password) { this.password = password; return this; }
        public Builder setEmail(String email) { this.email = email; return this; }
        public Builder setPhone(String phone) { this.phone = phone; return this; }
        public Builder setRole(Role role) { this.role = role; return this; }

        public Builder copy(User user) {
            this.userID = user.userID;
            this.firstName = user.firstName;
            this.lastName = user.lastName;
            this.password = user.password;
            this.email = user.email;
            this.phone = user.phone;
            this.role = user.role;
            return this;
        }

        public User build() { return new User(this); }
    }
}
