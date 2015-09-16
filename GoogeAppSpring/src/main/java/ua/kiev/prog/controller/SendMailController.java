package ua.kiev.prog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.kiev.prog.DAO.Student;
import ua.kiev.prog.controller.MailStack;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
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
@Controller
public class SendMailController{

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


    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    public ModelAndView doSendMail(HttpServletResponse response, @RequestParam(value = "emailAddress") String emailAddress, @RequestParam(value = "mailId") Integer mailId) {

        ModelAndView result = new ModelAndView("mailsent");
        try {
            emailAddress = emailAddress.trim();
            List<Student> studentsList = MailStack.getEntryById(mailId);
            if (null == studentsList)
                throw new MessagingException();

            StringBuilder resultText = new StringBuilder();
            StringBuilder adminMessage = new StringBuilder();
            for (Student student : studentsList)
            {
                if (!student.getCourseName().equals(""))
                    resultText.append("\r\n").append(student.getCourseName()).append(": ").append(student.getUrl());
                adminMessage.append(String.format(Utils.MESSAGE_SENT_TO_EMAIL, student.getCourseName(), student.getLocation(), student.getTeacherName(), student.getStartDate(), student.getName(), student.getPhone(), emailAddress)).append("r\n");
            }
            String msgBody = String.format(message, resultText.toString());
            Utils.sendUserEmail(emailAddress, "Prog.kiev.ua video", msgBody);
            Utils.sendAdminEmail(studentsList.get(0).getPhone(), adminMessage.toString());
            result.addObject("mailSent", "success");

        } catch (MessagingException | UnsupportedEncodingException ex) {
            result.addObject("mailSent", "error"); // mail send fail
        }
        return result;
    }
}