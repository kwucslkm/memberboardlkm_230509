package com.icia.mbboard.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Data
public class BoardDTO {
    private Long id;
    private Long memberId;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private Timestamp boardCreatedDate;
    private int boardHits;
    private int fileAttached;
    private List<MultipartFile> boardFile;
}
