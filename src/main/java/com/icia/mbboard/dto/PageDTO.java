package com.icia.mbboard.dto;

import lombok.Data;

@Data
public class PageDTO {
    private int page; // 현재 페이지
    private int startPage; // 하단에 보여지는 시작 페이지 번호
    private int endPage; // 하단에 보여지는 마지막 페이지 번호
    private int maxPage; // 전체 페이지 갯수

}
