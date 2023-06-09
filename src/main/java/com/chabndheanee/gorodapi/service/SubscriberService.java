package com.chabndheanee.gorodapi.service;

import com.chabndheanee.gorodapi.dao.SubscriberDao;
import com.chabndheanee.gorodapi.model.Subscriber;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubscriberService {
    private final SubscriberDao dao;

    public List<Subscriber> getSubscribers(Boolean sortBy, String filter, Integer page) {
        return dao.getSubscribers(sortBy, filter, page);
    }

    public List<Subscriber> searchSubscriber(boolean includeChild, Integer page, Integer serviceId) {
        return dao.searchSubscriber(includeChild, page, serviceId);
    }

    public int createSubscriber(Subscriber subscriber) {
        return dao.createSubscriber(subscriber);
    }
}
