package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Payment;
import za.ac.cput.service.IPaymentService;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final IPaymentService paymentService;

    @Autowired
    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public Payment create(@RequestBody Payment payment) {
        return paymentService.create(payment);
    }


    @GetMapping("/read/{id}")
    public Payment read(@PathVariable int id) {
        return paymentService.read(id);
    }

    @PutMapping("/update")
    public Payment update(@RequestBody Payment payment) {
        return paymentService.update(payment);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        paymentService.delete(id);
    }

    @GetMapping("/all")
    public List<Payment> getAll() {
        return paymentService.getAll();
    }
}


