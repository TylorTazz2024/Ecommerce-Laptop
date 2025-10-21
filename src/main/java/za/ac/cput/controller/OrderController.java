package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.OrderStatus;
import za.ac.cput.domain.Laptop;
import za.ac.cput.dto.OrderDTO;
import za.ac.cput.dto.OrderDisplayDTO;

import za.ac.cput.service.IOrderService;
import za.ac.cput.service.IUserService;
import za.ac.cput.service.OrderProcessingService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final IOrderService orderService;
    private final OrderProcessingService orderProcessingService;
    private final IUserService userService;

    @Autowired
    public OrderController(IOrderService orderService, OrderProcessingService orderProcessingService, IUserService userService) {
        this.orderService = orderService;
        this.orderProcessingService = orderProcessingService;
        this.userService = userService;
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Order> create(@RequestBody Order order) {
        try {
            Order createdOrder = orderService.create(order);
            return ResponseEntity.ok(createdOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create-with-payment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createOrderWithPayment(@RequestBody OrderProcessingService.CreateOrderRequest request) {
        try {
            System.out.println("Received order request: " + request);
            System.out.println("User ID: " + request.getUserId());
            System.out.println("Laptop IDs: " + request.getLaptopIds());
            System.out.println("Payment Method: " + request.getPaymentMethod());
            
            OrderDTO orderDTO = orderProcessingService.createOrderWithPayment(request);
            return ResponseEntity.ok(orderDTO);
        } catch (Exception e) {
            System.err.println("Order creation failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponse("Order creation failed: " + e.getMessage()));
        }
    }

    // Simple error response class
    public static class ErrorResponse {
        private String error;
        
        public ErrorResponse(String error) {
            this.error = error;
        }
        
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or @orderService.read(#order.orderID)?.user?.contact?.email == authentication.name")
    public ResponseEntity<Order> update(@RequestBody Order order) {
        try {
            Order updatedOrder = orderService.update(order);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update-status/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable int orderId, @RequestParam OrderStatus status) {
        try {
            Order order = orderService.read(orderId);
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

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            orderService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ADMIN') or @orderService.read(#id)?.user?.contact?.email == authentication.name")
    public ResponseEntity<Order> read(@PathVariable int id) {
        Order order = orderService.read(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAll() {
        List<Order> orders = orderService.getAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @userService.read(#userId)?.contact?.email == authentication.name")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // New: Fetch orders for the currently authenticated user (no userId needed)
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Order>> getMyOrders(Authentication authentication) {
        try {
            String email = authentication.getName();
            var user = userService.findByEmail(email);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            List<Order> orders = orderService.getOrdersByUserId(user.getUserID());
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/statuses")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderStatus[]> getOrderStatuses() {
        return ResponseEntity.ok(OrderStatus.values());
    }

    // Debug endpoint to verify database relationships
    @GetMapping("/debug/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> debugOrder(@PathVariable int orderId) {
        try {
            Order order = orderService.read(orderId);
            if (order == null) {
                return ResponseEntity.ok("Order " + orderId + " not found");
            }
            
            StringBuilder debug = new StringBuilder();
            debug.append("Order ").append(orderId).append(" Debug Info:\n");
            debug.append("- Status: ").append(order.getStatus()).append("\n");
            debug.append("- Total Amount: ").append(order.getTotalAmount()).append("\n");
            debug.append("- Laptops Count: ").append(order.getLaptops() != null ? order.getLaptops().size() : 0).append("\n");
            
            if (order.getLaptops() != null && !order.getLaptops().isEmpty()) {
                debug.append("- Laptops: ");
                for (int i = 0; i < order.getLaptops().size(); i++) {
                    var laptop = order.getLaptops().get(i);
                    debug.append(laptop.getBrand()).append(" ").append(laptop.getModel());
                    if (i < order.getLaptops().size() - 1) debug.append(", ");
                }
                debug.append("\n");
            }
            
            debug.append("- Payment Exists: ").append(order.getPayment() != null).append("\n");
            if (order.getPayment() != null) {
                debug.append("- Payment Method: ").append(order.getPayment().getMethod()).append("\n");
                debug.append("- Payment Status: ").append(order.getPayment().getStatus()).append("\n");
                debug.append("- Payment Amount: ").append(order.getPayment().getAmount()).append("\n");
            }
            
            return ResponseEntity.ok(debug.toString());
        } catch (Exception e) {
            return ResponseEntity.ok("Error debugging order " + orderId + ": " + e.getMessage());
        }
    }

    // Get laptops for a specific order
    @GetMapping("/{orderId}/laptops")
    @PreAuthorize("hasRole('ADMIN') or @orderService.read(#orderId)?.user?.contact?.email == authentication.name")
    public ResponseEntity<List<Laptop>> getOrderLaptops(@PathVariable int orderId) {
        try {
            Order order = orderService.read(orderId);
            if (order != null && order.getLaptops() != null) {
                return ResponseEntity.ok(order.getLaptops());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get detailed order information with laptop details and payment info
    @GetMapping("/{orderId}/details")
    @PreAuthorize("hasRole('ADMIN') or @orderService.read(#orderId)?.user?.contact?.email == authentication.name")
    public ResponseEntity<OrderDisplayDTO> getOrderDetails(@PathVariable int orderId) {
        try {
            Order order = orderService.read(orderId);
            if (order == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Validate order data
            if (order.getLaptops() == null || order.getLaptops().isEmpty()) {
                System.out.println("Warning: Order " + order.getOrderID() + " has no associated laptops");
            }
            if (order.getPayment() == null) {
                System.out.println("Warning: Order " + order.getOrderID() + " has no associated payment");
            }
            
            // Build laptop summaries
            List<OrderDisplayDTO.LaptopSummary> laptopSummaries = order.getLaptops().stream()
                .map(laptop -> new OrderDisplayDTO.LaptopSummary(
                    laptop.getLaptopID(),
                    laptop.getBrand(),
                    laptop.getModel(),
                    laptop.getPrice()
                ))
                .collect(Collectors.toList());
            
            // Build payment info
            OrderDisplayDTO.PaymentInfo paymentInfo = null;
            if (order.getPayment() != null) {
                paymentInfo = new OrderDisplayDTO.PaymentInfo(
                    order.getPayment().getMethod(),
                    order.getPayment().getStatus(),
                    order.getPayment().getAmount()
                );
            }
            
            // Build the DTO
            OrderDisplayDTO dto = new OrderDisplayDTO(
                order.getOrderID(),
                order.getOrderDate(),
                order.getStatus(),
                order.getTotalAmount(),
                laptopSummaries,
                paymentInfo
            );
            
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
