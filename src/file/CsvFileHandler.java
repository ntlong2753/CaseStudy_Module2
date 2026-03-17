package file;

import model.Student;
import model.StudentStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvFileHandler implements FileHandler {
    private static final String CSV_PATH = "src/data/student.csv";

    @Override
    public ArrayList<Student> read(String path) {
        ArrayList<Student> students = new ArrayList<>();
        File file = new File(CSV_PATH);
        if (!file.exists()) {
            System.out.println("File không tồn tại.");
            return students;
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
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

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    @Override
    public void write(String path, Student student) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CSV_PATH, true))) {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveToFile(String path, List<Student> students) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CSV_PATH))) {
            bufferedWriter.write("id,name,birthDate,email,phone,className,gpa,status");
            bufferedWriter.newLine();
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
