package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealDao implements MealDao {
    public static AtomicInteger count = new AtomicInteger(0);
    private final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();

    public MemoryMealDao() {
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
    public void delete(int id) {
        mealMap.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        if (mealMap.containsKey(meal.getId())) {
            mealMap.put(meal.getId(), meal);
        } else {
            add(meal);
        }
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
