package ua.kiev.prog.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by user on 03.09.2015.
 */
public class Utils {

    /*private static final AppEngineDataStoreFactory DATA_STORE_FACTORY =
            AppEngineDataStoreFactory.getDefaultInstance();*/

    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final URL fileUrl = Utils.class.getResource("/Diploma.p12");
    static GoogleCredential credential;
    private static String CLIENT_ID;
    private static HttpTransport httpTransport;
    static String MESSAGE_SENT_TO_EMAIL;
    static String MESSAGE_SENT_TO_PHONE;
    static String APP_EMAIL_ADDRESS;
    static String APP_FROM_NAME;
    static String ADMIN_EMAIL_ADDRESS;
    static String MESSAGE_IS_NOT_SENT;

    private static final List<String> SCOPES = Arrays.asList(
            "https://spreadsheets.google.com/feeds",
            "https://docs.google.com/feeds",
            "https://www.googleapis.com/auth/drive");

    static {
        Properties properties = new Properties();
        try
        {
            properties.load(Utils.class.getResourceAsStream("/main.properties"));
            CLIENT_ID = properties.getProperty("CLIENT_ID");
            properties.load(Utils.class.getResourceAsStream("/email.properties"));
            MESSAGE_SENT_TO_EMAIL = properties.getProperty("MESSAGE_SENT_TO_EMAIL");
            MESSAGE_SENT_TO_PHONE = properties.getProperty("MESSAGE_SENT_TO_PHONE");
            APP_EMAIL_ADDRESS = properties.getProperty("APP_EMAIL_ADDRESS");
            APP_FROM_NAME = properties.getProperty("APP_FROM_NAME");
            ADMIN_EMAIL_ADDRESS = properties.getProperty("ADMIN_EMAIL_ADDRESS");
            MESSAGE_IS_NOT_SENT = properties.getProperty("MESSAGE_IS_NOT_SENT");
        }
        catch (IOException ex) {
            System.out.println("Application initialization error. Check main properties.");
            ex.printStackTrace();
        }
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

    private static void sendEmail(String from, String fromName, String to, String subject, String body) throws UnsupportedEncodingException, MessagingException
    {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from, fromName));
        msg.addRecipient(Message.RecipientType.TO,
                new InternetAddress(to, to));
        msg.setSubject(subject);
        msg.setText(body);
        Transport.send(msg);
    }

    static void sendUserEmail(String to, String subject, String body) throws UnsupportedEncodingException, MessagingException
    {
        sendEmail(APP_EMAIL_ADDRESS, APP_FROM_NAME, to, subject, body);
    }

    static void sendAdminEmail(String subject, String body) throws UnsupportedEncodingException, MessagingException
    {
        sendEmail(APP_EMAIL_ADDRESS, APP_FROM_NAME, ADMIN_EMAIL_ADDRESS, subject, body);
    }

}
