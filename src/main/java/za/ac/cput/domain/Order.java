package za.ac.cput.domain;

import java.util.Date;

public class Order {

    private int orderID;
    private Date orderDate;
    private String status;
    private double totalAmount;


    private Order(Builder builder) {
        this.orderID = builder.orderID;
        this.orderDate = builder.orderDate;
        this.status = builder.status;
        this.totalAmount = builder.totalAmount;
    }


    public int getOrderID() {
        return orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }


    public void processPayment() {
        if ("Pending".equalsIgnoreCase(status)) {
            status = "Paid";
            System.out.println("Payment processed for Order ID: " + orderID);
        } else {
            System.out.println("Order cannot be paid. Current status: " + status);
        }
    }

    public void refundPayment() {
        if ("Paid".equalsIgnoreCase(status)) {
            status = "Refunded";
            System.out.println("Refund issued for Order ID: " + orderID);
        } else {
            System.out.println("Refund not possible. Current status: " + status);
        }
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

        // Build method
        public Order build() {
            return new Order(this);
        }
    }
}
