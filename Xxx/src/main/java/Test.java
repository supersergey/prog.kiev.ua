import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 07.09.2015.
 */
public class Test extends HttpServlet {

    private static final List<String> SCOPES = Arrays.asList(
            "https://spreadsheets.google.com/feeds",
            "https://docs.google.com/feeds",
            "https://www.googleapis.com/auth/drive");


    private static GoogleCredential getCredentials() throws GeneralSecurityException,
            IOException, URISyntaxException {
        JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        HttpTransport httpTransport = GoogleNetHttpTransport
                .newTrustedTransport();

        String CLIENT_ID = "749895953376-d8muikbmv6okhaaa4cthg68g3pamimd1@developer.gserviceaccount.com";
        URL fileUrl = Test.class.getResource("/Diploma.p12");


        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(CLIENT_ID)
                .setServiceAccountPrivateKeyFromP12File(
                        new File(fileUrl.toURI()))
                        //.setClientSecrets(getClientCredential())
                .setServiceAccountScopes(SCOPES).build();

        return credential;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
        {
            GoogleCredential googleCredential = getCredentials();

            SpreadsheetService service =
                    new SpreadsheetService("Diploma");

            service.setOAuth2Credentials(googleCredential);


            URL SPREADSHEET_FEED_URL = new URL(
                    "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
            SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
            List<SpreadsheetEntry> spreadsheets = feed.getEntries();
            Sheets sheets = new Sheets();

            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();

            for (SpreadsheetEntry spreadsheet : spreadsheets) {
                //sheets.getSheets().add(new Sheet(spreadsheet.getTitle().getPlainText()));
                writer.write(spreadsheet.getTitle().getPlainText()+"\r\n");
                writer.write("\r\n");
                // Print the title of this spreadsheet to the screen
                System.out.println(spreadsheet.getTitle().getPlainText());
            }

            response.setStatus(HttpServletResponse.SC_OK);
            request.setAttribute("list", sheets);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("results.jsp");
            // dispatcher.forward(request, response);
        }
        catch (ServiceException | URISyntaxException | GeneralSecurityException ex)
        {
            System.out.println("Wrong login!");
        }
    }
}
