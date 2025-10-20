package za.ac.cput.service;
import za.ac.cput.domain.Laptop;
import java.util.List;

public interface ILaptopService extends IService<Laptop, Integer> {
    Laptop updateLaptopImage(int laptopId, byte[] image);
    List<Laptop> getAll();
}