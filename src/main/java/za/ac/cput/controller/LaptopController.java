package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Laptop;
import za.ac.cput.service.ILaptopService;
import java.util.List;
import java.util.Base64;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/laptops")
public class LaptopController {

    @PostMapping("/create-with-image")
    @PreAuthorize("hasRole('ADMIN')")
    public Laptop createWithImage(
            @RequestParam("brand") String brand,
            @RequestParam("model") String model,
            @RequestParam("specifications") String specifications,
            @RequestParam("price") double price,
            @RequestParam("laptopCondition") String laptopCondition,
            @RequestParam(value = "image", required = false) org.springframework.web.multipart.MultipartFile image
    ) {
        try {
            Laptop.Builder builder = new Laptop.Builder()
                    .setBrand(brand)
                    .setModel(model)
                    .setSpecifications(specifications)
                    .setPrice(price)
                    .setLaptopCondition(laptopCondition);
            if (image != null) {
                if (!image.isEmpty()) {
                    byte[] bytes = image.getBytes();
                    builder.setImage(bytes);
                    System.out.println("[LaptopController] Received image bytes: " + bytes.length);
                } else {
                    System.out.println("[LaptopController] Multipart 'image' was provided but empty.");
                }
            } else {
                System.out.println("[LaptopController] No Multipart 'image' provided in request.");
            }
            Laptop laptop = builder.build();
            return laptopService.save(laptop);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create laptop with image", e);
        }
    }

    // New: JSON create that accepts optional Base64 image string for easier frontend usage
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public Laptop createJson(@RequestBody CreateLaptopRequest req) {
        try {
            Laptop.Builder builder = new Laptop.Builder()
                    .setBrand(req.getBrand())
                    .setModel(req.getModel())
                    .setSpecifications(req.getSpecifications())
                    .setPrice(req.getPrice())
                    .setLaptopCondition(req.getLaptopCondition());
            if (req.getImageBase64() != null && !req.getImageBase64().isBlank()) {
                byte[] bytes = Base64.getDecoder().decode(req.getImageBase64());
                builder.setImage(bytes);
                System.out.println("[LaptopController] Decoded Base64 image bytes: " + bytes.length);
            }
            Laptop laptop = builder.build();
            return laptopService.save(laptop);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create laptop with JSON/base64 image", e);
        }
    }
    @PostMapping("/{id}/upload-image")
    @PreAuthorize("hasRole('ADMIN')")
    public Laptop uploadImage(@PathVariable int id, @RequestParam("image") org.springframework.web.multipart.MultipartFile image) {
        try {
            return laptopService.updateLaptopImage(id, image.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    private final ILaptopService laptopService;

    @Autowired
    public LaptopController(ILaptopService laptopService) {
        this.laptopService = laptopService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Laptop save(@RequestBody Laptop laptop) {
        return laptopService.save(laptop);
    }

    @GetMapping("/read/{id}")
    public Laptop read(@PathVariable int id) {
        return laptopService.read(id);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Laptop update(@RequestBody Laptop laptop) {
        return laptopService.update(laptop);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        laptopService.delete(id);
    }

    @GetMapping("/all")
    public List<Laptop> getAll() {
        List<Laptop> laptops = laptopService.getAll();
        System.out.println("Fetched laptops: " + (laptops != null ? laptops.size() : 0));
        return laptops;
    }

    // Optional: fetch image bytes for a laptop (useful to verify BLOB was saved)
    @GetMapping(value = "/{id}/image", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        Laptop l = laptopService.read(id);
        if (l == null || l.getImage() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(l.getImage());
    }

    // Request body model for JSON create
    public static class CreateLaptopRequest {
        private String brand;
        private String model;
        private String specifications;
        private double price;
        private String laptopCondition;
        private String imageBase64; // optional

        public String getBrand() { return brand; }
        public void setBrand(String brand) { this.brand = brand; }
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public String getSpecifications() { return specifications; }
        public void setSpecifications(String specifications) { this.specifications = specifications; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public String getLaptopCondition() { return laptopCondition; }
        public void setLaptopCondition(String laptopCondition) { this.laptopCondition = laptopCondition; }
        public String getImageBase64() { return imageBase64; }
        public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
    }


}
