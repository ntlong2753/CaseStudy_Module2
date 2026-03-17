package service;

import file.CsvFileHandler;
import file.FileHandler;
import file.TxtFileHandler;
import model.Account;
import model.Student;
import model.StudentStatus;
import util.EmailValidator;
import util.IdValidator;
import util.PhoneValidator;

import java.util.*;

public class AdminManager implements Manager<Student> {
    private List<Student> students;
    private List<Account> accounts = new ArrayList<>();

    private FileHandler txtFileHandler = new TxtFileHandler();
    private FileHandler csvFileHandler = new CsvFileHandler();
    private static final String FILE_PATH = "src/data/student.txt";

    public AdminManager() {
        students = txtFileHandler.read(FILE_PATH);
    }

    // ---------- kiểm tra số điện thoại tồn tại ----------
    public boolean isPhoneExist(String phone) {
        for (Account acc : accounts) {
            if (acc.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    // ---------- kiểm tra email tồn tại ----------
    public boolean isEmailExist(String email) {
        for (Account acc : accounts) {
            if (acc.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    // ---------- kiểm tra id tồn tại ----------
    public boolean isIdExist(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    // ---------- thêm sinh viên ----------
    @Override
    public void add(Student student) {
        Scanner sc = new Scanner(System.in);
        System.out.println("------ Thêm sinh viên mới ------");

        String id;
        while (true) {
            System.out.print("Id: ");
            id = sc.nextLine();
            if (isIdExist(id)) {
                System.out.println("Id đã tồn tại");
                continue;
            }
            if (!IdValidator.checkId(id)) {
                System.out.println("Id không hợp lệ");
                continue;
            }
            break;
        }

        System.out.print("Họ và tên: ");
        String name = sc.nextLine();

        String email;
        while (true) {
            System.out.print("Email: ");
            email = sc.nextLine();
            if (!EmailValidator.checkEmail(email)) {
                System.out.println("Email không hợp lệ");
                continue;
            }
            if (isEmailExist(email)) {
                System.out.println("Email đã tồn tại");
                continue;
            }
            break;
        }

        String phone;
        while (true) {
            System.out.print("Số điện thoại: ");
            phone = sc.nextLine();
            if (!PhoneValidator.checkPhone(phone)) {
                System.out.println("Số điện thoại không hợp lệ");
                continue;
            }
            if (isPhoneExist(phone)) {
                System.out.println("Số điện thoại đã tồn tại");
                continue;
            }
            break;
        }

        System.out.print("Ngày sinh (dd-MM-yyyy): ");
        String birthDate = sc.nextLine();

        String className;
        while (true) {
            System.out.print("Lớp: ");
            className = sc.nextLine();
            if (!className.matches(className)) {
                System.out.println("Lớp không hợp lệ");
                continue;
            }
            break;
        }

        double gpa;
        while (true) {
            System.out.print("GPA: ");
            try {
                gpa = Double.parseDouble(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("GPA không hợp lệ. Vui lòng nhập lại một số.");
            }
        }// trạng thái
        StudentStatus status;
        while (true) {
            System.out.print("Trạng thái (STUDYING/GRADUATED/SUSPENDED/DROPPED_OUT): ");
            String statusInput = sc.nextLine();
            try {
                status = StudentStatus.valueOf(statusInput.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Trạng thái không hợp lệ. Vui lòng nhập: STUDYING, GRADUATED, SUSPENDED hoặc DROPPED_OUT.");
            }
        }

        Student newStudent = new Student(id, name, birthDate, email, phone, className, gpa, status);
        students.add(newStudent);
        txtFileHandler.saveToFile(FILE_PATH, students);
        System.out.println("Thêm sinh viên thành công!");
    }

    // ---------- xóa sinh viên ----------
    @Override
    public void delete(String id) {
        Student student = findById(id);
        if (student != null) {
            students.remove(student);
            txtFileHandler.saveToFile(FILE_PATH, students);
        }
        System.out.println("Xóa sinh viên thành công");
    }

    // ---------- cập nhật thông tin sinh viên ----------
    @Override
    public void update() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập ID sinh viên cần cập nhật: ");
        String id = sc.nextLine();

        Student student = findById(id);

        if (student == null) {
            System.out.println("Không tìm thấy sinh viên");
            return;
        }

        System.out.println("Để trống nếu muốn giữ nguyên thông tin");

        System.out.print("Họ và tên mới: ");
        String name = sc.nextLine();
        if (!name.isEmpty()) {
            student.setName(name);
        }

        System.out.print("Email mới: ");
        String email = sc.nextLine();
        if (!email.isEmpty()) {
            if (EmailValidator.checkEmail(email) && !isEmailExist(email)) {
                student.setEmail(email);
            } else {
                System.out.println("Email không hợp lệ hoặc đã tồn tại");
            }
        }

        System.out.print("Số điện thoại mới: ");
        String phone = sc.nextLine();
        if (!phone.isEmpty()) {
            if (PhoneValidator.checkPhone(phone) && !isPhoneExist(phone)) {
                student.setPhone(phone);
            } else {
                System.out.println("Số điện thoại không hợp lệ hoặc đã tồn tại");
            }
        }

        System.out.print("Ngày sinh mới: ");
        String birthDate = sc.nextLine();
        if (!birthDate.isEmpty()) {
            student.setBirthDate(birthDate);
        }

        System.out.print("GPA mới: ");
        String gpaInput = sc.nextLine();
        if (!gpaInput.isEmpty()) {
            try {
                double gpa = Double.parseDouble(gpaInput);
                student.setGpa(gpa);
            } catch (NumberFormatException e) {
                System.out.println("GPA không hợp lệ");
            }
        }

        txtFileHandler.saveToFile(FILE_PATH, students);
        System.out.println("Cập nhật thành công");

    }

    // cập nhật trạng thái sinh viên
    public void updateStatus() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập ID sinh viên cần cập nhật: ");
        String id = sc.nextLine();
        Student student = findById(id);
        if (student == null) {
            System.out.println("Không tìm thấy sinh viên");
            return;
        }
        System.out.print("Trạng thái mới (STUDYING/GRADUATED/SUSPENDED/DROPPED_OUT): ");
        String statusInput = sc.nextLine();
        if (!statusInput.isEmpty()) {
            try {
                StudentStatus status = StudentStatus.valueOf(statusInput.toUpperCase());
                student.setStatus(status);
            } catch (IllegalArgumentException e) {
                System.out.println("Trạng thái không hợp lệ");
            }
        }
    }

    // kiểm tra danh sách sinh viên
    public void checkStudent() {
        if (students.isEmpty()) {
            System.out.println("Danh sách sinh viên trống");
        }
    }

    // tìm kiếm sinh vin theo id
    @Override
    public Student findById(String id) {
        checkStudent();
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    // tìm kiếm sinh viên theo tên
    public List<Student> findByName(String name) {
        List<Student> result = new ArrayList<>();
        checkStudent();

        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                result.add(s);
            }
        }
        return result;
    }

    // tìm kiếm sinh viên theo trạng thái
    public List<Student> findByStatus(StudentStatus status) {
        List<Student> result = new ArrayList<>();
        checkStudent();

        for (Student s : students) {
            if (s.getStatus() == status) {
                result.add(s);
            }
        }
        return result;
    }

    // tìm kiếm theo gpa
    public List<Student> findByGpa(double gpa) {
        List<Student> result = new ArrayList<>();
        checkStudent();

        for (Student s : students) {
            if (s.getGpa() == gpa) {
                result.add(s);
            }
        }
        return result;

    }

    @Override
    public List<Student> findAll() {
        checkStudent();

        System.out.println("------ Danh sách sinh viên ------");
        System.out.printf("%-5s %-20s %-15s %-25s %-15s %-5s %-5s %-15s %-15s\n",
                "Id", "Họ tên", "Ngày sinh", "Emai", "Số điện thoại", "Lớp", "GPA", "Học lực", "Trạng thái");
        for (Student s : students) {
            System.out.printf("%-5s %-20s %-15s %-25s %-15s %-5s %-5s %-15s %-15s\n",
                    s.getId(), s.getName(), s.getBirthDate(), s.getEmail(), s.getPhone(), s.getClassName(), s.getGpa(), s.getAcademicRank(), s.getStatus());
        }
        return students;
    }

    // phân loại sinh viên
    // gpa tăng dần
    public void sortGpaAscending() {
        checkStudent();

        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Double.compare(s1.getGpa(), s2.getGpa());
            }
        });
    }

    // gpa giảm dần
    public void sortGpaDescending() {
        checkStudent();

        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Double.compare(s2.getGpa(), s1.getGpa());
            }
        });
    }

}
