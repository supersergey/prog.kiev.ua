import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by user on 03.09.2015.
 */
public class CalendarAppEngineSampleST extends AbstractAppEngineAuthorizationCodeServlet {
    private AuthorizationCodeFlow flow;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter writer = response.getWriter();
        writer.write("Hello hello hello");

        /*try {

            String token = flow.getCredentialDataStore().get(clientSecret).getAccessToken();

            SpreadsheetService service =
                    new SpreadsheetService("Diploma");

            URL SPREADSHEET_FEED_URL = new URL(
                    "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
            service.setAuthSubToken(token);
            SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
            List<SpreadsheetEntry> spreadsheets = feed.getEntries();
            for (SpreadsheetEntry spreadsheet : spreadsheets) {
                // Print the title of this spreadsheet to the screen
                System.out.println(spreadsheet.getTitle().getPlainText());
            }
        }
        catch (ServiceException ex)
        {
            ex.printStackTrace();
        }*/
    }

    @Override
    protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
        return Utils.getRedirectUri(req);
    }

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws IOException {
        flow = null; // Utils.newFlow();
        return flow;
    }
}
