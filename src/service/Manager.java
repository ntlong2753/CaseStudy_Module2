package service;

import java.util.List;

public interface Manager<Student> {
    void add(Student t);

    void update();

    void delete(String id);

    Student findById(String id);

    List<Student> findAll();
}
