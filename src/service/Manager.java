package service;

import java.util.List;

public interface Manager<T> {
    void add(T t);
    void remove(String id);
    void update(String id, T t);
    T findById(String id);
    List<T> findAll();

}
