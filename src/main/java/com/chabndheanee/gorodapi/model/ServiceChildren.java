package com.chabndheanee.gorodapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ServiceChildren {
    private int parentId;
    private int childId;
}
