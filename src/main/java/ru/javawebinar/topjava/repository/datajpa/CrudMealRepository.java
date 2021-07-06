package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query(name = Meal.DELETE)
    int delete(@Param("id") int id, @Param("userId") int userId);

    Optional<Meal> findByIdAndUserId(int id, int userId);

    List<Meal> findAllByUserId(int userId, Sort sort);

    @Query(name = Meal.GET_BETWEEN)
    List<Meal> getAllBetween(@Param("userId") int userId,
                             @Param("startDateTime") LocalDateTime startDateTime,
                             @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.user u WHERE m.id=:id AND u.id=:userId")
    Meal getWithUser(@Param("id") int id, @Param("userId") int userId);
}

