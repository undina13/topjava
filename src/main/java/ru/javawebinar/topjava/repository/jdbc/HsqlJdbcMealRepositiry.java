package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Profile(Profiles.HSQL_DB)
public class HsqlJdbcMealRepositiry extends JdbcMealRepository {
    public HsqlJdbcMealRepositiry(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Timestamp convertDateTime(LocalDateTime localDateTime) {
        return DateTimeUtil.parseToTimestamp(localDateTime);
    }
}
