package file;

import model.Student;
import model.StudentStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TxtFileHandler implements FileHandler {

    @Override
    public ArrayList<Student> read(String path) {
        ArrayList<Student> students = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File không tồn tại.");
            return students;
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
//                if (parts.length == 9) {
//
//                }
                Student std = new Student(parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        parts[4].trim(),
                        parts[5].trim(),
                        Double.parseDouble(parts[6].trim()),
                        StudentStatus.valueOf(parts[8].trim()));
                students.add(std);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    @Override
    public void write(String path, Student student) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true))) {
            String data = student.getId() + ", " +
                    student.getName() + ", " +
                    student.getBirthDate() + ", " +
                    student.getEmail() + ", " +
                    student.getPhone() + ", " +
                    student.getClassName() + ", " +
                    student.getGpa() + ", " +
                    student.getAcademicRank() + ", " +
                    student.getStatus();
            bufferedWriter.write(data);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveToFile(String path, List<Student> students) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            for (Student student : students) {
                String data = student.getId() + "," +
                        student.getName() + "," +
                        student.getBirthDate() + "," +
                        student.getEmail() + "," +
                        student.getPhone() + "," +
                        student.getClassName() + "," +
                        student.getGpa() + "," +
                        student.getAcademicRank() + "," +
                        student.getStatus();
                bufferedWriter.write(data);
                bufferedWriter.newLine();
            }
            System.out.println("Lưu thông tin thành công");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
