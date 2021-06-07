package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DaoMealMap implements DaoMeal {
    public static AtomicInteger count = new AtomicInteger(0);
    private final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();

    public DaoMealMap() {
        for (Meal meal : MealsUtil.meals) {
            add(meal);
        }
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(count.getAndIncrement());
        mealMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int mealID) {
        mealMap.remove(mealID);
    }

    @Override
    public Meal update(Meal meal) {
        mealMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public Meal getById(int id) {
        return mealMap.get(id);
    }
}
