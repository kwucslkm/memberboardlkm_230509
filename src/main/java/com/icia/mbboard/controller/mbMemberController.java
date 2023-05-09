package com.icia.mbboard.controller;

import com.icia.mbboard.dto.MemberDTO;
import com.icia.mbboard.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class mbMemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/save_member")
    public String saveMember(){
        return "memberpages/saveMemberForm";
    }
    @PostMapping("/saveMember")
    public  String save(@ModelAttribute MemberDTO memberDTO) throws IOException {
        System.out.println("컨트롤러 보내기전 = " + memberDTO);
        memberService.saveMember(memberDTO);

            return "index";
        }
    @PostMapping("/email-chk")
    public ResponseEntity emailChk(@RequestParam String memberEmail){
        System.out.println("회원가입에서ajax로 보냇어 = " + memberEmail);
        MemberDTO memberDTO = memberService.findByMemberEmail(memberEmail);
        System.out.println("디비에서 가져왔어 = " + memberDTO);
        if (memberDTO==null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    }


