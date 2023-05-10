package com.icia.mbboard.controller;

import com.icia.mbboard.dto.BoardDTO;
import com.icia.mbboard.dto.BoardFileDTO;
import com.icia.mbboard.dto.MemberDTO;
import com.icia.mbboard.service.BoardService;
import com.icia.mbboard.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class boardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private  MemberService memberService;

    @GetMapping("/findAll")
    public String  boardfindAll(Model model){
//        System.out.println("list 가질러 간다.");
        List<BoardDTO> boardDTOList = boardService.boardFindAll();
//        System.out.println("cont까지온게 = " + boardDTOList);
        model.addAttribute("boardList",boardDTOList);
//        System.out.println(boardDTOList);
        return "boardpages/boardList";

    }
    @GetMapping("/boardSave")
    public String boardSave(HttpSession session, Model model){
        String loginEmailchk = (String)session.getAttribute("loginEmail");
//        System.out.println("세션값1"+loginEmailchk);
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmailchk);
//        System.out.println("가져온멤버정보 = " + memberDTO);
        model.addAttribute("member",memberDTO);
        return "boardpages/boardSaveForm";
    }
    @PostMapping("/boardSave")
    public String boardSaveFoem(@ModelAttribute BoardDTO boardDTO, HttpSession session)throws IOException {
        String loginEmailchk = (String)session.getAttribute("loginEmail");
//        System.out.println("세션값2"+loginEmailchk);
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmailchk);
//        System.out.println("멤버에서가져온데이터 = " + memberDTO);

        boardDTO.setMemberId(memberDTO.getId());
        boardDTO.setBoardWriter(memberDTO.getMemberName());
        System.out.println(" 컨트롤러딴 보드글번호"+boardDTO.getId());
        boardService.boardSave(boardDTO);
        return "redirect:/findAll";
    }
    @GetMapping("/board")
    public String findById(@RequestParam("id") Long id, Model model){
        boardService.boardCntHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardDetail",boardDTO);
        if (boardDTO.getFileAttached()==1){
            List<BoardFileDTO> boardFileDTOList = boardService.findBoardFile(id);
            model.addAttribute("boardFileList", boardFileDTOList);
        }
        return "boardpages/boardDetail";
    }


}
