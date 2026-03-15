package service;

import model.Student;
import model.StudentStatus;

import java.util.ArrayList;
import java.util.List;

public class StudentManager implements Manager<Student> {
    private List<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
    }

    @Override
    public void add(Student student) {
        students.add(student);
    }

    @Override
    public void remove(String id) {
        Student student = findById(id);
        if (student != null) {
            students.remove(student);
        }
    }

    @Override
    public void update(String id, Student newStudent) {
        Student oldStudent = findById(id);

        if (oldStudent != null) {
            oldStudent.setName(newStudent.getName());
            oldStudent.setBirthDate(newStudent.getBirthDate());
            oldStudent.setEmail(newStudent.getEmail());
            oldStudent.setPhone(newStudent.getPhone());
            oldStudent.setGpa(newStudent.getGpa());
            oldStudent.setStatus(newStudent.getStatus());
        }
    }

    @Override
    public Student findById(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    public List<Student> findByName(String name) {
        List<Student> result = new ArrayList<>();

        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Student> findByStatus(StudentStatus status) {
        List<Student> result = new ArrayList<>();

        for (Student s : students) {
            if (s.getStatus() == status) {
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public List<Student> findAll() {
        return students;
    }
}
