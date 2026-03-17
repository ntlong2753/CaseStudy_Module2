package service;

import file.UserFileHandler;
import model.Account;
import util.EmailValidator;
import util.PhoneValidator;
import util.UsernameValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountManager {
    private List<Account> accounts = new ArrayList<>();
    private UserFileHandler userFileHandler = new UserFileHandler();


    Scanner sc = new Scanner(System.in);

    public AccountManager() {
        this.accounts = userFileHandler.readUsersFromFile();
    }

    // kiểm tra xem tên tài khoản, email, số điện thoại đã tồn tại trong danh sách tài khoản
    // kiểm tra tên
    public Account findByUsername(String username) {
        for (Account acc : accounts) {
            if (acc.getUsername().equalsIgnoreCase(username)) {
                return acc;
            }
        }
        return null;
    }

    // kiểm tra email
    public boolean isEmailExist(String email) {
        for (Account acc : accounts) {
            if (acc.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    // kiểm tra số điện thoại
    public boolean isPhoneExist(String phone) {
        for (Account acc : accounts) {
            if (acc.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    // kiểm tra mật khẩu
    public boolean isPasswordCorrect(String username, String password) {
        Account acc = findByUsername(username);
        if (acc == null) {
            return false;
        }
        return acc.getPassword().equals(password);
    }


    // đăng ký tài khoản
    public boolean register() {
        String email;
        while (true) {
            System.out.print("Nhập email: ");
            email = sc.nextLine();
            // kiểm tra email hợp lệ
            if (!EmailValidator.checkEmail(email)) {
                System.out.println("Email không hợp lệ");
                continue;
            }
            // kiểm tra email tồn tại
            if (isEmailExist(email)) {
                System.out.println("Email đã tồn tại");
                continue;
            }
            break;
        }
        String phone;
        while (true) {
            // số điện thoại
            System.out.print("Nhập số điện thoại: ");
            phone = sc.nextLine();
            // kiểm tra số điện thoại hợp lệ
            if (!PhoneValidator.checkPhone(phone)) {
                System.out.println("Số điện thoại không hợp lệ");
                continue;
            }
            // kiểm tra số điện thoại tồn tại
            if (isPhoneExist(phone)) {
                System.out.println("Số điện thoại đã tồn tại");
                continue;
            }
            break;
        }
        String username;
        while (true) {
            // username
            System.out.print("Nhập username: ");
            username = sc.nextLine();
            // kiểm tra username hợp lệ
            if (!UsernameValidator.checkUsername(username)) {
                System.out.println("Username không hợp lệ");
                continue;
            }
            // kiểm tra username tồn tại
            if (findByUsername(username) != null) {
                System.out.println("Username đã tồn tại");
                continue;
            }
            break;
        }

        // password
        System.out.print("Nhập password: ");
        String password = sc.nextLine();

        Account account = new Account(email, phone, username, password);
        accounts.add(account);
        userFileHandler.writeUsersToFile(accounts);
        System.out.println("Đăng ký thành công");
        return true;
    }

    // đăng nhập
    public boolean login() {
        String username;
        while (true) {
            System.out.print("Nhập username: ");
            username = sc.nextLine();
            // kiểm tra username tồn tại
            if (findByUsername(username) == null) {
                System.out.println("Tài khoản chưa được đăng ký");
                return false;
            }
            break;
        }

        // password
        String password;
        while (true) {
            System.out.print("Nhập password: ");
            password = sc.nextLine();
            // kiểm tra password
            if (!isPasswordCorrect(username, password)) {
                System.out.println("Mật khẩu không đúng");
                continue;
            }
            break;
        }

        Account acc = findByUsername(username);

        if (acc == null) {
            System.out.println("Tài khoản chưa được đăng ký");
            return false;
        }
        return acc.getPassword().equals(password);
    }

    // xóa tài khoản
    public void deleteAccount() {
        String username;
        System.out.print("Nhập username cần xóa: ");
        username = sc.nextLine();
        Account acc = findByUsername(username);
        if (acc != null) {
            accounts.remove(acc);
            // Lưu lại file sau khi xóa
            userFileHandler.writeUsersToFile(accounts);
            System.out.println("Xóa tài khoản thành công");
        } else {
            System.out.println("Tài khoản không tồn tại");
        }
    }

    // hiển thị thông tin tài khoản
    public void displayAccountInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập username: ");
        String username = sc.nextLine();

        Account account = null;
        for (Account acc : accounts) {
            if (acc.getUsername().equalsIgnoreCase(username)) {
                account = acc;
                break;
            }
        }
        checkAccount();
        if (account != null) {
            System.out.println("------ Thông tin tài khoản ------");
            System.out.println("Email: " + account.getEmail());
            System.out.println("Số điện thoại: " + account.getPhone());
            System.out.println("Username: " + account.getUsername());
            System.out.println("Password: " + account.getPassword());

        } else {
            System.out.println("Không tìm thấy tài khoản với username: " + username);
        }
        System.out.println();
    }

    // kiểm tra tài khoản
    public void checkAccount() {
        if (accounts.isEmpty()) {
            System.out.println("Danh sách tài khoản trống");
        }

    }
}
