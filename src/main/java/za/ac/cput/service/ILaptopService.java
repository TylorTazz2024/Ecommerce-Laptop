package za.ac.cput.service;

import za.ac.cput.domain.Laptop;

import java.util.List;

public interface ILaptopService {

    Laptop create(Laptop laptop);

    Laptop read(Integer integer);

    Laptop update(Laptop laptop);

    void delete(Integer laptopId);

    List<Laptop> getAll();
}
