package com.chabndheanee.gorodapi.dao.impl;

import com.chabndheanee.gorodapi.auxilary.DaoHelper;
import com.chabndheanee.gorodapi.dao.SubscriberDao;
import com.chabndheanee.gorodapi.exception.ObjectAlreadyExistsException;
import com.chabndheanee.gorodapi.model.Subscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubscriberDaoImpl implements SubscriberDao {
    private final JdbcTemplate jdbcTemplate;
    private final DaoHelper helper;

    @Override
    public List<Subscriber> getSubscribers(Boolean sortBy, String filter, Integer page) {
        String sql = "SELECT * FROM subscribers ";

        if (filter != null) {
            sql += "WHERE account=" + filter;
        }

        if (sortBy) {
            sql += " SORT BY account ";
        }

        List<Subscriber> result = jdbcTemplate.query(sql, helper::makeSubscriber);

        return helper.getListForPage(result, page);
    }

    @Override
    public int createSubscriber(Subscriber subscriber) {
        checkSubscriber(subscriber);

        String sql = "INSERT INTO subscribers (fio, account, service_id) " +
                "VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, subscriber.getFio());
            ps.setString(2, subscriber.getAccount());
            ps.setInt(3, subscriber.getService().getId());
            return ps;
        }, keyHolder);

        return (int) keyHolder.getKey();
    }

    @Override
    public List<Subscriber> searchSubscriber(boolean includeChild, Integer page, Integer serviceId) {
        return helper.getSubscribersByServiceId(includeChild, page, serviceId);
    }

    private void checkSubscriber(Subscriber subscriber) {
        String sql = "SELECT COUNT(*) " +
                "FROM subscribers " +
                "WHERE account=? AND service_id=?";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, subscriber.getAccount(), subscriber.getService().getId());

        if (result != null && result > 0) {
            throw new ObjectAlreadyExistsException("Данная пара л/c-услуга уже существует");
        }
    }
}
