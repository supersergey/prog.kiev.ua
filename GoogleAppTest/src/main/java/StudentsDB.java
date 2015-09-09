import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 09.09.2015.
 */
public class StudentsDB {
    private static StudentsDB ourInstance = new StudentsDB();

    public static StudentsDB getInstance() {
        return ourInstance;
    }

    private StudentsDB() {
    }

    private List<Student> students = new LinkedList<>();;

    public List<Student> getStudents()
    {
        return students;
    }

    public void addStudent(Student student)
    {
        students.add(student);
    }

    public List<Student> getStudentsByPhoneNumber(String phoneNumber)
    {
        List<Student> result = new LinkedList<>();
        for (Student student : students)
        {
            String studentPhoneNumber = student.getPhone();
            studentPhoneNumber = Utils.normalizePhone(studentPhoneNumber);
            if (studentPhoneNumber.equals(phoneNumber))
                result.add(student);
        }
        return result;
    }
}
