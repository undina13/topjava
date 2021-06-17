package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    public static Integer userId;
    public static Integer authUserId() {
        if ( userId == null) {
            userId = 1;
        }
        return userId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public static void setAuthUserId(Integer id) {
        SecurityUtil.userId = id;
    }
}