package com.chabndheanee.gorodapi.dao.impl;

import com.chabndheanee.gorodapi.auxilary.DaoHelper;
import com.chabndheanee.gorodapi.dao.ServiceDao;
import com.chabndheanee.gorodapi.exception.ServiceDeleteException;
import com.chabndheanee.gorodapi.model.Service;
import com.chabndheanee.gorodapi.model.Subscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class ServiceDaoImpl implements ServiceDao {
    private final JdbcTemplate jdbcTemplate;
    private final DaoHelper helper;

    @Override
    public List<Service> getHierarchy() {
        String sql = "SELECT * FROM services " +
                "WHERE id NOT IN (SELECT child_id FROM services_children)";
        List<Service> result = jdbcTemplate.query(sql, helper::makeService);
        return result;

    }

    @Override
    public void deleteService(boolean force, Integer serviceId) {
        String sql;
        Service service = helper.getService(serviceId);

        if (force) {
            sql = "DELETE FROM SUBSCRIBERS WHERE ID=?";
            List<Subscriber> subscribersToDelete = helper.getSubscribersByServiceId(true, -1, service.getId());

            for (Subscriber sub : subscribersToDelete) {
                jdbcTemplate.update(sql, sub.getId());
            }

            sql = "DELETE FROM services WHERE ID=?";

            for (Service ser : service.getChildren()) {
                jdbcTemplate.update(sql, ser.getId());
            }
        } else {
            checkService(service);
            sql = "DELETE FROM services WHERE id=?";
            jdbcTemplate.update(sql, service.getId());
        }
    }

    private void checkService(Service service) {
        if (helper.checkSubscriberExistenceByServiceId(service.getId())) {
            throw new ServiceDeleteException("Невозможно удаление! Данная услуга содержит абонента!");
        } else if (!service.getChildren().isEmpty()) {
            throw new ServiceDeleteException("Невозможно удаление! Данная услуга содержит дочерние услуги!");
        }
    }
}
