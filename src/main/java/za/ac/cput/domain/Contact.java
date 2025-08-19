package za.ac.cput.domain;

public class Contact {

        private int contactID;
        private String email;
        private String phoneNumber;

        public Contact(Builder builder) {
            this.contactID = builder.contactID;
            this.email = builder.email;
            this.phoneNumber = builder.phoneNumber;
        }

        public Contact(int contactID, String email, String phoneNumber) {
            this.contactID = contactID;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

        public int getContactID() {
            return contactID;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoneNumber() {

            return phoneNumber;
        }

        @Override public String toString() {
            return "Contact{" + "contactID=" + contactID +
                    ", email='" + email + '\''
                    + ", phoneNumber='" + phoneNumber
                    + '\'' + '}';
        }
        public static class Builder{
            private int contactID;
            private String email;
            private String phoneNumber;

            public Builder setContactID(int contactID) {
                this.contactID = contactID;
                return this;
            }

            public Builder setEmail(String email) {
                this.email = email;
                return this;
            }

            public Builder setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
                return this;
            }

            public za.ac.cput.domain.Contact build() {
                return new za.ac.cput.domain.Contact(this); }
        }
    }


