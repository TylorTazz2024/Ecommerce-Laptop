package za.ac.cput.dto;

import za.ac.cput.domain.OrderStatus;
import za.ac.cput.domain.PaymentMethod;
import za.ac.cput.domain.PaymentStatus;

import java.util.Date;
import java.util.List;

/**
 * DTO for displaying order information with laptop details and payment info
 */
public class OrderDisplayDTO {
    private int orderID;
    private Date orderDate;
    private OrderStatus status;
    private double totalAmount;
    private List<LaptopSummary> laptops;
    private PaymentInfo payment;

    public OrderDisplayDTO() {}

    public OrderDisplayDTO(int orderID, Date orderDate, OrderStatus status, double totalAmount,
                           List<LaptopSummary> laptops, PaymentInfo payment) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.laptops = laptops;
        this.payment = payment;
    }

    // Getters and setters
    public int getOrderID() { return orderID; }
    public void setOrderID(int orderID) { this.orderID = orderID; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public List<LaptopSummary> getLaptops() { return laptops; }
    public void setLaptops(List<LaptopSummary> laptops) { this.laptops = laptops; }

    public PaymentInfo getPayment() { return payment; }
    public void setPayment(PaymentInfo payment) { this.payment = payment; }

    public static class LaptopSummary {
        private int laptopID;
        private String brand;
        private String model;
        private double unitPrice;
        private int quantity;
        private double totalPrice;

        public LaptopSummary() {}

        public LaptopSummary(int laptopID, String brand, String model, double unitPrice, int quantity) {
            this.laptopID = laptopID;
            this.brand = brand;
            this.model = model;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
            this.totalPrice = unitPrice * quantity;
        }

        // Constructor for backward compatibility (without quantity)
        public LaptopSummary(int laptopID, String brand, String model, double price) {
            this.laptopID = laptopID;
            this.brand = brand;
            this.model = model;
            this.unitPrice = price;
            this.quantity = 1; // Default quantity
            this.totalPrice = price;
        }

        public int getLaptopID() { return laptopID; }
        public void setLaptopID(int laptopID) { this.laptopID = laptopID; }

        public String getBrand() { return brand; }
        public void setBrand(String brand) { this.brand = brand; }

        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }

        public double getUnitPrice() { return unitPrice; }
        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
            this.totalPrice = unitPrice * quantity;
        }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
            this.totalPrice = unitPrice * quantity;
        }

        public double getTotalPrice() { return totalPrice; }
        public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    }

    public static class PaymentInfo {
        private PaymentMethod method;
        private PaymentStatus status;
        private double amount;

        public PaymentInfo() {}

        public PaymentInfo(PaymentMethod method, PaymentStatus status, double amount) {
            this.method = method;
            this.status = status;
            this.amount = amount;
        }

        public PaymentMethod getMethod() { return method; }
        public void setMethod(PaymentMethod method) { this.method = method; }

        public PaymentStatus getStatus() { return status; }
        public void setStatus(PaymentStatus status) { this.status = status; }

        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
    }
}