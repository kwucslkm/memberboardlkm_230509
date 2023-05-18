package com.icia.mbboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class boardCommentDTO {
    private static int id;
    private Long boardId;
    private Long boardCommentCount;

    public boardCommentDTO() {
        id = id + 1;
    }


}
