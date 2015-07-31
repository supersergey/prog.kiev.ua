package myServletPackage;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.*;

public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.print("hello world!");
    }
}

