package ua.kiev.prog.controller;

import ua.kiev.prog.DAO.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by st on 11.09.2015.
 */
public class MailStack {
    private static Integer counter = 0;
    private static Map<Integer, List<Student>> mails = new HashMap<>();

    public static int addNewEntry(List<Student> students)
    {
        if (counter >= 100) // we don't need too many sent emails, let's overwrite the old ones
            counter = 0;

        mails.put(++counter, students);
        return counter;
    }

    public static List<Student> getEntryById(Integer id)
    {
        List<Student> result = mails.get(id);
        mails.remove(id);
        return result;
    }
}

