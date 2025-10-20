package za.ac.cput.service;

import za.ac.cput.domain.User;
import za.ac.cput.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Order;
import za.ac.cput.repository.OrderRepository;

import java.util.List;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import za.ac.cput.domain.Laptop;
import za.ac.cput.domain.OrderLaptop;
import za.ac.cput.domain.OrderStatus;
import za.ac.cput.repository.LaptopRepository;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final LaptopRepository laptopRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, LaptopRepository laptopRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.laptopRepository = laptopRepository;
    }

    @Override
    public Order create(Order order) {
        // Ensure a managed User is attached; if missing, derive from authentication
        if (order.getUser() != null && order.getUser().getUserID() != null) {
            User managedUser = userRepository.findById(order.getUser().getUserID()).orElse(null);
            order.setUser(managedUser);
        } else {
            // Derive current user from SecurityContext (email is the username)
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && auth.getName() != null) {
                User currentUser = userRepository.findByContact_Email(auth.getName());
                if (currentUser != null) {
                    order.setUser(currentUser);
                }
            }
        }

        // Attach managed Laptop entities if only IDs are provided
        if (order.getLaptops() != null && !order.getLaptops().isEmpty()) {
            List<Laptop> managedLaptops = order.getLaptops().stream()
                    .filter(Objects::nonNull)
                    .map(l -> l.getLaptopID() != 0 ? laptopRepository.findById(l.getLaptopID()).orElse(null) : null)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            order = new Order.Builder()
                    .copy(order)
                    .setLaptops(managedLaptops)
                    .build();
        }

        // Default order date
        if (order.getOrderDate() == null) {
            order = new Order.Builder()
                    .copy(order)
                    .setOrderDate(new Date())
                    .build();
        }

        // Default status
        if (order.getStatus() == null) {
            order = new Order.Builder()
                    .copy(order)
                    .setStatus(OrderStatus.PENDING)
                    .build();
        }

        // Calculate total amount if not set or zero
        if ((order.getTotalAmount() == 0 || Double.isNaN(order.getTotalAmount())) && order.getOrderLaptops() != null) {
            double total = order.getOrderLaptops().stream()
                    .filter(Objects::nonNull)
                    .mapToDouble(OrderLaptop::getTotalPrice)
                    .sum();
            order = new Order.Builder()
                    .copy(order)
                    .setTotalAmount(total)
                    .build();
        }

        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void delete(Integer orderID) {
        orderRepository.deleteById(orderID);
    }

    @Override
    public Order read(Integer orderID) {
        return orderRepository.findById(orderID).orElse(null);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUser_UserID(userId);
    }
}
