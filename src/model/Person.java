package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Person {
    protected String id;
    protected String name;
    protected String birthDate;
    protected String email;
    protected String phone;

    public Person() {

    }

    public Person(String id, String name, String birthDate, String email, String phone) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDate = LocalDate.parse(this.birthDate, formatter);
        int age = currentDate.getYear() - birthDate.getYear();
        return age;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %s | Họ tên: %s | Ngày sinh: %s | Tuổi: %d | Email: %s | Phone: %s ",
                id, name, birthDate, getAge(), email, phone
        );
    }
}
