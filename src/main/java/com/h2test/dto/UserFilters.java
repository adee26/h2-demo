package com.h2test.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFilters {
    private String filter;
    private String value;
}
