package za.ac.cput.service;

import za.ac.cput.domain.Payment;

import java.util.List;

public interface IPaymentService {
    Payment create(Payment payment);
    Payment update(Payment payment);
    Payment read(Integer id);
    void delete(Integer id);
    List<Payment> getAll();
    Payment findByOrderId(int orderId);
}