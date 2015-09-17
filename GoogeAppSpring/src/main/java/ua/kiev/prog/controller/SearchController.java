package ua.kiev.prog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.kiev.prog.DAO.Student;
import ua.kiev.prog.DAO.StudentsDAO;
import ua.kiev.prog.DAO.StudentsDAOImpl;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.ServiceException;
import ua.kiev.prog.config.LinkSource;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sergey on 09.09.2015.
 */
@Controller
public class SearchController{

    public final String SPREADSHEETS_FEED_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";
    private SpreadsheetService service;
    private SpreadsheetFeed feed;
    @Autowired
    private StudentsDAO studentsDAO;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView doSearch(HttpServletResponse response, @RequestParam(value="phoneNumber") String searchMask)  {
        try {
            Date currentDate = new Date();
            // last update more than 1 minute ago, simple antiflood protection
            if (currentDate.getTime() - studentsDAO.getLastUpdateDate().getTime() > 60 * 1000) {
                List<SpreadsheetEntry> spreadsheets = loadSpreadsheets();
                if (null == spreadsheets)
                    throw new IOException();
                studentsDAO.addAll(getStudentsList(spreadsheets));
                studentsDAO.setLastUpdateDate();
            }
            List<Student> studentsMatchingPhone;
            if (!searchMask.isEmpty())
                studentsMatchingPhone = getSearchResults(searchMask);
            else
                throw new IOException();

            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("UTF-8");

            ModelAndView result = new ModelAndView("searchpage", "studentsList", studentsMatchingPhone);
            StringBuilder adminMessage = new StringBuilder();

            if (!studentsMatchingPhone.isEmpty()) {
                Integer mailId = MailStack.addNewEntry(studentsMatchingPhone);
                result.addObject("searchResult", "dataIsFound");
                result.addObject("mailId", mailId); // found
                for (Student s : studentsMatchingPhone)
                    if (s.getUrl().isEmpty())
                        adminMessage.append(String.format(Utils.MESSAGE_IS_NOT_SENT, s.getCourseName(), s.getLocation(), s.getTeacherName(), s.getStartDate(), s.getName(), s.getPhone())).append("\r\n");
                else
                    adminMessage.append(String.format(Utils.MESSAGE_SENT_TO_PHONE, s.getCourseName(), s.getLocation(), s.getTeacherName(), s.getStartDate(), s.getName(), s.getPhone())).append("\r\n");
                try
                {
                    Utils.sendAdminEmail(studentsMatchingPhone.get(0).getPhone(), adminMessage.toString());
                }
                catch (MessagingException ignored) {}
            } else {
                result.addObject("searchResult", "userNotFound"); // not found
            }
            return result;
        }
        catch (ServiceException | IOException | URISyntaxException | GeneralSecurityException ex) {
            try
            {
                PrintWriter writer = response.getWriter();
                writer.write("<html>");
                writer.write("<body>");
                writer.write("Something went wrong. Check you google access credentials for getting data from Google Docs.");
                writer.write("<br><a href=\"/\" > Return back.</a>");
                writer.write("</body>");
                writer.write("</html>");
                writer.flush();
            }
            catch (IOException ignored) {}
                throw new UnknownMatchException(searchMask);
            }
    }

    private List<Student> getSearchResults(String searchMask) {

        Pattern phonePattern = Pattern.compile("^((\\+\\d{1,3}(-|\\s)?\\(?\\d\\)?(-|\\s)?\\d{1,5})|(\\(?\\d{2,6}\\)?))(-|\\s)?(\\d{3,4})(-|\\s)?(\\d{2})(-|\\s)?(\\d{2})$");
        Matcher phoneMatcher = phonePattern.matcher(searchMask);
        if (phoneMatcher.matches())
            searchMask = Utils.normalizePhone(searchMask);
        List<Student> studentsMatchingPhone = studentsDAO.getStudentsByPhoneNumber(searchMask);

        if (!studentsMatchingPhone.isEmpty()) {
            for (Student student : studentsMatchingPhone) {
                String studentCourse = student.getCourseName();
                String coursePrice = student.getCoursePrice();
                String payment = student.getPayment();
                if (null == payment || !payment.equals(coursePrice))
                    student.setUrl("");
                else
                    student.setUrl(LinkSource.getLinkByCourse(studentCourse));
            }
        }
        return studentsMatchingPhone;
    }

