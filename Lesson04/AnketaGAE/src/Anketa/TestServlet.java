package Anketa;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by user on 05.08.2015.
 */
@WebServlet(name = "TestServlet", urlPatterns = "{/process}")
public class TestServlet extends HttpServlet {

    private static SurveyData currentSurvey = new SurveyData();
    private SurveyStatistics statistics = SurveyStatistics.getInstance();

    public static SurveyData getCurrentSurvey() {
        return currentSurvey;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String temp = request.getParameter("name");
        if (null==temp)
            currentSurvey.setName("Аноним");
        else
            if (temp.equals(""))
        currentSurvey.setName("Аноним");
        else
                currentSurvey.setName(temp);

        temp = request.getParameter("drink");
        if (null==temp)
            currentSurvey.setDrinks("Напиток не указан");
        else
        if (temp.equals(""))
            currentSurvey.setDrinks("Напиток не указан");
        else
            currentSurvey.setDrinks(temp);

        if (null != request.getParameter("music"))
            currentSurvey.setMusics(request.getParameterValues("music"));
        else
            currentSurvey.setMusics(new String[]{"Музыкальный стиль не выбран."});

        temp = request.getParameter("age");
        currentSurvey.setAges(temp);

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        statistics.addSurveyEntry(currentSurvey);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/surveyResults.jsp");
        dispatcher.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        pw.write("Hello this is Test Servlet");
        pw.flush();

    }
}
