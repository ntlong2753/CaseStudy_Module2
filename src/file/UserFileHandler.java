package file;

import model.Account;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserFileHandler {
    private static final String USER_FILE_PATH = "src/data/users.txt";

    public List<Account> readUsersFromFile() {

        List<Account> accounts = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USER_FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                Account account = new Account(data[0], data[1], data[2], data[3]);
                accounts.add(account);
            }

        } catch (IOException e) {
            System.out.println("loi file");
        }
        return accounts;
    }

    public void writeUsersToFile(List<Account> accounts) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(USER_FILE_PATH))) {
            for (Account account : accounts) {
                String data = account.getEmail() + "," +
                        account.getPhone() + "," +
                        account.getUsername() + "," +
                        account.getPassword();
                bufferedWriter.write(data);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("loi file");
        }
    }
}
