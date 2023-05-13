package com.chabndheanee.gorodapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Service {
    private int id;
    private String name;
    private List<Service> children;
}
