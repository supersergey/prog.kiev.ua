import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.net.Authenticator;
import java.util.Properties;

public class Main {

    private static final String MAIL_SERVER = "imap.ukr.net";
    private static final String LOGIN = "serg_z2003";
    private static final String PASSWORD = "judas64";

    public static void main(String[] args) {
        System.out.println(LOGIN.replaceAll("\\D", ""));
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getInstance(props, null);
        try
        {
            Store store = session.getStore();
            store.connect(MAIL_SERVER, LOGIN, PASSWORD);
            Folder[] f = store.getDefaultFolder().list();
            for(Folder fd:f)
                System.out.println(">> "+fd.getName());
            Folder inbox = store.getFolder("Черновики");
            inbox.open(Folder.READ_WRITE);

            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);

            Message[] msg = inbox.search(unseenFlagTerm);
            System.out.println(inbox.getUnreadMessageCount());
            System.out.println(msg.length);
            /*Address[] in = msg.getFrom();
            for (Address address : in) {
                System.out.println("FROM:" + address.toString());
            }
            Multipart mp = (Multipart) msg.getContent();
            BodyPart bp = mp.getBodyPart(0);
            System.out.println("SENT DATE:" + msg.getSentDate());
            System.out.println("SUBJECT:" + msg.getSubject());
            System.out.println("CONTENT:" + bp.getContent());*/
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
