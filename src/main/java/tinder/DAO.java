package tinder;

import java.util.List;

public interface DAO<T> {
    List<T> getAll();
    void add(T item);
    T getByID(int id);
}
