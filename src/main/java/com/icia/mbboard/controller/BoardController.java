package com.icia.mbboard.controller;

import com.icia.mbboard.dto.*;
import com.icia.mbboard.service.BoardService;
import com.icia.mbboard.service.CommentService;
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
import java.util.List;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/findAll")
    public String boardfindAll(Model model) {
//        System.out.println("list 가질러 간다.");
        List<BoardDTO> boardDTOList = boardService.boardFindAll();
        model.addAttribute("boardList", boardDTOList);
        return "boardpages/boardList";
    }
    @GetMapping("/boardSave")
    public String boardSave(HttpSession session, Model model) {
        String loginEmailchk = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmailchk);
        model.addAttribute("member", memberDTO);
        return "boardpages/boardSaveForm";
    }
    @PostMapping("/boardSave")
    public String boardSaveFoem(@ModelAttribute BoardDTO boardDTO, HttpSession session) throws IOException {
        String loginEmailchk = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmailchk);
        boardDTO.setMemberId(memberDTO.getId());
        boardDTO.setBoardWriter(memberDTO.getMemberName());
        boardService.boardSave(boardDTO);
        return "redirect:/pagingList";
    }
    @GetMapping("/pagingList")
    public String pagingList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             @RequestParam(value = "q", required = false, defaultValue = "") String q,
                             @RequestParam(value = "type", required = false, defaultValue = "") String type,
                             Model model) {
        List<BoardDTO> boardDTOList = null;
        PageDTO pageDTO = null;
        System.out.println("검색할type = " + type);
        if (q.equals("")) {
            boardDTOList = boardService.pagingList(page);
            pageDTO = boardService.pagingParam(page);
        }else {
            System.out.println("검색하러간다 "+type);
            boardDTOList = boardService.searchList(page, type, q);
            pageDTO = boardService.pagingSearchParam(page, type, q);
        }
        model.addAttribute("pagingList", boardDTOList);
        model.addAttribute("paging", pageDTO);

        return "boardpages/boardpagingList";
    }
    @GetMapping("/board")
    public String findById(@RequestParam("id") Long id, Model model) {
//        System.out.println("보드상세조회보드id = " + id);
        boardService.boardCntHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        MemberDTO memberDTO = memberService.findMemberById(boardDTO.getMemberId());
        model.addAttribute("member", memberDTO);
        model.addAttribute("boardDetail", boardDTO);
        if (boardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = boardService.findBoardFile(id);
            model.addAttribute("boardFileList", boardFileDTOList);
        }
        List<CommentDTO> commentDTOList = commentService.commentFindAll(id);
        model.addAttribute("commentList", commentDTOList);
        return "boardpages/boardDetail";
    }

    @GetMapping("/boardUpdate")
    public String boardUpdateform(@RequestParam("id") Long id, Model model) {
        System.out.println("보드상세에서받아온id = " + id);
        BoardDTO boardDTO = boardService.findById(id);
        System.out.println("보드디비에서가져온필드값들 = " + boardDTO);
        MemberDTO memberDTO = memberService.findMemberById(boardDTO.getMemberId());
        System.out.println("회원디비에서가져온필드값들 = " + memberDTO);
        model.addAttribute("boardUpdate", boardDTO);
        model.addAttribute("bmember", memberDTO);
        return "boardpages/boardUpdateForm";
    }

    @PostMapping("/boardUpdate")
    public String boardUpdate(@ModelAttribute BoardDTO boardDTO) {
        boardService.boardUpdate(boardDTO);
        return "redirect:/findAll";
    }

    @GetMapping("/boardDelete")
    public String boardDelete(@RequestParam("id") Long boardId) {
        boardService.boardDel(boardId);
        return "redirect:/findAll";
    }


}
