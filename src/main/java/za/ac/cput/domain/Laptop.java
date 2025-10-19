package za.ac.cput.domain;
import java.util.List;
import jakarta.persistence.*;
import jakarta.persistence.OneToMany;

/*
Laptop domain Class
Author: Nontando Zondi 221248986
*/


@Entity
public class Laptop {
    public void setBrand(String brand) { this.brand = brand; }
    public void setModel(String model) { this.model = model; }
    public void setSpecifications(String specifications) { this.specifications = specifications; }
    public void setPrice(double price) { this.price = price; }
    public void setLaptopCondition(String laptopCondition) { this.laptopCondition = laptopCondition; }
    public void setImage(byte[] image) { this.image = image; }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int laptopID;

    private String brand;

    private String model;

    private String specifications;

    private double price;

    private String laptopCondition;

    @OneToMany(mappedBy = "laptop")
    private List<Review> reviews;

    @OneToMany(mappedBy = "laptop")
    private List<OrderLaptop> orderLaptops;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    public Laptop() {}

    private Laptop (Builder builder) {
        this.laptopID = builder.laptopID;
        this.brand = builder.brand;
        this.model = builder.model;
        this.specifications = builder.specifications;
        this.price = builder.price;
        this.laptopCondition = builder.laptopCondition;
        this.image = builder.image;
    }


    public int getLaptopID() { return laptopID; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public String getSpecifications() { return specifications; }
    public double getPrice() { return price; }
    public String getLaptopCondition() { return laptopCondition; }
    public byte[] getImage() { return image; }


    @Override
    public String toString() {
        return "Laptop{" +
                "laptopID=" + laptopID +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", specifications='" + specifications + '\'' +
                ", price=" + price +
                ", laptopCondition='" + laptopCondition + '\'' +
                '}';
    }


    public static class Builder {

        private int laptopID;
        private String brand;
        private String model;
        private String specifications;
        private double price;
        private String laptopCondition;
        private List<OrderLaptop> orderLaptops;
        private List<Review> reviews;
        private byte[] image;
        public Builder setImage(byte[] image) {
            this.image = image;
            return this;
        }


        public Builder setLaptopID(int laptopID) {
            this.laptopID = laptopID;
            return this;
        }

        public Builder setBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setSpecifications(String specifications) {
            this.specifications = specifications;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setLaptopCondition(String laptopCondition) {
            this.laptopCondition = laptopCondition;
            return this;
        }
        public Builder setOrderLaptops(List<OrderLaptop> orderLaptops) {
            this.orderLaptops = orderLaptops;
            return this;
        }
        public Builder setReviews(List<Review> reviews) {
            this.reviews = reviews;
            return this;
        }

        public Builder copy(Laptop laptop) {
            this.laptopID = laptop.laptopID;
            this.brand = laptop.brand;
            this.model = laptop.model;
            this.specifications = laptop.specifications;
            this.price = laptop.price;
            this.laptopCondition = laptop.laptopCondition;
            this.orderLaptops = laptop.orderLaptops;
            this.reviews = laptop.reviews;
            this.image = laptop.image;
            return this;
        }


        public Laptop build() {
            Laptop laptop = new Laptop(this);
            laptop.orderLaptops = this.orderLaptops;
            laptop.reviews = this.reviews;
            return laptop;
        }
    }
}
