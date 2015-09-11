package DAO;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 09.09.2015.
 */
public class StudentsDB {

    public Date lastUpdateDate = new Date(0L);

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
}
