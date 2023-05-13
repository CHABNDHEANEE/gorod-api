package com.chabndheanee.gorodapi.dao;

import com.chabndheanee.gorodapi.model.Service;

import java.util.List;

public interface ServiceDao {
    List<Service> getHierarchy();
    void deleteService(boolean force, Integer service);
}
