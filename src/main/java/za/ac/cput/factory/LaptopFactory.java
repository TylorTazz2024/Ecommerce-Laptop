package za.ac.cput.factory;
import za.ac.cput.domain.Laptop;
import za.ac.cput.util.Helper;
/*
Laptop factory Class
Author: Nontando Zondi 221248986
*/



public class LaptopFactory {

    public static Laptop createLaptop(int laptopID, String brand, String model, String specifications, double price, String condition) {
        if (Helper.isNullOrEmpty(brand) ||
            Helper.isNullOrEmpty(model) ||
            Helper.isNullOrEmpty(specifications) ||
            Helper.isNullOrEmpty(condition)) {

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
                .setCondition(condition)
                .build();

    }
}
