package ua.kiev.prog.DAO;

import java.util.Date;
import java.util.List;

/**
 * Created by sergey on 09.09.2015.
 */
public interface StudentsDAO {
    List<Student> getStudentsByPhoneNumber(String phoneNumber);
    void addStudent(Student student);
    void addAll(List<Student> students);
    Date getLastUpdateDate();
    void setLastUpdateDate();
    void drop();
}
