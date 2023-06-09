package com.chabndheanee.gorodapi.dao;

import com.chabndheanee.gorodapi.model.Subscriber;

import java.util.List;

public interface SubscriberDao {
    List<Subscriber> getSubscribers(Boolean sortBy, String filter, Integer page);
    List<Subscriber> searchSubscriber(boolean includeChild, Integer page, Integer serviceId);
    int createSubscriber(Subscriber subscriber);
}
