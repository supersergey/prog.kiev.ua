package ua.kiev.prog.DAO;

import ua.kiev.prog.controller.Utils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sergey on 09.09.2015.
 */
public class StudentsDAOImpl implements StudentsDAO{

    private StudentsDB students = StudentsDB.getInstance();

    @Override
    public List<Student> getStudentsByPhoneNumber(String phoneNumber)
    {
        List<Student> result = new LinkedList<>();
        for (Student student : students.getStudents())
        {
            String studentPhoneNumber = student.getPhone();
            studentPhoneNumber = Utils.normalizePhone(studentPhoneNumber);
            if (studentPhoneNumber.equals(phoneNumber))
                result.add(student);
        }
        return result;
    }

    @Override
    public void addStudent(Student student)
    {
        students.getStudents().add(student);
    }

    @Override
    public void addAll(List<Student> newStudents) {
        students.getStudents().addAll(newStudents);
    }

    @Override
    public Date getLastUpdateDate() {
        return students.lastUpdateDate;
    }

    @Override
    public void setLastUpdateDate() {
        students.lastUpdateDate = new Date();
    }

    @Override
    public void drop() {
        students.getStudents().clear();
    }
}
