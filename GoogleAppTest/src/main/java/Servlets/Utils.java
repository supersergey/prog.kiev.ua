package Servlets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
// import com.google.appengine.api.search.checkers.Preconditions;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 03.09.2015.
 */
public class Utils {

    private static final AppEngineDataStoreFactory DATA_STORE_FACTORY =
            AppEngineDataStoreFactory.getDefaultInstance();

    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CLIENT_ID = "749895953376-d8muikbmv6okhaaa4cthg68g3pamimd1@developer.gserviceaccount.com";
    private static final URL fileUrl = Utils.class.getResource("/Diploma.p12");
    static GoogleCredential credential;
    private static final String APPLICATION_NAME = "Diploma";
    private static HttpTransport httpTransport;

    private static final List<String> SCOPES = Arrays.asList(
            "https://spreadsheets.google.com/feeds",
            "https://docs.google.com/feeds",
            "https://www.googleapis.com/auth/drive",
            GmailScopes.GMAIL_LABELS);

    /*static {
        try
        {
            DATA_STORE_FACTORY.getDataStore("StoredCredential").clear();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }*/

    static Gmail getGmailService()
    {
        return new Gmail.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(Utils.APPLICATION_NAME).build();
    }

    static GoogleCredential getP12Credentials() throws GeneralSecurityException,
            IOException, URISyntaxException {
        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(CLIENT_ID)
                .setServiceAccountPrivateKeyFromP12File(
                        new File(fileUrl.toURI()))
                //.setClientSecrets(getClientCredential())
                .setServiceAccountScopes(SCOPES).build();
    }

    public static String normalizePhone(String phoneNumber)
    {
        // remove all junk characters from the phone number, like + - . / etc.
        // remove starting two digits, we convert +38 050 xxx xxx xxx into 050 xxx xxx xxx
        phoneNumber = phoneNumber.replaceAll("[^x0-9]", "");
        if (phoneNumber .length()==12)
            phoneNumber = phoneNumber .substring(2);
        return phoneNumber;
    }
}
