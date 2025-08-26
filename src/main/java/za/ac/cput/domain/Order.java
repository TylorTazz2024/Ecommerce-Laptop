package za.ac.cput.domain;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderID;

    private Date orderDate;

    private String status;

    private double totalAmount;

    protected Order() {}

    private Order(Builder builder) {
        this.orderID = builder.orderID;
        this.orderDate = builder.orderDate;
        this.status = builder.status;
        this.totalAmount = builder.totalAmount;
    }

    public int getOrderID() { return orderID; }
    public Date getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    public double getTotalAmount() { return totalAmount; }

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
        private String status;
        private double totalAmount;

        public Builder setOrderID(int orderID) {
            this.orderID = orderID;
            return this;
        }

        public Builder setOrderDate(Date orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder copy(Order order) {
            this.orderID = order.orderID;
            this.orderDate = order.orderDate;
            this.status = order.status;
            this.totalAmount = order.totalAmount;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
