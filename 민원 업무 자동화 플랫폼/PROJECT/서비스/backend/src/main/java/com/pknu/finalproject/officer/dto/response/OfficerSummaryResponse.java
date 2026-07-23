package com.pknu.finalproject.officer.dto.response;

import com.pknu.finalproject.officer.entity.Officer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OfficerSummaryResponse {
    private Long accountId;
    private String name;
    private String position;

    public static OfficerSummaryResponse from(Officer o) {
        return OfficerSummaryResponse.builder()
                .accountId(o.getAccountId())
                .name(o.getName())
                .position(o.getPosition())
                .build();
    }
}
