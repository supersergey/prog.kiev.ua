/**
 * Created by user on 09.09.2015.
 */
public class Student {
    private String name;
    private String phone;
    private String payment;
    private String comment;
    private String courseName;
    private String startDate;
    private String teacherName;
    private String location;

    public Student() {
    }

    public Student(String name, String phone, String payment, String comment, String courseName, String startDate, String teacherName, String location) {
        this.name = name;
        this.phone = phone;
        this.payment = payment;
        this.comment = comment;
        this.courseName = courseName;
        this.startDate = startDate;
        this.teacherName = teacherName;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
