package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Payment;

import static org.junit.jupiter.api.Assertions.*;

class PaymentFactoryTest {

    @Test
    void testCreatePayment_ValidInput() {
        //creating payment using PyamentFactory
        Payment payment = PaymentFactory.createPayment(
                15,
                7500.00,
                "Master Card",
                "Successful");

        // Validate that payment object is not null
        assertNotNull(payment);
        assertEquals(15, payment.getPaymentID());
        assertEquals(7500.00, payment.getAmount());
        assertEquals("Master Card", payment.getMethod());
        assertEquals("Successful", payment.getStatus());

        System.out.println(payment);
    }

    @Test
    void testCreatePayment_InvalidID() {
        Payment payment = PaymentFactory.createPayment(0, 7500.00, "Master Card", "Successful");
        assertNull(payment, "Payment should be null when paymentID <= 0");
    }

    @Test
    void testCreatePayment_InvalidMethodOrStatus() {
        Payment payment = PaymentFactory.createPayment(15, 7500.00, "", "Successful");
        assertNull(payment, "Payment should be null when method is empty");

        payment = PaymentFactory.createPayment(15, 7500.00, "Master Card", "");
        assertNull(payment, "Payment should be null when status is empty");
    }

    @Test
    void testCreatePayment_InvalidAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PaymentFactory.createPayment(1, -7500.00, "Card", "Completed"));

        assertEquals("Payment amount must be positive", exception.getMessage());
    }
}
