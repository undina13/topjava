package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.MealTestData.adminMeal1;
import static ru.javawebinar.topjava.MealTestData.adminMeal2;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.admin;

@ActiveProfiles("datajpa")
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(ADMIN_ID);
        UserTestData.MATCHER.assertMatch(user, admin);
        MealTestData.MATCHER.assertMatch(user.getMeals(), adminMeal1, adminMeal2);
    }
}
