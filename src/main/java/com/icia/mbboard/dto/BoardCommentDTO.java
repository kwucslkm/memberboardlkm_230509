package com.icia.mbboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class BoardCommentDTO {
    public static int id;
    private Long boardId;
    private Long boardCommentCount;

    public BoardCommentDTO() {
        id = id + 1;
    }



}
