package za.ac.cput.service;

public interface IService <T, ID> {
    T save (T t);
    T update (T t);
    T read (ID id);
    void delete (ID id);
}
