package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.OrderStatus;
import za.ac.cput.domain.Laptop;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Payment;
import za.ac.cput.domain.PaymentStatus;
import za.ac.cput.service.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final IOrderService orderService;
    private final ILaptopService laptopService;
    private final IUserService userService;
    private final IPaymentService paymentService;
    private final OrderProcessingService orderProcessingService;

    @Autowired
    public AdminController(IOrderService orderService, ILaptopService laptopService,
                           IUserService userService, IPaymentService paymentService,
                           OrderProcessingService orderProcessingService) {
        this.orderService = orderService;
        this.laptopService = laptopService;
        this.userService = userService;
        this.paymentService = paymentService;
        this.orderProcessingService = orderProcessingService;
    }

    // ===== ORDER MANAGEMENT =====

    @GetMapping("/orders")
    public ResponseEntity<List<za.ac.cput.dto.OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.getAll();
        List<za.ac.cput.dto.OrderDTO> orderDTOs = orders.stream()
                .map(order -> {
                    Payment payment = paymentService.findByOrderId(order.getOrderID());
                    return orderProcessingService.convertToDTO(order, payment);
                })
                .toList();
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<za.ac.cput.dto.OrderDTO> getOrderById(@PathVariable int id) {
        Order order = orderService.read(id);
        if (order == null) return ResponseEntity.notFound().build();
        Payment payment = paymentService.findByOrderId(order.getOrderID());
        za.ac.cput.dto.OrderDTO orderDTO = orderProcessingService.convertToDTO(order, payment);
        return ResponseEntity.ok(orderDTO);
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable int id,
            @RequestParam(value = "status", required = false) OrderStatus statusParam,
            @RequestBody(required = false) StatusUpdateRequest body
    ) {
        try {
            OrderStatus status = statusParam != null ? statusParam : (body != null ? body.getStatus() : null);
            if (status == null) {
                return ResponseEntity.badRequest().build();
            }
            Order order = orderService.read(id);
            if (order != null) {
                Order updatedOrder = new Order.Builder()
                        .copy(order)
                        .setStatus(status)
                        .build();
                Order result = orderService.update(updatedOrder);
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Optional path-variable variant to support /api/admin/orders/{id}/status/{status}
    @PutMapping("/orders/{id}/status/{status}")
    public ResponseEntity<Order> updateOrderStatusPath(@PathVariable int id, @PathVariable OrderStatus status) {
        return updateOrderStatus(id, status, null);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        try {
            orderService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/orders/statuses")
    public ResponseEntity<OrderStatus[]> getOrderStatuses() {
        return ResponseEntity.ok(OrderStatus.values());
    }

    // ===== PRODUCT MANAGEMENT =====
    @GetMapping("/products")
    public ResponseEntity<List<Laptop>> getAllProducts() {
        List<Laptop> laptops = laptopService.getAll();
        return ResponseEntity.ok(laptops);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Laptop> getProductById(@PathVariable int id) {
        Laptop laptop = laptopService.read(id);
        return laptop != null ? ResponseEntity.ok(laptop) : ResponseEntity.notFound().build();
    }

    @PostMapping("/products")
    public ResponseEntity<Laptop> createProduct(@RequestBody Laptop laptop) {
        try {
            Laptop createdLaptop = laptopService.save(laptop);
            return ResponseEntity.ok(createdLaptop);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/products")
    public ResponseEntity<Laptop> updateProduct(@RequestBody Laptop laptop) {
        try {
            Laptop updatedLaptop = laptopService.update(laptop);
            return ResponseEntity.ok(updatedLaptop);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        try {
            laptopService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ===== USER MANAGEMENT =====
    @GetMapping("/users")
    public ResponseEntity<List<za.ac.cput.dto.UserDTO>> getAllUsers() {
        List<User> users = userService.getAll();
        List<za.ac.cput.dto.UserDTO> userDTOs = users.stream().map(user -> {
            za.ac.cput.dto.UserDTO dto = new za.ac.cput.dto.UserDTO();
            dto.setUserId(user.getUserID());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setRole(user.getRole());
            if (user.getContact() != null) {
                dto.setEmail(user.getContact().getEmail());
                dto.setPhone(user.getContact().getPhoneNumber());
            } else {
                dto.setEmail(null);
                dto.setPhone(null);
            }
            return dto;
        }).toList();
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.read(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            User updatedUser = userService.update(user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ===== PAYMENT MANAGEMENT =====
    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAll();
        return ResponseEntity.ok(payments);
    }

    @PutMapping("/payments/{id}/status")
    public ResponseEntity<Payment> updatePaymentStatus(@PathVariable int id, @RequestParam PaymentStatus status) {
        try {
            Payment payment = paymentService.read(id);
            if (payment != null) {
                Payment updatedPayment = new Payment.Builder()
                        .copy(payment)
                        .setStatus(status)
                        .build();
                Payment result = paymentService.update(updatedPayment);
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ===== DASHBOARD STATISTICS =====
    @GetMapping("/dashboard/stats")
    public ResponseEntity<DashboardStats> getDashboardStats() {
        try {
            List<Order> orders = orderService.getAll();
            List<User> users = userService.getAll();
            List<Laptop> products = laptopService.getAll();
            List<Payment> payments = paymentService.getAll();

            DashboardStats stats = new DashboardStats();
            stats.setTotalOrders(orders.size());
            stats.setTotalUsers(users.size());
            stats.setTotalProducts(products.size());
            stats.setTotalPayments(payments.size());

            // Calculate pending orders
            long pendingOrders = orders.stream()
                    .filter(order -> "PENDING".equals(order.getStatus()))
                    .count();
            stats.setPendingOrders((int) pendingOrders);

            // Calculate total revenue
            double totalRevenue = orders.stream()
                    .mapToDouble(Order::getTotalAmount)
                    .sum();
            stats.setTotalRevenue(totalRevenue);

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Inner class for dashboard statistics
    public static class DashboardStats {
        private int totalOrders;
        private int totalUsers;
        private int totalProducts;
        private int totalPayments;
        private int pendingOrders;
        private double totalRevenue;

        // Getters and setters
        public int getTotalOrders() { return totalOrders; }
        public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }

        public int getTotalUsers() { return totalUsers; }
        public void setTotalUsers(int totalUsers) { this.totalUsers = totalUsers; }

        public int getTotalProducts() { return totalProducts; }
        public void setTotalProducts(int totalProducts) { this.totalProducts = totalProducts; }

        public int getTotalPayments() { return totalPayments; }
        public void setTotalPayments(int totalPayments) { this.totalPayments = totalPayments; }

        public int getPendingOrders() { return pendingOrders; }
        public void setPendingOrders(int pendingOrders) { this.pendingOrders = pendingOrders; }

        public double getTotalRevenue() { return totalRevenue; }
        public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
    }

    // Small request DTO for status updates via JSON
    public static class StatusUpdateRequest {
        private OrderStatus status;
        public OrderStatus getStatus() { return status; }
        public void setStatus(OrderStatus status) { this.status = status; }
    }
}
