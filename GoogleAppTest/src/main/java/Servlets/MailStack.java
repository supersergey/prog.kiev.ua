package Servlets;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by st on 11.09.2015.
 */
public class MailStack {
    private static Integer counter = 0;
    private static Map<Integer, MailEntry> mails = new HashMap<>();

    static int addNewEntry(Map<String, String> linksMap)
    {
        if (counter >= 100) // we don't need too many sent emails, let's overwrite the old ones
            counter = 0;

        MailEntry me = new MailEntry(linksMap);

        mails.put(++counter, me);
        return counter;
    }

    static MailEntry getEntryById(Integer id)
    {
        MailEntry result = mails.get(id);
        mails.remove(id);
        return result;
    }

}

class MailEntry
{
    // key = course name, value = url
    private Map<String, String> courses = new HashMap<>();

    public MailEntry(Map<String, String> courses) {
        this.courses = courses;
    }

    public Map<String, String> getCourses() {
        return courses;
    }
}