package za.ac.cput.service;

import java.util.List;

public interface IService <T, ID> {
    T save (T t);
    T update (T t);
    T read (ID id);
    void delete (ID id);


}
