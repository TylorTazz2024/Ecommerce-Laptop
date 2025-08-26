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
    public Laptop save (Laptop laptop) {
        return laptopRepository.save(laptop);
    }

    @Override
    public Laptop read(Integer integer) {
        return laptopRepository.findById(integer).orElse(null);
    }

    @Override
    public Laptop update(Laptop laptop) {
        return laptopRepository.save(laptop);
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
