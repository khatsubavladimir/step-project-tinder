package tinder;

import java.util.List;

public interface DAO<T> {
    List<T> getAllProfiles();
    void addProfile(T item);

    T getNextProfile();
}
