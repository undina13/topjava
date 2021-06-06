package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class DaoMealImpl implements DaoMeal {
    public static AtomicInteger count = new AtomicInteger(1);
    public static List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    private final List<Meal> mealList = new CopyOnWriteArrayList<>();

    public DaoMealImpl() {
        for (Meal meal : meals
        ) {
            addMeal(meal);
        }
    }

    @Override
    public void addMeal(Meal meal) {
        if (meal.getId() == 0) {
            meal.setId(count.getAndIncrement());
        }
        mealList.add(meal);
    }

    @Override
    public void deleteMeal(int mealID) {
        int id = mealList.indexOf(getMealByID(mealID));
        mealList.remove(id);
    }

    @Override
    public void updateMeal(Meal meal, int mealId) {
        int id = mealList.indexOf(getMealByID(mealId));
        mealList.set(id, meal);
    }

    @Override
    public List<MealTo> getAllMeals() {
        return MealsUtil.filteredByStreams(mealList, LocalTime.MIN, LocalTime.MAX, MealsUtil.NORMAL_CALORIES);
    }

    @Override
    public Meal getMealByID(int id) {
        for (Meal meal : mealList
        ) {
            if (meal.getId() == id) {
                return meal;
            }
        }
        return null;
    }
}
