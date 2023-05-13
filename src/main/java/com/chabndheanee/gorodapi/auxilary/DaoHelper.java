package com.chabndheanee.gorodapi.auxilary;

import com.chabndheanee.gorodapi.model.Service;
import com.chabndheanee.gorodapi.model.Subscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DaoHelper {
    private final JdbcTemplate jdbcTemplate;

    public Service getService(int id) {
        String sql = "SELECT * FROM services WHERE id=?";
        return jdbcTemplate.queryForObject(sql, this::makeService, id);
    }

    public Service makeService(ResultSet rs, int rowNum) throws SQLException {
        Service result = Service.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .children(getChildList(rs.getInt("id")))
                .build();
        return result;
    }

    public List<Subscriber> getSubscribersByServiceId(boolean includeChild, Integer page, Integer service_id) {
        List<Subscriber> result;

        if (includeChild) {
            result = searchIncludeChild(service_id);
        } else {
            result = searchExcludeChild(service_id);
        }

        return getListForPage(result, page);
    }

    public boolean checkSubscriberExistenceByServiceId(int serviceId) {
        return getSubscribersByServiceId(true, 1, serviceId).size() != 0;
    }

    public Subscriber makeSubscriber(ResultSet rs, int rowNum) throws SQLException {
        return Subscriber.builder()
                .fio(rs.getString("fio"))
                .account(rs.getString("account"))
                .service(getService(rs.getInt("service_id")))
                .build();
    }

    public List<Subscriber> getListForPage(List<Subscriber> init, int page) {
        if (page == -1) {
            return init;
        } else if (page == 1) {
            return init.stream()
                    .limit(10)
                    .collect(Collectors.toList());
        } else {
            List<Subscriber> result = init.subList((page - 1) * 10, init.size());
            return result.stream()
                    .limit(10)
                    .collect(Collectors.toList());
        }
    }

    private List<Subscriber> searchIncludeChild(int service_id) {
        List<Subscriber> result = new ArrayList<>();
        Service searchService = getService(service_id);

        for (Service service :
                searchService.getChildren()) {
            String sql = "SELECT * FROM subscribers WHERE service_id=?";
            List<Subscriber> subRes = jdbcTemplate.query(sql, this::makeSubscriber, service.getId());
            result.addAll(subRes);
        }
        return result;
    }

    private List<Subscriber> searchExcludeChild(int service_id) {
        String sql = "SELECT * FROM subscribers WHERE service_id=?";
        return jdbcTemplate.query(sql, this::makeSubscriber, service_id);
    }

    private List<Service> getChildList(int parent_id) {
        String sql = "SELECT s.* FROM services_children AS sc " +
                "JOIN services AS s " +
                "ON s.id=sc.child_id " +
                "WHERE sc.PARENT_ID = ?";
        return jdbcTemplate.query(sql, this::makeService, String.valueOf(parent_id));
    }
}
