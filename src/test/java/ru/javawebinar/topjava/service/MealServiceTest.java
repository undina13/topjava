package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-jdbc.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_ID_1, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_MEAL, USER_ID));
    }

    @Test
    public void getNotFoundWithOtherUserId() {
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID_1, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID_2, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID_2, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_MEAL, USER_ID));
    }

    @Test
    public void deleteNotFoundWithOtherUserId() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_ID_1, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> allBetweenInclusive = service.getBetweenInclusive(
                LocalDate.of(2021, Month.MAY, 29),
                LocalDate.of(2021, Month.MAY, 30),
                USER_ID);
        assertMatch(allBetweenInclusive, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void getBetweenNullInclusive() {
        List<Meal> allBetweenInclusive = service.getBetweenInclusive(
                null,
                null,
                USER_ID);
        assertMatch(allBetweenInclusive, userMeals);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, adminMeals);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL_ID_1, USER_ID), MealTestData.getUpdated());
    }

    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () -> service.update(
                new Meal(NOT_FOUND, LocalDateTime.now(), "NotFound", 10), USER_ID));
    }

    @Test
    public void updateNotFoundWithOtherUserId() {
        assertThrows(NotFoundException.class, () -> service.update(new Meal(userMeal1), ADMIN_ID));
    }

    @Test
    public void create() {
        Meal newMeal = MealTestData.getNew();
        Meal created = service.create(newMeal, ADMIN_ID);
        Integer newId = created.getId();
        Meal lastNewMeal = MealTestData.getNew();
        lastNewMeal.setId(newId);
        assertMatch(created, lastNewMeal);
        assertMatch(service.get(newId, ADMIN_ID), lastNewMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, userMeal1.getDateTime(), "duplicateDateTime", 500), USER_ID));
    }
}