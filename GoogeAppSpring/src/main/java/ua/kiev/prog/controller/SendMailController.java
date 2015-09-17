package ua.kiev.prog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.kiev.prog.DAO.Student;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.mail.*;

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
    public ModelAndView doSendMail(@RequestParam(value = "emailAddress") String emailAddress, @RequestParam(value = "mailId") Integer mailId) {

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
                String courseUrl = student.getUrl();
                if (courseUrl.isEmpty())
                {
                    courseUrl = "Видео недоступно, т.к. не получена полная оплата за курс";
                    adminMessage.append(String.format(Utils.MESSAGE_IS_NOT_SENT, student.getCourseName(),
                            student.getLocation(),
                            student.getTeacherName(),
                            student.getStartDate(),
                            student.getName(),
                            student.getPhone()))
                            .append("\r\n");
                }
                else
                    adminMessage.append(String.format(Utils.MESSAGE_SENT_TO_EMAIL, student.getCourseName(), student.getLocation(), student.getTeacherName(), student.getStartDate(), student.getName(), student.getPhone(), emailAddress)).append("\r\n");
                resultText.append("\r\n").append(student.getCourseName()).append(": ").append(courseUrl);
            }
            String msgBody = String.format(message, resultText.toString());
            Utils.sendUserEmail(emailAddress, "Prog.kiev.ua video", msgBody);
            if (!adminMessage.toString().isEmpty())
            {
                Utils.sendAdminEmail(studentsList.get(0).getPhone(), adminMessage.toString());
            }
            result.addObject("mailSent", "success");

        } catch (MessagingException | UnsupportedEncodingException ex) {
            result.addObject("mailSent", "error"); // mail send fail
        }
        return result;
    }
}