package com.icia.mbboard.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class boardDTO {
    private Long id;
    private Long memberId;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private Timestamp boardCreatedDate;
    private int boardHits;
    private int fileAttached;
}
