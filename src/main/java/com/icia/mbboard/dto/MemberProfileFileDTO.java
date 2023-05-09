package com.icia.mbboard.dto;

import lombok.Data;

@Data
public class MemberProfileFileDTO {
    private Long id;
    private String profileFileName;
    private String profileStoredFileName;
    private Long memberId;


}
