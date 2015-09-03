import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by st on 03.09.2015.
 */
public class ListSheets extends HttpServlet {
    private static final String APPLICATION_NAME = "Diploma";
    // private static final java.io.File DATA_STORE_DIR =
       //     new java.io.File(".", ".store/oauth2_sample");
    private static FileDataStoreFactory dataStoreFactory;
    private static HttpTransport httpTransport;
    // private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Arrays.asList(
            "https://spreadsheets.google.com/feeds");
    private static Oauth2 oauth2;
    private static GoogleClientSecrets clientSecrets;
    private static Credential credential;

    /*public ListSheets() {
        try{
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(new File("/"));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }*/

    /*private static Credential auth() throws Exception {
        // load client secrets
        clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(ListSheets.class.getResourceAsStream("/client_secrets.json")));
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println("Enter Client ID and Secret from https://code.google.com/apis/console/ "
                    + "into oauth2-cmdline-sample/src/main/resources/client_secrets.json");
            return null;
        }
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(
                dataStoreFactory).build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }*/

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            AppIdentityService appIdentity = AppIdentityServiceFactory.getAppIdentityService();
            String accessToken = appIdentity.getAccessToken(SCOPES).getAccessToken();

            GoogleCredential googleCredential = new GoogleCredential();
            googleCredential.setAccessToken(accessToken);


            /*credential.setAccessToken(accessToken);

            oauth2 = new Oauth2.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(
                    APPLICATION_NAME).build();

                String token = credential.getAccessToken();*/
                SpreadsheetService service =
                        new SpreadsheetService("Diploma");
                service.setOAuth2Credentials(googleCredential);
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
                resp.setStatus(HttpServletResponse.SC_OK);
                req.setAttribute("list", sheets);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("results.jsp");
                dispatcher.forward(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

