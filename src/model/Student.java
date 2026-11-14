
package model;

public class Student {

    private String studentId;
    private String name;
    private String department;
    private String email;

    public Student(String studentId, String name,
                   String department, String email) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.email = email;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }
}
