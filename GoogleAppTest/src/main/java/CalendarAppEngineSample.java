import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.ServiceException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by user on 03.09.2015.
 */
public class CalendarAppEngineSample extends AbstractAppEngineAuthorizationCodeServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            AuthorizationCodeFlow flow = initializeFlow();
            Credential googleCredential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
            SpreadsheetService service =
                    new SpreadsheetService("Diploma");
            service.setOAuth2Credentials(googleCredential);
            String accessToken = googleCredential.getAccessToken();
            service.setAuthSubToken(accessToken);
            URL SPREADSHEET_FEED_URL = new URL(
                    "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
            SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
            List<SpreadsheetEntry> spreadsheets = feed.getEntries();
            Sheets sheets = new Sheets();
            for (SpreadsheetEntry spreadsheet : spreadsheets) {
                sheets.getSheets().add(new Sheet(spreadsheet.getTitle().getPlainText()));
                // Print the title of this spreadsheet to the screen
//                        System.out.println(spreadsheet.getTitle().getPlainText());
            }
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            request.setAttribute("list", sheets);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("results.jsp");
            dispatcher.forward(request, response);
        }
        catch (ServletException | ServiceException ex)
        {
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
        return Utils.getRedirectUri(req);
    }

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws IOException {
        return Utils.newFlow();
    }
}
