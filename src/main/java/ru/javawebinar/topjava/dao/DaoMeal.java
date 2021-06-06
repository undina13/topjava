package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface DaoMeal {
    void addMeal(Meal meal);

    void deleteMeal(int mealID);

    void updateMeal(Meal meal, int mealId);

    List<MealTo> getAllMeals();

    Meal getMealByID(int id);

}
