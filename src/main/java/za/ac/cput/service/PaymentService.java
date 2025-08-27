package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Payment;
import za.ac.cput.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    @Override
    public Payment create(Payment payment) {
        if (payment.getAmount() <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment read(int id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        return payment.orElse(null);
    }

    @Override
    public Payment update(Payment payment) {
        if (!paymentRepository.existsById(payment.getPaymentID())) {
            return null; // Payment doesn't exist
        }
        return paymentRepository.save(payment);
    }

    @Override
    public void delete(int id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
        }
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }
}


