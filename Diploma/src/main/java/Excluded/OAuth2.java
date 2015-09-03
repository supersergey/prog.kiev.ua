package Excluded;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.*;
import com.google.gdata.data.batch.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 02.09.2015.
 */
public class OAuth2 {



    public static void main(String[] args)
    {
     /*   try
        {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
            Credential credential = authorize();
            oauth2 = new Oauth2.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(
                    APPLICATION_NAME).build();
            if (null!=credential) {
                String token = credential.getAccessToken();
                SpreadsheetService service =
                        new SpreadsheetService("Diploma");
                service.setAuthSubToken(token);
                URL SPREADSHEET_FEED_URL = new URL(
                        "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
                SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
                List<SpreadsheetEntry> spreadsheets = feed.getEntries();
                for (SpreadsheetEntry spreadsheet : spreadsheets) {
                    // Print the title of this spreadsheet to the screen
                    System.out.println(spreadsheet.getTitle().getPlainText());
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
*/
    }

}
