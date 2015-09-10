import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sergey on 09.09.2015.
 */
public class SearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StudentsDAO studentsDAO = new StudentsDAOImpl();
        String mask = request.getParameter("phoneNumber");
        Pattern phonePattern = Pattern.compile("\\+?([0-9\\s\\-]{7,})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$");
        Matcher phoneMatcher = phonePattern.matcher(mask);
        if (phoneMatcher.matches())
            mask = Utils.normalizePhone(mask);

        List<Student> studentsMatchingPhone = studentsDAO.getStudentsByPhoneNumber(mask);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        request.setAttribute("students", studentsMatchingPhone);
        request.setAttribute("dataIsReady", true);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/searchpage.jsp");
        dispatcher.forward(request, response);
    }
}
