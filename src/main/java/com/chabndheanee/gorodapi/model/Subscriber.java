package com.chabndheanee.gorodapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Subscriber {
    private int id;
    private String fio;
    private String account;
    private Service service;
}
