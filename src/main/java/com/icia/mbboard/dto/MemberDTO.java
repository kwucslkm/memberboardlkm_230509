package com.icia.mbboard.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class mb_memberDTO {
    private Long id;
    private String memberEmail;
    private  String memberPassword;
    private String memberName;
    private String memberMobile;
    private String memberProfile;
    private int fileAttached;
    private List<MultipartFile> memberProfileFile;

}
