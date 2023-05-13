package com.chabndheanee.gorodapi.controller;

import com.chabndheanee.gorodapi.model.Service;
import com.chabndheanee.gorodapi.service.ServiceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/service")
public class ServiceController {
    private final ServiceService service;

    @GetMapping
    @ResponseBody
    public List<Service> getHierarchy() {
        return service.getHierarchy();
    }

    @DeleteMapping
    public void deleteService(@RequestParam(defaultValue = "false") boolean force,
                              @RequestParam(value = "id") Integer serviceToDelete) {
        service.deleteService(force, serviceToDelete);
    }
}
