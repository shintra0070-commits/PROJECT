package com.pknu.finalproject.admin.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficerStatusRequest {
    private String enabled; // "Y" or "N"
}