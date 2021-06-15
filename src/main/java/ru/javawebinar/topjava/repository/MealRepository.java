package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal, Integer userId);

    boolean delete(int id, Integer userId);

    Meal get(int id, Integer userId);

    Collection<Meal> getAll(int userId);

    List<Meal> getFilteredMeal(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId);
}
