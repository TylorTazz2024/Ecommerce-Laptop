package za.ac.cput.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int paymentID;

    private double amount;

    private String method;

    private String status;

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

    public String getMethod() { return method; }

    public String getStatus() { return status; }

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

        private String method;

        private String status;


        public Builder setPaymentID(int paymentID) {
            this.paymentID = paymentID;
            return this;
        }

        public Builder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }

        public Builder copy(Payment payment2) {

            return null;
        }
    }
}
