package com.icia.mbboard.controller;

import com.icia.mbboard.dto.MemberDTO;
import com.icia.mbboard.dto.MemberProfileFileDTO;
import com.icia.mbboard.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class MbMemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/save_member")
    public String saveMember() {
        return "memberpages/saveMemberForm";
    }

    @PostMapping("/saveMember")
    public String save(@ModelAttribute MemberDTO memberDTO) throws IOException {
        System.out.println("컨트롤러 보내기전 = " + memberDTO);
        memberService.saveMember(memberDTO);
        System.out.println("회원가입성공");
        return "memberpages/memberlogin";
    }
    @PostMapping("/email-chk")
    public ResponseEntity emailChk(@RequestParam String memberEmail) {
//        System.out.println("회원가입에서ajax로 보냇어 = " + memberEmail);`
        MemberDTO memberDTO = memberService.findByMemberEmail(memberEmail);
//        System.out.println("디비에서 가져왔어 = " + memberDTO);
        if (memberEmail.length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (memberDTO == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/memberLogin")
    public String loginForm() {
        return "memberpages/memberlogin";
    }
    @PostMapping("/memberLogin")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        boolean loginResult = memberService.memberLogin(memberDTO);
        if (loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            String loginEmailchk = (String) session.getAttribute("loginEmail");
//            System.out.println("세션에 담긴 = " + loginEmailchk);
            return "redirect:/pagingList";
        } else {
            return "index";
        }
    }

    @GetMapping("/memberList")
    public String memberFindAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.memberFindAll();

        model.addAttribute("memberList", memberDTOList);
        return "memberpages/memberListAll";
    }




    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
//        System.out.println("ㅁㅏ이페이지 시작");
        String loginEmailchk = (String) session.getAttribute("loginEmail");
//        System.out.println("세션에 담긴 = " + loginEmailchk);
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmailchk);
        model.addAttribute("member", memberDTO);
//        System.out.println("해당 프로픨사진 여부"+memberDTO.getMemberProfile());
        if (memberDTO.getMemberProfile() == 1) {
            Long memberId = memberDTO.getId();

            List<MemberProfileFileDTO> memberProfileFileDTOList = memberService.findFile(memberId);
            model.addAttribute("memberPofile", memberProfileFileDTOList);
//            System.out.println("memberProfileFileDTOList = " + memberProfileFileDTOList);
        }
        return "memberpages/memberDetail";
    }

    @GetMapping("/memberPage")
    public String memberFindById(@RequestParam("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.findMemberById(id);
        model.addAttribute("member", memberDTO);
        if (memberDTO.getMemberProfile() == 1) {
            Long memberId = memberDTO.getId();
            List<MemberProfileFileDTO> memberProfileFileDTOList = memberService.findFile(memberId);
            model.addAttribute("memberPofile", memberProfileFileDTOList);
//            System.out.println("memberProfileFileDTOList = " + memberProfileFileDTOList);
        }
        return "memberpages/memberDetail";

    }
    @GetMapping("/memberDelete")
    public String memberDelete(@RequestParam("id") Long memberId){
        System.out.println("CTR시작memberId = " + memberId);
        memberService.memberDelete(memberId);

        return "redirect:/memberList";
    }
    @GetMapping("/memberSelfOut")
    public String memberSelfOut(@RequestParam("id") Long memberId, HttpSession session){
        System.out.println("탈퇴 id = " + memberId);
        session.invalidate();
        memberService.memberDelete(memberId);

        return "redirect:/memberList";
    }
    @GetMapping("/memberUpdate")
    public String memberUPdate(@RequestParam("id") Long id, Model model){
        MemberDTO memberDTO = memberService.findMemberById(id);
        model.addAttribute("member",memberDTO);
        return "memberpages/memberUpdateForm";
    }
    @PostMapping("/memberUpdate")
    public String memberUpdateForm(@ModelAttribute MemberDTO memberDTO, HttpSession session){
//        System.out.println("업데이트폼에서넘어온memberDTO = " + memberDTO);
        int result = memberService.memberUpdate(memberDTO);
        if (result==1) {
            session.invalidate();
            return "redirect:/memberLogin";
        }else {
            System.out.println("수정 실패");
            return "redirect:/memberPage";
        }
    }
}


