package com.pknu.finalproject.officer.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {
    private Long toDeptId;
    private String reason;
    private String reasonCode;
}
