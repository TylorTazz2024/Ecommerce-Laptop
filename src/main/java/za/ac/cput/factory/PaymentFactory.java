package za.ac.cput.factory;

import za.ac.cput.domain.Payment;
import za.ac.cput.util.Helper;

public class PaymentFactory {

    public static Payment createPayment(int paymentID, double amount, String method, String status) {
        if (Helper.isNullOrEmpty(method) ||
                Helper.isNullOrEmpty(status)) {

            return null;
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }

        return new Payment.Builder()
                .setPaymentID(paymentID)
                .setAmount(amount)
                .setMethod(method)
                .setStatus(status)
                .build();

    }
    }
