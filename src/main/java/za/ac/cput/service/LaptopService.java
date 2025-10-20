package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Laptop;
import za.ac.cput.repository.LaptopRepository;
import java.util.List;

@Service
public class LaptopService implements ILaptopService {

    private final LaptopRepository laptopRepository;

    @Autowired
    public LaptopService(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    @Override
    public Laptop updateLaptopImage(int laptopId, byte[] image) {
        Laptop laptop = laptopRepository.findById(laptopId).orElse(null);
        if (laptop == null) return null;
        Laptop updated = new Laptop.Builder()
                .copy(laptop)
                .setImage(image)
                .build();
        return laptopRepository.save(updated);
    }

    @Override
    public Laptop save(Laptop laptop) {
        return laptopRepository.save(laptop);
    }

    @Override
    public Laptop read(Integer id) {
        return laptopRepository.findById(id).orElse(null);
    }

    @Override
    public Laptop update(Laptop laptop) {
        Laptop existingLaptop = laptopRepository.findById(laptop.getLaptopID()).orElse(null);
        if (existingLaptop != null) {
            existingLaptop.setBrand(laptop.getBrand());
            existingLaptop.setModel(laptop.getModel());
            existingLaptop.setSpecifications(laptop.getSpecifications());
            existingLaptop.setPrice(laptop.getPrice());
            existingLaptop.setLaptopCondition(laptop.getLaptopCondition());
            existingLaptop.setImage(laptop.getImage());
            return laptopRepository.save(existingLaptop);
        }
        return null;
    }

    @Override
    public void delete(Integer laptopId) {
        laptopRepository.deleteById(laptopId);
    }

    @Override
    public List<Laptop> getAll() {
        return laptopRepository.findAll();
    }
}

