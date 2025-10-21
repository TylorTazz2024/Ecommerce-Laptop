package za.ac.cput.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import za.ac.cput.domain.Contact;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;
import za.ac.cput.service.IUserService;

@Component

public class DataInitializationService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializationService.class);

    // Default Admin Credentials
    private static final String ADMIN_EMAIL = "sinesipho@ecommerce.com";
    private static final String ADMIN_PASSWORD = "Sinesipho@123";
    private static final String ADMIN_FIRST_NAME = "Sinesipho";
    private static final String ADMIN_LAST_NAME = "Muhlauli";
    private static final String ADMIN_PHONE = "+27735572245";

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializationService(IUserService userService, 
                                   PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(" Starting Data Initialization...");
        createDefaultAdminUser();
        logger.info(" Data Initialization Completed!");
    }

    private void createDefaultAdminUser() {
        try {
            // Check if admin already exists
            User existingAdmin = userService.findByEmail(ADMIN_EMAIL);
            
            if (existingAdmin != null) {
                logger.info("  Default admin user already exists. Skipping admin creation.");
                logger.info(" Admin Email: {}", ADMIN_EMAIL);
                return;
            }

            logger.info(" Creating default admin user...");

            // Create admin contact without ID (let JPA auto-generate it)
            Contact adminContact = new Contact.Builder()
                    .setEmail(ADMIN_EMAIL)
                    .setPhoneNumber(ADMIN_PHONE)
                    .build();

            // Create admin user (JPA will cascade and save the contact automatically)
            User adminUser = new User.Builder()
                    .setFirstName(ADMIN_FIRST_NAME)
                    .setLastName(ADMIN_LAST_NAME)
                    .setPassword(passwordEncoder.encode(ADMIN_PASSWORD))
                    .setContact(adminContact)
                    .setRole(Role.ADMIN)
                    .build();

            User savedAdmin = userService.save(adminUser);

            logger.info(" Default admin user created successfully!");
            logger.info(" Email: {}", ADMIN_EMAIL);
            logger.info(" Password: {}", ADMIN_PASSWORD);
            logger.info(" Name: {} {}", ADMIN_FIRST_NAME, ADMIN_LAST_NAME);
            logger.info(" Phone: {}", ADMIN_PHONE);
            logger.info(" User ID: {}", savedAdmin.getUserID());
            logger.info(" Role: {}", savedAdmin.getRole());

            logger.warn("SECURITY NOTICE: Please change the default admin password after first login!");

        } catch (Exception e) {
            logger.error("Failed to create default admin user: {}", e.getMessage());
            logger.error("Error details: ", e);
        }
    }
}
