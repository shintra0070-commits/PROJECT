package com.pknu.finalproject.officer.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignRequest {
    private Long officerId; // 배정할 담당자 accountId
}
