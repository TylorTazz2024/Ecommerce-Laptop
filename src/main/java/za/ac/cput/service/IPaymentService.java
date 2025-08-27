package za.ac.cput.service;

import za.ac.cput.domain.Payment;
import java.util.List;

public interface IPaymentService {
    Payment create(Payment payment);

    Payment read(int id);

    Payment update(Payment payment);

    void delete(int id);

    List<Payment> getAll();
}


