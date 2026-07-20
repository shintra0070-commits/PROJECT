package com.pknu.backend.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * COMMENT 복합키 클래스
*/
@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

@ToString

public class CommentId implements Serializable {

    private Integer comid;
    private String memid;
    private LocalDateTime commentcreated;
}