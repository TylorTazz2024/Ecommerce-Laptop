package za.ac.cput.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import za.ac.cput.domain.Laptop;
import za.ac.cput.factory.LaptopFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LaptopServiceTest {
    @Autowired
    private LaptopService laptopService;

    private static Laptop laptop1;
    private static Laptop laptop2;

    @BeforeAll
    static void setUp() {
        laptop1 = LaptopFactory.createLaptop(
                10,
                "Dell",
                "Inspiron 15",
                "Intel i7, 16GB RAM, 512GB SSD",
                7000.00,
                "Used - Good"
        );
        assertNotNull(laptop1);
        System.out.println("Laptop 1: " + laptop1);


        laptop2 = LaptopFactory.createLaptop(
                1,
                "HP",
                "Laptop 14",
                "Intel HD Graphics, 2 GB RAM, 500 GB HDD",
                5700.00,
                "Used - Fairly"
        );
        assertNotNull(laptop2);
        System.out.println("Laptop 2: " + laptop2);
    }

    @Test
    @Order(1)
    void create() {
        Laptop createdLaptop1 = laptopService.create(laptop1);
        assertNotNull(createdLaptop1);
        laptop1 = createdLaptop1; // Update with persisted instance
        System.out.println("Created: " + createdLaptop1);

        Laptop createdLaptop2 = laptopService.create(laptop2);
        assertNotNull(createdLaptop2);
        laptop2 = createdLaptop2;
        System.out.println("Created: " + createdLaptop2);
    }

    @Test
    @Order(2)
    void read() {
        Laptop readLaptop = laptopService.read(laptop1.getLaptopID());
        assertNotNull(readLaptop);
        System.out.println("Read: " + readLaptop);
    }

    @Test
    @Order(3)
    void update() {
        Laptop updatedLaptop = new Laptop.Builder()
                .copy(laptop2)
                .setSpecifications("Intel i5, 8GB RAM, 512GB SSD") // Updated specs
                .build();

        Laptop result = laptopService.update(updatedLaptop);
        assertNotNull(result);
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(4)
    void getAll() {
        List<Laptop> laptops = laptopService.getAll();
        assertNotNull(laptops);
        assertTrue(laptops.size() >= 2);
        System.out.println("All Laptops: " + laptops);
    }

    @Test
    @Order(5)
    void delete() {
        laptopService.delete(laptop1.getLaptopID());
        Laptop deletedLaptop = laptopService.read(laptop1.getLaptopID());
        assertNull(deletedLaptop);
        System.out.println("Deleted Laptop ID: " + laptop1.getLaptopID());
    }
}


