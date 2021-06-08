package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MemoryMealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "/updateMeal.jsp";
    private static final String LIST_MEAL = "/meals.jsp";
    private MealDao dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dao = new MemoryMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String forward = "";
        String action = (request.getParameter("action") != null) ? request.getParameter("action") : "null";
        switch (action) {
            case ("delete"):
                log.debug("delete meal");
                dao.delete(Integer.parseInt(request.getParameter("mealId")));
                response.sendRedirect("meals");
                break;
            case ("edit"):
                log.debug("edit meal");
                forward = INSERT_OR_EDIT;
                Meal meal = dao.getById(Integer.parseInt(request.getParameter("mealId")));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(forward).forward(request, response);
                break;
            case ("add"):
                log.debug("add meal");
                forward = INSERT_OR_EDIT;
                request.getRequestDispatcher(forward).forward(request, response);
                break;
            default:
                log.debug("mealList all or meal wrong action");
                request.setAttribute("listMeal", MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.NORMAL_CALORIES));
                forward = LIST_MEAL;
                request.getRequestDispatcher(forward).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("mealid");

        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (id.isEmpty()) {
            log.debug("add meal Post");
            dao.add(meal);
        } else {
            log.debug("update meal Post");
            meal.setId(Integer.parseInt(id));
            dao.update(meal);
        }
        response.sendRedirect("meals");
    }

}
