package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DaoMeal;
import ru.javawebinar.topjava.dao.DaoMealImpl;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final static String INSERT_OR_EDIT = "/updateMeal.jsp";
    private final static String LIST_MEAL = "/meals.jsp";
    private DaoMeal dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dao = new DaoMealImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String forward = "";
        String action = request.getParameter("action");

        // List<MealTo> listMeal = MealsUtil.filteredByStreams(DaoMealImpl.meals, LocalTime.MIN, LocalTime.MAX, MealsUtil.NORMAL_CALORIES);
        if (action == null) {
            request.setAttribute("listMeal", dao.getAllMeals());
            forward = LIST_MEAL;
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equals("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));

            dao.deleteMeal(mealId);
            response.sendRedirect("meals");

        } else if (action.equals("edit")) {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = dao.getMealByID(mealId);
            request.setAttribute("meal", meal);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equals("add")) {
            forward = INSERT_OR_EDIT;

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
            dao.addMeal(meal);
        } else {
            int idM = Integer.parseInt(id);
            meal.setId(idM);
            dao.updateMeal(meal, idM);
        }
        response.sendRedirect("meals");
    }

}
