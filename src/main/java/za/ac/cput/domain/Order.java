package za.ac.cput.domain;
import java.util.List;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "orders")
public class Order {
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference("user-orders")
    private User user;

    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private double totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("order-laptops")
    private List<OrderLaptop> orderLaptops;

    @OneToOne(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference("order-payment")
    private Payment payment;

    protected Order() {}

    private Order(Builder builder) {
        this.orderID = builder.orderID;
        this.orderDate = builder.orderDate;
        this.status = builder.status;
        this.totalAmount = builder.totalAmount;
    }

    public int getOrderID() { return orderID; }
    public Date getOrderDate() { return orderDate; }
    public OrderStatus getStatus() { return status; }
    public double getTotalAmount() { return totalAmount; }
    public List<OrderLaptop> getOrderLaptops() { return orderLaptops; }

    // Helper method to get laptops (for backward compatibility)
    public List<Laptop> getLaptops() {
        if (orderLaptops == null) return null;
        return orderLaptops.stream()
                .map(OrderLaptop::getLaptop)
                .collect(java.util.stream.Collectors.toList());
    }
    public Payment getPayment() { return payment; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return orderID == order.orderID &&
                Double.compare(order.totalAmount, totalAmount) == 0 &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, orderDate, status, totalAmount);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }

    public static class Builder {
        private int orderID;
        private Date orderDate;
        private OrderStatus status;
        private double totalAmount;
        private User user;
        private List<OrderLaptop> orderLaptops;
        private Payment payment;

        public Builder setOrderID(int orderID) {
            this.orderID = orderID;
            return this;
        }

        public Builder setOrderDate(Date orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder setStatus(OrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }
        public Builder setUser(User user) {
            this.user = user;
            return this;
        }
        public Builder setOrderLaptops(List<OrderLaptop> orderLaptops) {
            this.orderLaptops = orderLaptops;
            return this;
        }

        // Helper method for backward compatibility
        public Builder setLaptops(List<Laptop> laptops) {
            if (laptops != null) {
                this.orderLaptops = laptops.stream()
                        .map(laptop -> new OrderLaptop.Builder()
                                .setLaptop(laptop)
                                .setQuantity(1) // Default quantity
                                .setUnitPrice(laptop.getPrice())
                                .build())
                        .collect(java.util.stream.Collectors.toList());
            }
            return this;
        }
        public Builder setPayment(Payment payment) {
            this.payment = payment;
            return this;
        }

        public Builder copy(Order order) {
            this.orderID = order.orderID;
            this.orderDate = order.orderDate;
            this.status = order.status;
            this.totalAmount = order.totalAmount;
            this.user = order.user;
            this.orderLaptops = order.orderLaptops;
            this.payment = order.payment;
            return this;
        }

        public Order build() {
            Order order = new Order(this);
            order.user = this.user;
            order.orderLaptops = this.orderLaptops;
            // Set the order reference in each OrderLaptop
            if (this.orderLaptops != null) {
                this.orderLaptops.forEach(ol -> ol.setOrder(order));
            }
            order.payment = this.payment;
            return order;
        }
    }
}