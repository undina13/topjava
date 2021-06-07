package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DaoMeal;
import ru.javawebinar.topjava.dao.DaoMealMap;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
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
    private DaoMeal dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dao = new DaoMealMap();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String forward = "";
        String action = request.getParameter("action");


        if (action == null) {
            log.debug("mealList all");
            request.setAttribute("listMeal", MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.NORMAL_CALORIES));
            forward = LIST_MEAL;
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equals("delete")) {
            log.debug("delete meal");
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            dao.delete(mealId);
            response.sendRedirect("meals");
        } else if (action.equals("edit")) {
            log.debug("edit meal");
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = dao.getById(mealId);
            request.setAttribute("meal", meal);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equals("add")) {
            log.debug("add meal");
            forward = INSERT_OR_EDIT;
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else {
            log.debug("meal wrong action");
            request.setAttribute("listMeal", MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.NORMAL_CALORIES));
            forward = LIST_MEAL;
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        }
    }

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
