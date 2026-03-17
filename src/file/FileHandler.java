package file;

import model.Student;

import java.util.List;

public interface FileHandler {
    public abstract List<Student> read(String path);

    public abstract void write(String path, Student student);

    public abstract void saveToFile(String path, List<Student> student);
}