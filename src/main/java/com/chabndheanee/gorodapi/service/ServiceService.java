package com.chabndheanee.gorodapi.service;

import com.chabndheanee.gorodapi.dao.ServiceDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceService {
    private final ServiceDao dao;

    public List<com.chabndheanee.gorodapi.model.Service> getHierarchy() {
        return dao.getHierarchy();
    }

    public void deleteService(boolean force, Integer service) {
        dao.deleteService(force, service);
    }
}
