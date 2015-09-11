package Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by user on 11.09.2015.
 */
public class SendMail extends HttpServlet {

    private Date lastSentMailDate = new Date();
    private String message;

    {
        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/links.txt")));
        try {

            StringBuilder sb = new StringBuilder();
            while (br.ready()) {
                sb.append(br.readLine());
            }
            message = sb.toString();
        }
        catch (IOException ex)
        {
            System.out.println("Check links configuration.");
            ex.printStackTrace();
        }
        finally {
            try
            {
                br.close();
            }
            catch (IOException ignored) {}
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String emailAddress = request.getParameter("emailAddress").trim();
        Integer mailId = Integer.parseInt(request.getParameter("mailId"));
        MailEntry mailEntry = MailStack.getEntryById(mailId);

        StringBuilder resultText = new StringBuilder();

        for (Map.Entry<String, String> entry : mailEntry.getCourses().entrySet())
        {
            if (!entry.getKey().equals("Для получения ссылки оплатите курс полностью."))
                resultText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }

        String msgBody = String.format(message, resultText.toString());
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("diploma-1056@appspot.gserviceaccount.com", "prog.kiev.ua Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(emailAddress, emailAddress));
            msg.setSubject("prog.kiev.ua video");
            msg.setText(msgBody);
            Transport.send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}