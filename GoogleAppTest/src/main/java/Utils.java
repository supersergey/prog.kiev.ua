import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
// import com.google.appengine.api.search.checkers.Preconditions;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 03.09.2015.
 */
public class Utils {

    private static final AppEngineDataStoreFactory DATA_STORE_FACTORY =
            AppEngineDataStoreFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    static final HttpTransport HTTP_TRANSPORT =
            new UrlFetchTransport();

    /** Global instance of the JSON factory. */
    static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    private static GoogleClientSecrets clientSecrets = null;

    private static Credential googleCredenitals = null;

    private static final List<String> SCOPES = Arrays.asList(
            "https://spreadsheets.google.com/feeds",
            "https://docs.google.com/feeds",
            "https://www.googleapis.com/auth/drive");


    static {
        try
        {
            DATA_STORE_FACTORY.getDataStore("StoredCredential").clear();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    static String getRedirectUri(HttpServletRequest req) {
        GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath("/oauth2callback");
        return url.build();
    }

    static GoogleAuthorizationCodeFlow newFlow() throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                getClientCredential(), SCOPES).setDataStoreFactory(
                DATA_STORE_FACTORY).setAccessType("offline").build();
    }

    static GoogleClientSecrets getClientCredential() throws IOException {
        if (clientSecrets == null) {
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                    new InputStreamReader(Utils.class.getResourceAsStream("/client_secrets.json")));
            /*Preconditions.checkArgument(!clientSecrets.getDetails().getClientId().startsWith("Enter ")
                            && !clientSecrets.getDetails().getClientSecret().startsWith("Enter "),
                    "Download client_secrets.json file from https://code.google.com/apis/console/"
                            + "?api=calendar into calendar-appengine-sample/src/main/resources/client_secrets.json");*/
        }

        return clientSecrets;
    }

    // uncomment if you want to use p12 authorization with Google Service Account

    /*static GoogleCredential getCredentials() throws GeneralSecurityException,
            IOException, URISyntaxException {
        JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        HttpTransport httpTransport = GoogleNetHttpTransport
                .newTrustedTransport();

        String CLIENT_ID = "749895953376-d8muikbmv6okhaaa4cthg68g3pamimd1@developer.gserviceaccount.com";
        URL fileUrl = Utils.class.getResource("/Diploma.p12");


        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(CLIENT_ID)
                .setServiceAccountPrivateKeyFromP12File(
                        new File(fileUrl.toURI()))
                //.setClientSecrets(getClientCredential())
                .setServiceAccountScopes(SCOPES).build();

        return credential;
    }*/

    public static Credential getGoogleCredenitals() {
        return googleCredenitals;
    }

    public static void setGoogleCredenitals(Credential googleCredenitals) {
        Utils.googleCredenitals = googleCredenitals;
    }

    public static String normalizePhone(String phoneNumber)
    {
        // remove all junk characters from the phone number, like + - . / etc.
        // cut starting two digits, we convert +38 050 xxx xxx xxx into 050 xxx xxx xxx
        phoneNumber = phoneNumber.replaceAll("[^x0-9]", "");
        if (phoneNumber .length()==12)
            phoneNumber = phoneNumber .substring(2);
        return phoneNumber;
    }
}
