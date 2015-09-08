import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

/**
 * Created by user on 08.09.2015.
 */
public class LoadSheetsServlet extends AbstractAuthorizationCodeServlet {

    public final String SPREADSHEETS_FEED_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";
    private AuthorizationCodeFlow flow;
    private SpreadsheetService service;
    Credential googleCredential;
    SpreadsheetFeed feed;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        try {

            service = new SpreadsheetService("Diploma");
            googleCredential = flow.loadCredential("sergey.tolokunsky");
            service.setOAuth2Credentials(googleCredential);

            URL SPREADSHEET_FEED_URL = new URL(SPREADSHEETS_FEED_URL);
            feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
                List<SpreadsheetEntry> spreadsheets = feed.getEntries();
                Sheets sheets = new Sheets();
                for (SpreadsheetEntry spreadsheet : spreadsheets) {
                    sheets.addSheet(new Sheet(spreadsheet.getTitle().getPlainText()));
                }
                response.setStatus(HttpServletResponse.SC_OK);
                request.setAttribute("list", sheets);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/results.jsp");
                dispatcher.forward(request, response);
            }
        catch (ServiceException ex)
        {
            PrintWriter writer = response.getWriter();
            writer.write("Invalid login. Check you google access credentials for getting data from Google Docs.");
            writer.write("<a href=\"/getSpreadSheets\">Return back.</a>");
            writer.flush();
        }
        catch (IOException | ServletException ex)
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
        flow = Utils.newFlow();
        return flow;
    }

    @Override
    protected String getUserId(HttpServletRequest httpServletRequest) throws ServletException, IOException {
        return "sergey.tolokunsky";
    }
}
