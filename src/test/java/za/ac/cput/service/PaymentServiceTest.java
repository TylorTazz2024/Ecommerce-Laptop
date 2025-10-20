//package za.ac.cput.service;
//
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import za.ac.cput.domain.Payment;
//import za.ac.cput.factory.PaymentFactory;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class PaymentServiceTest {
//
//    @Autowired
//    private PaymentService paymentService;
//
//    private static Payment payment1;
//    private static Payment payment2;
//
//    @BeforeAll
//    static void setUp() {
//        payment1 = PaymentFactory.createPayment(15, 7500.00, "Mastercard", "Successful");
//        assertNotNull(payment1);
//        System.out.println("Setup Payment 1: " + payment1);
//
//        payment2 = PaymentFactory.createPayment(2, 5000.00, "PayPal", "Pending");
//        assertNotNull(payment2);
//        System.out.println("Setup Payment 2: " + payment2);
//    }
//
//    @Test
//    @Order(1)
//    void a_create() {
//        Payment created1 = paymentService.create(payment1);
//        assertNotNull(created1);
//        System.out.println("Created Payment 1: " + created1);
//
//        Payment created2 = paymentService.create(payment2);
//        assertNotNull(created2);
//        System.out.println("Created Payment 2: " + created2);
//    }
//
//    @Test
//    @Order(2)
//    void b_read() {
//        Payment readPayment = paymentService.read(payment1.getPaymentID());
//        assertNotNull(readPayment);
//        assertEquals(payment1.getPaymentID(), readPayment.getPaymentID());
//        System.out.println("Read Payment: " + readPayment);
//    }
//
//    @Test
//    @Order(3)
//    void c_update() {
//        Payment updatedPayment = new Payment.Builder()
//                .copy(payment2)
//                .setStatus("Successful")
//                .build();
//        Payment result = paymentService.update(updatedPayment);
//        assertNotNull(result);
//        assertEquals("Successful", result.getStatus());
//        System.out.println("Updated Payment: " + result);
//    }
//
//    @Test
//    @Order(4)
//    void d_getAll() {
//        List<Payment> payments = paymentService.getAll();
//        assertNotNull(payments);
//        assertTrue(payments.size() >= 2);
//        System.out.println("All Payments: " + payments);
//    }
//
//    @Test
//    @Order(5)
//    void e_delete() {
//        paymentService.delete(payment1.getPaymentID());
//        Payment deletedPayment = paymentService.read(payment1.getPaymentID());
//        assertNull(deletedPayment);
//        System.out.println("Deleted Payment with ID: " + payment1.getPaymentID());
//    }
//}