    private List<SpreadsheetEntry> loadSpreadsheets() throws ServiceException, IOException, GeneralSecurityException, URISyntaxException {

        if (null == Utils.credential)
            Utils.credential = Utils.getP12Credentials();

        if (null == Utils.credential)
            throw new IOException();

        if (null == service)
        {
            service = new SpreadsheetService("Diploma");
            service.setOAuth2Credentials(Utils.credential);
        }

        // this is the most time-consuming operation, may take 10-15 seconds
        // it only need to be done if new spreadsheets are assigned
        // this won't happen frequently, so we update studentsDB once per hour

        Date currentDate = new Date();
        if (currentDate.getTime() - studentsDAO.getLastUpdateDate().getTime() > 60 * 60 * 1000) {

            URL SPREADSHEET_FEED_URL = new URL(SPREADSHEETS_FEED_URL);
            feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
        }
        if (null!=feed)
            return feed.getEntries();
        else
            return null;
    }

    private List<Student> getStudentsList(List<SpreadsheetEntry> spreadsheets) throws IOException, ServiceException, URISyntaxException {
        List<Student> result = new LinkedList<>();
        studentsDAO.drop();

        for (SpreadsheetEntry spreadsheet : spreadsheets) {
            String sheetName = spreadsheet.getTitle().getPlainText().toLowerCase();
            if (sheetName.startsWith("java") || sheetName.startsWith("android")) // process only the spreadsheets names starting with Java or Android
            {
                WorksheetFeed worksheetFeed = service.getFeed(
                        spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
                WorksheetEntry worksheet = worksheetFeed.getEntries().get(0); // retrieve the first worksheet

                URL cellFeedUrl = new URI(worksheet.getCellFeedUrl().toString()
                        + "?min-row=1&max-row=5&min-col=5&max-col=5").toURL();
                CellFeed cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);

                String coursePrice = "";

                for (CellEntry cell : cellFeed.getEntries()) {
                    coursePrice = cell.getCell().getValue();
                }


                cellFeedUrl = new URI(worksheet.getCellFeedUrl().toString()
                        + "?min-row=2&min-col=1&max-col=4").toURL();
                cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);

                String[][] content = new String[cellFeed.getEntries().size()][4];
                for (CellEntry cell : cellFeed.getEntries()) {
                    String cellPosition = cell.getId();
                    cellPosition = cellPosition.substring(cellPosition.lastIndexOf("/") + 1); // cellposition like R2C5 = row 2, column 5
                    Integer row = Integer.parseInt(cellPosition.substring(1, cellPosition.indexOf("C")));
                    Integer column = Integer.parseInt(cellPosition.substring(cellPosition.indexOf("C") + 1));
                    content[row - 2][column - 1] = cell.getCell().getValue();
                }

                // Java Start, 09.09.15 (ИмяПрепода, Локация)
                String cousreMetaData = spreadsheet.getTitle().getPlainText();
                String courseName = cousreMetaData.substring(0, cousreMetaData.indexOf(","));
                String teacherName = cousreMetaData.substring(cousreMetaData.indexOf("(") + 1, cousreMetaData.lastIndexOf(","));
                String location = cousreMetaData.substring(cousreMetaData.lastIndexOf(",") + 1, cousreMetaData.length() - 1);
                String courseDate = "";
                Pattern datePattern = Pattern.compile("(\\d\\d\\.*){3}"); // catches date, should be in dd.mm.yy format, e.g. 09.09.2015
                Matcher m = datePattern.matcher(cousreMetaData);
                if (m.find())
                    courseDate = cousreMetaData.substring(m.start(), m.end());

                for (String[] x : content) {
                    if (null != x[0] || null != x[1] || null != x[2] || null != x[3]) // do not include empty rows
                        result.add(new Student(x[0], x[1], x[2], x[3], courseName, courseDate, teacherName, location, coursePrice, ""));
                }
            }
        }
        return result;
    }
}
