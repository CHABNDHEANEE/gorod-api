package com.chabndheanee.gorodapi.controller;

import com.chabndheanee.gorodapi.model.Subscriber;
import com.chabndheanee.gorodapi.service.SubscriberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/subscriber")
public class SubscriberController {
    private final SubscriberService service;

    @GetMapping
    public List<Subscriber> getSubscribers(@RequestParam(required = false) String sortBy,
                                           @RequestParam(required = false) String filter,
                                           @RequestParam(defaultValue = "1") Integer page) {
        return service.getSubscribers(sortBy, filter, page);
    }

    @GetMapping("/search")
    public List<Subscriber> searchSubscriber(@RequestParam(defaultValue = "false") boolean includeChild,
                                             @RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam Integer service_id) {
        return service.searchSubscriber(includeChild, page, service_id);
    }

    @PostMapping
    public int createSubscriber(@RequestBody Subscriber subscriber) {
        return service.createSubscriber(subscriber);
    }
}
