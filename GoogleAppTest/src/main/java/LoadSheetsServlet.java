import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 08.09.2015.
 */
public class LoadSheetsServlet extends AbstractAppEngineAuthorizationCodeServlet {

    public final String SPREADSHEETS_FEED_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";
    private SpreadsheetService service;
    private SpreadsheetFeed feed;
    private StudentsDAO studentsDAO = new StudentsDAOImpl();
    private GoogleAuthorizationCodeFlow flow;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (request.getRequestURI().equals("/")) {
            try {
                Date currentDate = new Date();
                // last update more than 10 seconds ago, simple antiflood protection
                if (currentDate.getTime() - studentsDAO.getLastUpdateDate().getTime() > 10 * 1000)
                {
                    List<SpreadsheetEntry> spreadsheets = loadSpreadsheets();
                    if (null == spreadsheets)
                        throw new IOException();
                    studentsDAO.addAll(getStudentsList(spreadsheets));
                    studentsDAO.setLastUpdateDate();
                }
                response.sendRedirect("/searchpage.jsp");
            } catch (ServiceException | IOException | URISyntaxException ex) {
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
    }

    @Override
    protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
        return Utils.getRedirectUri(req);
    }

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws IOException {
        flow = Utils.newFlow();
        return flow;
    }

    private List<SpreadsheetEntry> loadSpreadsheets() throws ServiceException, IOException {
        String userId = UserServiceFactory.getUserService().getCurrentUser().getUserId();
        Credential credential = Utils.newFlow().loadCredential(userId);
        if (null == credential)
            throw new IOException();

        service = new SpreadsheetService("Diploma");
        service.setOAuth2Credentials(Utils.getGoogleCredenitals());

        URL SPREADSHEET_FEED_URL = new URL(SPREADSHEETS_FEED_URL);
        feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
        return feed.getEntries();
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
