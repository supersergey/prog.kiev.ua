package Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
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
        try {
            InputStream is = this.getClass().getResourceAsStream("/emailtemplate.txt");
            byte buf[] = new byte[is.available()];
            is.read(buf);
            message = new String(buf, "UTF-8");        }
        catch (IOException ex)
        {
            System.out.println("Check email template.");
            ex.printStackTrace();
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

<<<<<<< HEAD
=======
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

>>>>>>> ce1cf9535f1ffb7d470a8a10874ff8f83284e8e8
        try {
            String emailAddress = request.getParameter("emailAddress").trim();
            Integer mailId = Integer.parseInt(request.getParameter("mailId"));
            MailEntry mailEntry = MailStack.getEntryById(mailId);
            if (null == mailEntry)
                throw new MessagingException();

            StringBuilder resultText = new StringBuilder();
            Map<String, String> courses = mailEntry.getCourses();
            if (null == courses)
                throw new MessagingException();
            for (Map.Entry<String, String> entry : courses.entrySet())
            {
                if (!entry.getValue().equals(""))
                    resultText.append("\r\n").append(entry.getKey()).append(": ").append(entry.getValue());
            }
            String msgBody = String.format(message, resultText.toString());
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("diploma-1056@appspot.gserviceaccount.com", "prog.kiev.ua Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(emailAddress, emailAddress));
            msg.setSubject("prog.kiev.ua video");
            msg.setText(msgBody);
            Transport.send(msg);

            response.setStatus(HttpServletResponse.SC_OK);
            request.setAttribute("mailSent", "success"); // mail sent
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/mailsent.jsp");
            dispatcher.forward(request, response);
        } catch (MessagingException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute("mailSent", "error"); // mail send fail
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/mailsent.jsp");
            dispatcher.forward(request, response);
        }
    }
}