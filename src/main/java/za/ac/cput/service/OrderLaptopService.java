package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.OrderLaptop;
import za.ac.cput.repository.IOrderLaptopRepository;

import java.util.List;


@Service
public class OrderLaptopService implements IOrderLaptopService {

    private final IOrderLaptopRepository repository;

    @Autowired
    public OrderLaptopService(IOrderLaptopRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderLaptop save(OrderLaptop orderLaptop) {
        return repository.save(orderLaptop);
    }

    public OrderLaptop create(OrderLaptop orderLaptop) {
        return repository.save(orderLaptop);
    }

    @Override
    public OrderLaptop read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public OrderLaptop update(OrderLaptop orderLaptop) {
        if (repository.existsById(orderLaptop.getId())) {
            return repository.save(orderLaptop);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<OrderLaptop> getAll() {
        return repository.findAll();
    }

    @Override
    public List<OrderLaptop> getByOrderId(int orderID) {
        return repository.findByOrderOrderID(orderID);
    }

    @Override
    public List<OrderLaptop> getByLaptopId(int laptopID) {
        return repository.findByLaptopLaptopID(laptopID);
    }

    @Override
    public OrderLaptop getByOrderIdAndLaptopId(int orderID, int laptopID) {
        return repository.findByOrderOrderIDAndLaptopLaptopID(orderID, laptopID);
    }

    @Override
    public boolean hasUserPurchasedLaptop(int userId, int laptopId) {
        return repository.existsByUserIdAndLaptopIdAndOrderDelivered(userId, laptopId);
    }

    @Override
    public int getTotalQuantityForLaptop(int laptopId) {
        return repository.getTotalQuantityForLaptop(laptopId);
    }

    @Override
    public List<OrderLaptop> getByOrderStatus(String status) {
        return repository.findByOrderStatus(status);
    }
}