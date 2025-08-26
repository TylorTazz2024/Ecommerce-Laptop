package za.ac.cput.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Laptop;
import za.ac.cput.service.ILaptopService;


import java.util.List;

@RestController
@RequestMapping("/laptops")
public class LaptopController {

    private final ILaptopService laptopService;

    @Autowired
    public LaptopController(ILaptopService laptopService) {
        this.laptopService = laptopService;
    }

    @PostMapping("/save")
    public Laptop save(@RequestBody Laptop laptop) {
        return laptopService.save(laptop);
    }

    @GetMapping("/read/{id}")
    public Laptop read(@PathVariable int id) {
        return laptopService.read(id);
    }

    @PutMapping("/update")
    public Laptop update(@RequestBody Laptop laptop) {
        return laptopService.update(laptop);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        laptopService.delete(id);
    }

    @GetMapping("/all")
    public List<Laptop> getAll() {
        return laptopService.getAll();
    }


}
