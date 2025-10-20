package za.ac.cput.dto;

import za.ac.cput.domain.OrderStatus;
import za.ac.cput.domain.PaymentMethod;
import za.ac.cput.domain.PaymentStatus;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private int orderID;
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private Date orderDate;
    private OrderStatus status;
    private double totalAmount;
    private List<LaptopDTO> laptops;
    private PaymentDTO payment;

    // Constructors
    public OrderDTO() {}

    // Getters and Setters
    public int getOrderID() { return orderID; }
    public void setOrderID(int orderID) { this.orderID = orderID; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserFirstName() { return userFirstName; }
    public void setUserFirstName(String userFirstName) { this.userFirstName = userFirstName; }

    public String getUserLastName() { return userLastName; }
    public void setUserLastName(String userLastName) { this.userLastName = userLastName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public List<LaptopDTO> getLaptops() { return laptops; }
    public void setLaptops(List<LaptopDTO> laptops) { this.laptops = laptops; }

    public PaymentDTO getPayment() { return payment; }
    public void setPayment(PaymentDTO payment) { this.payment = payment; }

    // Inner classes for nested DTOs
    public static class LaptopDTO {
        private int laptopID;
        private String brand;
        private String model;
        private double price;
        private int quantity;

        // Constructors, getters, and setters
        public LaptopDTO() {}

        public int getLaptopID() { return laptopID; }
        public void setLaptopID(int laptopID) { this.laptopID = laptopID; }

        public String getBrand() { return brand; }
        public void setBrand(String brand) { this.brand = brand; }

        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    public static class PaymentDTO {
        private int paymentID;
        private double amount;
        private PaymentMethod method;
        private PaymentStatus status;

        // Constructors, getters, and setters
        public PaymentDTO() {}

        public int getPaymentID() { return paymentID; }
        public void setPaymentID(int paymentID) { this.paymentID = paymentID; }

        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }

        public PaymentMethod getMethod() { return method; }
        public void setMethod(PaymentMethod method) { this.method = method; }

        public PaymentStatus getStatus() { return status; }
        public void setStatus(PaymentStatus status) { this.status = status; }
    }
}