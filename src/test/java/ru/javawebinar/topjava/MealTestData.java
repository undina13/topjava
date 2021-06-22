package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_ID_1 = START_SEQ + 2;
    public static final int USER_MEAL_ID_2 = USER_MEAL_ID_1 + 1;
    public static final int USER_MEAL_ID_3 = USER_MEAL_ID_1 + 2;
    public static final int USER_MEAL_ID_4 = USER_MEAL_ID_1 + 3;
    public static final int USER_MEAL_ID_5 = USER_MEAL_ID_1 + 4;
    public static final int USER_MEAL_ID_6 = USER_MEAL_ID_1 + 5;
    public static final int USER_MEAL_ID_7 = USER_MEAL_ID_1 + 6;
    public static final int ADMIN_MEAL_ID_1 = USER_MEAL_ID_1 + 7;
    public static final int ADMIN_MEAL_ID_2 = USER_MEAL_ID_1 + 8;
    public static final int NOT_FOUND_MEAL = 10;

    public static final Meal userMeal1 = new Meal(USER_MEAL_ID_1, LocalDateTime.of(2021, Month.MAY, 30, 10, 0), "User Завтрак", 500);
    public static final Meal userMeal2 = new Meal(USER_MEAL_ID_2, LocalDateTime.of(2021, Month.MAY, 30, 13, 0), "User Обед", 1000);
    public static final Meal userMeal3 = new Meal(USER_MEAL_ID_3, LocalDateTime.of(2021, Month.MAY, 30, 20, 0), "User Ужин", 500);
    public static final Meal userMeal4 = new Meal(USER_MEAL_ID_4, LocalDateTime.of(2021, Month.MAY, 31, 0, 0), "User Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(USER_MEAL_ID_5, LocalDateTime.of(2021, Month.MAY, 31, 10, 0), "User Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(USER_MEAL_ID_6, LocalDateTime.of(2021, Month.MAY, 31, 13, 0), "User Обед", 500);
    public static final Meal userMeal7 = new Meal(USER_MEAL_ID_7, LocalDateTime.of(2021, Month.MAY, 31, 20, 0), "User Ужин", 410);
    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL_ID_1, LocalDateTime.of(2021, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL_ID_2, LocalDateTime.of(2021, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);
    public static final List<Meal> userMeals = Arrays.asList(userMeal7, userMeal6,
            userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
    public static final List<Meal> adminMeals = Arrays.asList(adminMeal2, adminMeal1);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.JUNE, 1, 22, 0), "New meal", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal1);
        updated.setDescription("Updated meal");
        updated.setDateTime(LocalDateTime.of(2021, Month.MAY, 15, 10, 0));
        updated.setCalories(330);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
