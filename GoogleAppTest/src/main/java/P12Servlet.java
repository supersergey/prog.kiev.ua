import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;

/**
 * Created by user on 10.09.2015.
 */
public class P12Servlet extends HttpServlet {
    public final String SPREADSHEETS_FEED_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try
        {
            GoogleCredential credential = Utils.getP12Credentials();
            SpreadsheetService service = new SpreadsheetService("Diploma");
            service.setOAuth2Credentials(credential);

            URL SPREADSHEET_FEED_URL = new URL(SPREADSHEETS_FEED_URL);
            SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
            List<SpreadsheetEntry> spreadsheetEntries = feed.getEntries();
            for (SpreadsheetEntry entry : spreadsheetEntries)
            {
                System.out.println(entry.getTitle().getPlainText());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

}
