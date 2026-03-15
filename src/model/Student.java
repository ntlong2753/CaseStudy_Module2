package model;

public class Student extends Person {
    private double gpa;
    private String academicRank;
    private StudentStatus status;

    public Student() {

    }

    public Student(String id, String name, String birthDate, String email, String phone, double gpa, StudentStatus status) {
        super(id, name, birthDate, email, phone);
        this.gpa = gpa;
        this.status = status;
        classify();
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
        classify();
    }

    public String getAcademicRank() {
        return academicRank;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public void classify() {
        if (gpa >= 3.6) {
            academicRank = "Xuất sắc";
        } else if (gpa >= 3.2) {
            academicRank = "Giỏi";
        } else if (gpa >= 2.5) {
            academicRank = "Khá";
        } else if (gpa >= 2.0) {
            academicRank = "Trung bình";
        } else {
            academicRank = "Yếu";
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
                "Điểm trung bình: %.2f | Hạng học lực: %s | Trạng thái: %s",
                gpa, academicRank, status.toString()
        );
    }
}
