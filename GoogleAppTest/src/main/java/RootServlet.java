import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by sergey on 10.09.2015.
 */
public class RootServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("dataIsReady", 1); // 0 - init page, 1 - data is found, 2 - data not found
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/test.jsp");
        dispatcher.forward(request, response);

    }
}
