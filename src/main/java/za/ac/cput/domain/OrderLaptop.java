package za.ac.cput.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_laptops")
public class OrderLaptop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @com.fasterxml.jackson.annotation.JsonBackReference("order-laptops")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    // Constructors
    protected OrderLaptop() {}

    private OrderLaptop(Builder builder) {
        this.id = builder.id;
        this.order = builder.order;
        this.laptop = builder.laptop;
        this.quantity = builder.quantity;
        this.unitPrice = builder.unitPrice;
    }

    // Getters
    public Long getId() { return id; }
    public Order getOrder() { return order; }
    public Laptop getLaptop() { return laptop; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }

    // Calculated total price for this line item
    public double getTotalPrice() {
        return quantity * unitPrice;
    }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setOrder(Order order) { this.order = order; }
    public void setLaptop(Laptop laptop) { this.laptop = laptop; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLaptop that)) return false;
        return quantity == that.quantity &&
                Double.compare(that.unitPrice, unitPrice) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(order, that.order) &&
                Objects.equals(laptop, that.laptop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, laptop, quantity, unitPrice);
    }

    @Override
    public String toString() {
        return "OrderLaptop{" +
                "id=" + id +
                ", orderId=" + (order != null ? order.getOrderID() : null) +
                ", laptopId=" + (laptop != null ? laptop.getLaptopID() : null) +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }

    // Builder Pattern
    public static class Builder {
        private Long id;
        private Order order;
        private Laptop laptop;
        private int quantity;
        private double unitPrice;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder setLaptop(Laptop laptop) {
            this.laptop = laptop;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public Builder copy(OrderLaptop orderLaptop) {
            this.id = orderLaptop.id;
            this.order = orderLaptop.order;
            this.laptop = orderLaptop.laptop;
            this.quantity = orderLaptop.quantity;
            this.unitPrice = orderLaptop.unitPrice;
            return this;
        }

        public OrderLaptop build() {
            return new OrderLaptop(this);
        }
    }
}
