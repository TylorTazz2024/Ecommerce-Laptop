package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Laptop;
import static org.junit.jupiter.api.Assertions.*;


public class LaptopFactoryTest {

    @Test
    void testCreateLaptop_ValidInput() {
            // creating Laptop using LaptopFactory
            Laptop laptop = LaptopFactory.createLaptop(
                    1,
                    "HP",
                    "Laptop 14",
                    "Intel HD Graphics, 2 GB RAM, 500 GB HDD",
                    5700.00,
                    "Used - Fairly"
            );

            // Validate that laptop object is not null
            assertNotNull(laptop);
            assertEquals(1, laptop.getLaptopID());
            assertEquals("HP", laptop.getBrand());
            assertEquals("Laptop 14", laptop.getModel());
            assertEquals("Intel HD Graphics, 2 GB RAM, 500 GB HDD", laptop.getSpecifications());
            assertEquals(5700.00, laptop.getPrice());
            assertEquals("Used - Fairly", laptop.getCondition());

            System.out.println("Laptop: " + laptop);
        }

        @Test
        void testCreateLaptop_InvalidInput() {
            // creating laptop with empty brand
            Laptop laptop = LaptopFactory.createLaptop(
                    1,
                    "",
                    "Laptop 14",
                    "Intel HD Graphics, 2 GB RAM, 500 GB HDD",
                    5700.00,
                    "Used - Fairly"
            );
            assertNull(laptop);

            // creating laptop with negative price
            laptop = LaptopFactory.createLaptop(
                    1,
                    "HP",
                    "Laptop 14",
                    "Intel HD Graphics, 2 GB RAM, 500 GB HDD",
                    -5700.00,
                    "Used - Fairly"
            );
            assertNull(laptop);

            //creating laptop with invalid laptopID
            laptop = LaptopFactory.createLaptop(
                    0,
                    "HP",
                    "Laptop 14",
                    "Intel HD Graphics, 2 GB RAM, 500 GB HDD",
                    5700.00,
                    "Used - Fairly"
            );
            assertNull(laptop);
        }
    }

