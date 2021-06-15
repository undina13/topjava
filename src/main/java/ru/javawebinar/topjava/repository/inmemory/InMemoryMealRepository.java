package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserID(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        meal.setUserID(userId);
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        return isThisUserMeal(id, userId) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        if (isThisUserMeal(id, userId)) {
            return repository.get(id);
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserID() == userId)
                .sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        log.info("getFiltred");
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }

    private boolean isThisUserMeal(int id, int userId) {
                return repository.get(id).getUserID() == userId;
    }
}

