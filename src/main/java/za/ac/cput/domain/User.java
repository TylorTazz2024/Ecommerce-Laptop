package za.ac.cput.domain;

/* User.java
   User POJO class
   Author: T. Malifethe (222602511)
   Date: 11 May 2025
*/

public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private Role role;

    private User(Builder builder) {
        this.userID = builder.userID;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.phone = builder.phone;
        this.password = builder.password;
        this.role = builder.role;
    }

    // Getters
    public int getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    // Builder class
    public static class Builder {
        private int userID;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String password;
        private Role role;

        public Builder setUserID(int userID) {
            this.userID = userID;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder copy(User user) {
            this.userID = user.userID;
            this.firstName = user.firstName;
            this.lastName = user.lastName;
            this.email = user.email;
            this.phone = user.phone;
            this.password = user.password;
            this.role = user.role;
            return this;
        }

        public User build() {
            return new User(this);
        }

        @Override
        public String toString() {
            return "User.Builder{" +
                    "userID=" + userID +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    ", password='" + password + '\'' +
                    ", role=" + role +
                    '}';
        }
    }
}
