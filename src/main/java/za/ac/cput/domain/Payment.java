
package za.ac.cput.domain;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Payment {
    public void setOrder(Order order) {
        this.order = order;
    }
    public Order getOrder() {
        return order;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentID;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference("order-payment")
    private Order order;

    public Payment() {
    }

    private Payment (Payment.Builder builder) {
        this.paymentID = builder.paymentID;
        this.amount = builder.amount;
        this.method = builder.method;
        this.status = builder.status;
    }


    public int getPaymentID() { return paymentID; }

    public double getAmount() { return amount; }

    public PaymentMethod getMethod() { return method; }

    public PaymentStatus getStatus() { return status; }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID=" + paymentID +
                ", amount=" + amount +
                ", method='" + method + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Builder {
        private int paymentID;
        private double amount;
        private PaymentMethod method;
        private PaymentStatus status;
        private Order order;

        public Builder setPaymentID(int paymentID) {
            this.paymentID = paymentID;
            return this;
        }
        public Builder setAmount(double amount) {
            this.amount = amount;
            return this;
        }
        public Builder setMethod(PaymentMethod method) {
            this.method = method;
            return this;
        }
        public Builder setStatus(PaymentStatus status) {
            this.status = status;
            return this;
        }
        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }
        public Builder copy(Payment payment) {
            this.paymentID = payment.paymentID;
            this.amount = payment.amount;
            this.method = payment.method;
            this.status = payment.status;
            this.order = payment.order;
            return this;
        }
        public Payment build() {
            Payment payment = new Payment(this);
            payment.setOrder(this.order);
            return payment;
        }
    }
}
