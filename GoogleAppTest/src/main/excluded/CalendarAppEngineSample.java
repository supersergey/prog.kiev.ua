import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
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
 * Created by user on 03.09.2015.
 */
public class CalendarAppEngineSample extends AbstractAppEngineAuthorizationCodeServlet {

    private AuthorizationCodeFlow flow;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            //AuthorizationCodeFlow flow = initializeFlow();
            // AuthorizationCodeFlow flow1;

            // String ClientId = flow.getClientId();
            //Credential credential = flow.loadCredential(ClientId);
            //Credential credential = new GoogleCredential().setAccessToken(accessToken);


//            String userId = UserServiceFactory.getUserService().getCurrentUser().getUserId();
            //Credential credential = Utils.getCredentials();

            SpreadsheetService service =
                    new SpreadsheetService("Diploma");


            Credential googleCredential = flow.loadCredential(UserServiceFactory.getUserService().getCurrentUser().getUserId());

            String accessToken = googleCredential.getAccessToken();

            // credential.refreshToken();
            //service.setUserToken(accessToken);
            service.setOAuth2Credentials(googleCredential);


            URL SPREADSHEET_FEED_URL = new URL(
                    "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
            try
            {
                SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
                List<SpreadsheetEntry> spreadsheets = feed.getEntries();
                Sheets sheets = new Sheets();

                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();

                for (SpreadsheetEntry spreadsheet : spreadsheets) {
                    //sheets.getSheets().add(new Sheet(spreadsheet.getTitle().getPlainText()));
                    writer.write(spreadsheet.getTitle().getPlainText()+"\r\n");
                    // Print the title of this spreadsheet to the screen
                    System.out.println(spreadsheet.getTitle().getPlainText());
                }

                response.setStatus(HttpServletResponse.SC_OK);
                request.setAttribute("list", sheets);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("results.jsp");
                // dispatcher.forward(request, response);
            }
            catch (ServiceException ex)
            {
                System.out.println("Wrong login!");
            }

        }
        catch (IOException ex)
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
        flow = null; //Utils.newFlow();
        return flow;
    }
}
