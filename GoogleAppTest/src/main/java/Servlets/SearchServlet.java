package Servlets;

import DAO.Student;
import DAO.StudentsDAO;
import DAO.StudentsDAOImpl;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.ServiceException;
import config.LinkSource;
import config.PriceSource;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
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
public class SearchServlet extends HttpServlet {

    public final String SPREADSHEETS_FEED_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";
    private SpreadsheetService service;
    private SpreadsheetFeed feed;
    private StudentsDAO studentsDAO = new StudentsDAOImpl();
    private static int counter = 0;
    private GoogleCredential credential;

    {
        counter++;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
            String searchMask = request.getParameter("phoneNumber").trim();
            Map<String, String> linksMap = null;
            if (!searchMask.isEmpty())
                linksMap = getSearchResults(searchMask);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("UTF-8");
            if (null != linksMap) {
                request.setAttribute("linksMap", linksMap);
                request.setAttribute("searchResult", "dataIsFound"); // found
            } else {
                request.setAttribute("searchResult", "userNotFound"); // not found
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/searchpage.jsp");
            dispatcher.forward(request, response);
        }
        catch (ServiceException | IOException | URISyntaxException | GeneralSecurityException ex) {
            PrintWriter writer = response.getWriter();
            writer.write("<html>");
            writer.write("<body>");
            writer.write("Something went wrong. Check you google access credentials for getting data from Google Docs.");
            writer.write("<br><a href=\"/\" > Return back.</a>");
            writer.write("</body>");
            writer.write("</html>");
            writer.flush();
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String, String> getSearchResults(String searchMask) {
        StudentsDAO studentsDAO = new StudentsDAOImpl();
        Pattern phonePattern = Pattern.compile("^((\\+\\d{1,3}(-|\\s)?\\(?\\d\\)?(-|\\s)?\\d{1,5})|(\\(?\\d{2,6}\\)?))(-|\\s)?(\\d{3,4})(-|\\s)?(\\d{2})(-|\\s)?(\\d{2})$");
        Matcher phoneMatcher = phonePattern.matcher(searchMask);
        if (phoneMatcher.matches())
            searchMask = Utils.normalizePhone(searchMask);
        List<Student> studentsMatchingPhone = studentsDAO.getStudentsByPhoneNumber(searchMask);

        Map<String, String> linksMap = null;

        if (!studentsMatchingPhone.isEmpty()) {
            linksMap = new HashMap<>();
            for (Student student : studentsMatchingPhone) {
                String studentCourse = student.getCourseName();
                String coursePrice = PriceSource.getPriceByCourse(studentCourse);
                String link = LinkSource.getLinkByCourse(studentCourse);
                String payment = student.getPayment();
                if (null == payment || !payment.equals(coursePrice))
                    linksMap.put(studentCourse, "Для получения ссылки оплатите курс полностью.");
                else
                    linksMap.put(studentCourse, "<a href=\"" + link + "\" target=\"_blank\">Ссылка на видео</a>");
            }
        }
        return linksMap;
    }

    private List<SpreadsheetEntry> loadSpreadsheets() throws ServiceException, IOException, GeneralSecurityException, URISyntaxException {

        /*
        Uncomment this to use to OAuth2 authorization
        flow.loadCredential(Servlets.Utils.getClientCredential().getDetails().getClientId());
         */

        if (null == credential)
            credential = Utils.getP12Credentials();

        if (null == credential)
            throw new IOException();

        if (null == service)
        {
            service = new SpreadsheetService("Diploma");
            service.setOAuth2Credentials(credential);
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
                        + "?min-row=2&min-col=1&max-col=4").toURL();
                CellFeed cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);

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
                        result.add(new Student(x[0], x[1], x[2], x[3], courseName, courseDate, teacherName, location));
                }
            }
        }
        return result;
    }
}
