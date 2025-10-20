package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.*;
import za.ac.cput.dto.OrderDTO;
import za.ac.cput.repository.OrderLaptopRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderProcessingService {

    private final IOrderService orderService;
    private final IPaymentService paymentService;
    private final IUserService userService;
    private final ILaptopService laptopService;
    private final OrderLaptopRepository orderLaptopRepository;

    @Autowired
    public OrderProcessingService(IOrderService orderService, IPaymentService paymentService,
                                  IUserService userService, ILaptopService laptopService,
                                  OrderLaptopRepository orderLaptopRepository) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.userService = userService;
        this.laptopService = laptopService;
        this.orderLaptopRepository = orderLaptopRepository;
    }

    public OrderDTO createOrderWithPayment(CreateOrderRequest request) {
        // Get user
        User user = userService.read(request.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Count laptop quantities from the request
        Map<Integer, Long> laptopQuantities = request.getLaptopIds().stream()
                .collect(Collectors.groupingBy(
                        id -> id,
                        Collectors.counting()
                ));

        // Get unique laptops and validate
        List<Laptop> uniqueLaptops = laptopQuantities.keySet().stream()
                .map(id -> laptopService.read(id))
                .filter(laptop -> laptop != null)
                .collect(Collectors.toList());

        if (uniqueLaptops.isEmpty()) {
            throw new RuntimeException("No valid laptops found");
        }

        // Create OrderLaptop entities with proper quantities
        List<OrderLaptop> orderLaptops = uniqueLaptops.stream()
                .map(laptop -> new OrderLaptop.Builder()
                        .setLaptop(laptop)
                        .setQuantity(laptopQuantities.get(laptop.getLaptopID()).intValue())
                        .setUnitPrice(laptop.getPrice())
                        .build())
                .collect(Collectors.toList());

        // Calculate total amount
        double totalAmount = orderLaptops.stream()
                .mapToDouble(OrderLaptop::getTotalPrice)
                .sum();

        // Create order
        Order order = new Order.Builder()
                .setUser(user)
                .setOrderDate(new Date())
                .setStatus(OrderStatus.PENDING)
                .setTotalAmount(totalAmount)
                .setOrderLaptops(orderLaptops)
                .build();

        Order savedOrder = orderService.create(order);

        // Create payment
        Payment payment = new Payment.Builder()
                .setOrder(savedOrder)
                .setAmount(totalAmount)
                .setMethod(request.getPaymentMethod())
                .setStatus(PaymentStatus.PENDING)
                .build();

        Payment savedPayment = paymentService.create(payment);

        // Convert to DTO
        return convertToDTO(savedOrder, savedPayment);
    }

    public OrderDTO convertToDTO(Order order, Payment payment) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderID(order.getOrderID());
        dto.setUserId(order.getUser().getUserID());
        dto.setUserFirstName(order.getUser().getFirstName());
        dto.setUserLastName(order.getUser().getLastName());
        dto.setUserEmail(order.getUser().getContact().getEmail());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());

        // Convert OrderLaptops to DTO
        List<OrderDTO.LaptopDTO> laptopDTOs = order.getOrderLaptops().stream()
                .map(orderLaptop -> {
                    OrderDTO.LaptopDTO laptopDTO = new OrderDTO.LaptopDTO();
                    Laptop laptop = orderLaptop.getLaptop();
                    laptopDTO.setLaptopID(laptop.getLaptopID());
                    laptopDTO.setBrand(laptop.getBrand());
                    laptopDTO.setModel(laptop.getModel());
                    laptopDTO.setPrice(orderLaptop.getUnitPrice()); // Use order price, not current laptop price
                    laptopDTO.setQuantity(orderLaptop.getQuantity()); // Add quantity
                    return laptopDTO;
                })
                .collect(Collectors.toList());
        dto.setLaptops(laptopDTOs);

        // Convert payment to DTO
        if (payment != null) {
            OrderDTO.PaymentDTO paymentDTO = new OrderDTO.PaymentDTO();
            paymentDTO.setPaymentID(payment.getPaymentID());
            paymentDTO.setAmount(payment.getAmount());
            paymentDTO.setMethod(payment.getMethod());
            paymentDTO.setStatus(payment.getStatus());
            dto.setPayment(paymentDTO);
        }

        return dto;
    }

    // Request DTO for creating orders
    public static class CreateOrderRequest {
        private Long userId;
        private List<Integer> laptopIds;
        private PaymentMethod paymentMethod;

        // Constructors, getters, and setters
        public CreateOrderRequest() {}

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public List<Integer> getLaptopIds() { return laptopIds; }
        public void setLaptopIds(List<Integer> laptopIds) { this.laptopIds = laptopIds; }

        public PaymentMethod getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

        @Override
        public String toString() {
            return "CreateOrderRequest{" +
                    "userId=" + userId +
                    ", laptopIds=" + laptopIds +
                    ", paymentMethod=" + paymentMethod +
                    '}';
        }
    }
}