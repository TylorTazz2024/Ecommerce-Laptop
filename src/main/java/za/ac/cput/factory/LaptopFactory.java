package za.ac.cput.factory;
import za.ac.cput.domain.Laptop;
import za.ac.cput.util.Helper;

/*
Laptop Factory Class
Author: Nontando Zondi 221248986
*/

public class LaptopFactory {

    public static Laptop createLaptop(int laptopID, String brand, String model, String specifications, double price, String laptopCondition, java.util.List<za.ac.cput.domain.Review> reviews) {
        if (Helper.isNullOrEmpty(brand) || Helper.isNullOrEmpty(model) || Helper.isNullOrEmpty(specifications) || Helper.isNullOrEmpty(laptopCondition) || reviews == null) {
            return null;
        }
        if(laptopID <= 0 || price <= 0) {
            return null;
        }
        return new Laptop.Builder()
                .setLaptopID(laptopID)
                .setBrand(brand)
                .setModel(model)
                .setSpecifications(specifications)
                .setPrice(price)
                .setLaptopCondition(laptopCondition)
                .setReviews(reviews)
                .build();
    }
}
