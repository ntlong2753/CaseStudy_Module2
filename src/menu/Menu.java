package menu;

import model.Student;
import service.StudentManager;

import service.AccountManager;

import java.util.Scanner;

public class Menu {
    Scanner sc = new Scanner(System.in);
    StudentManager std = new StudentManager();
    AccountManager acc = new AccountManager();

    public void menuManager() {
        int choice;
        do {
            System.out.println("------MENU QUẢN LÝ SINH VIÊN------");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Tìm sinh viên");
            System.out.println("3. Hiển thị thông tin các sinh viên");
            System.out.println("4. Cập nhật thông tin sinh viên");
            System.out.println("5. Xóa thông tin sinh viên");
            System.out.println("6. Thay đổi trạng thái sinh viên");
            System.out.println("7. Phân loại sinh viên");
            System.out.println("8. Thông tin tài khoản");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        std.add(new Student());
                        break;
                    case 2:
                        searchStudent();
                        break;
                    case 3:
                        std.findAll();
                        break;
                    case 4:
                        std.update();
                        break;
                    case 5:
                        System.out.print("Nhập ID sinh viên cần xóa: ");
                        String id = sc.nextLine();
                        std.delete(id);
                        break;
                    case 6:
                        std.updateStatus();
                        break;
                    case 7:
                        sortStudent();
                        break;
                    case 8:
                        acc.displayAccountInfo();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập số từ 0 đến 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên.");
                choice = -1;
            }
            System.out.println();
        } while (true);
    }

    public void searchStudent() {
        System.out.println("------Danh sách chọn------");
        System.out.println("1. Tìm kiếm theo id");
        System.out.println("2. Tìm kiếm theo tên");
        System.out.println("3. Tìm kiếm theo trạng thái");
        System.out.println("4. Tìm kiếm theo gpa");
        System.out.println("0. Quay lại");
        int choice;
        do {
            try {
                System.out.print("Chọn: ");
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        System.out.print("Nhập ID: ");
                        String id = sc.nextLine();
                        Student student = std.findById(id);
                        if (student != null) {
                            System.out.println(student);
                        } else {
                            System.out.println("Không tìm thấy sinh viên với ID: " + id);
                        }
                        return;
                    case 2:
                        System.out.print("Nhập tên: ");
                        String name = sc.nextLine();
                        var students = std.findByName(name);
                        if (students.isEmpty()) {
                            System.out.println("Không tìm thấy sinh viên với tên: " + name);
                        } else {
                            for (Student s : students) {
                                System.out.println(s);
                            }
                        }
                        return;
                    case 3:
                        System.out.print("Nhập trạng thái (STUDYING/GRADUATED/SUSPENDED/DROPPED_OUT): ");
                        String statusInput = sc.nextLine();
                        try {
                            model.StudentStatus status = model.StudentStatus.valueOf(statusInput.toUpperCase());
                            var result = std.findByStatus(status);
                            if (result.isEmpty()) {
                                System.out.println("Không tìm thấy sinh viên với trạng thái: " + status);
                            } else {
                                for (Student s : result) {
                                    System.out.println(s);
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Trạng thái không hợp lệ.");
                        }
                        return;
                    case 4:
                        System.out.print("Nhập gpa: ");
                        double gpa = Double.parseDouble(sc.nextLine());
                        std.findByGpa(gpa);
                    case 0:
                        return; // Quay lại menu chính
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập số từ 0 đến 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên.");
                choice = -1;
            }
        } while (true);
    }

    public void menuLogIn() {
        int choice;
        do {
            System.out.println("Đăng nhập hoặc đăng ký");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký");
            System.out.println("3. Xóa tài khoản");
            System.out.println("0. Thoát");
            try {
                System.out.print("Chọn: ");
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        if (acc.login()) {
                            menuManager();
                        }
                        break;
                    case 2:
                        acc.register();
                        break;
                    case 3:
                        acc.deleteAccount();
                        break;
                    case 0:
                        System.out.println("Đã thoát chương trình.");
                        System.exit(0); // Thoát khỏi phương thức và kết thúc chương trình
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên.");
                choice = -1;
            }
        } while (true);
    }

    // phân loại sinh viên
    public void sortStudent() {
        int choice;
        do {
            System.out.println("------Danh sách chọn------");
            System.out.println("1. Sắp xếp theo gpa tăng dần");
            System.out.println("2. Sắp xếp theo gpa giảm dần");
            System.out.println("0. Quay lại");
            try {
                System.out.print("Chọn: ");
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        std.sortGpaAscending();
                        std.findAll();
                        break;
                    case 2:
                        std.sortGpaDescending();
                        std.findAll();
                        break;
                    case 0:
                        return; // Quay lại menu chính
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên.");
                choice = -1;
            }
        } while (true);
    }
}
