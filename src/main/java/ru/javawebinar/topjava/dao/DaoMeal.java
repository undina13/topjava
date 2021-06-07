package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface DaoMeal {
    Meal add(Meal meal);

    void delete(int mealID);

    Meal update(Meal meal);

    List<Meal> getAll();

    Meal getById(int id);

}
