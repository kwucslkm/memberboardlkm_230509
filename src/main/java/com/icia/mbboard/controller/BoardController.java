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
import java.util.ArrayList;
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
    public String boardSaveForm(@ModelAttribute BoardDTO boardDTO, HttpSession session) throws IOException {
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
                             @RequestParam(value = "type", required = false, defaultValue = "boardTitle") String type,
                             @RequestParam(value = "pageMaxBoard", required = false, defaultValue = "5") int pageMaxBoard,
                             Model model) {
        System.out.println("pageMaxBoard = " + pageMaxBoard);
        List<BoardDTO> boardDTOList = boardService.pagingList(page, type, q, pageMaxBoard);
        PageDTO pageDTO = boardService.pagingSearchParam(page, type, q, pageMaxBoard);
        List<BoardCommentDTO> commentCnt = new ArrayList<>();
        for (BoardDTO boardId : boardDTOList) {
            Long boardid = boardId.getId();
            BoardCommentDTO commentDTO = new BoardCommentDTO();
            commentDTO.setBoardId(boardid);
            commentDTO.setBoardCommentCount(commentService.findCommentCntByBoardId(boardId.getId()));
            commentCnt.add(commentDTO);
        }
        model.addAttribute("pagingList", boardDTOList);
        model.addAttribute("paging", pageDTO);
        model.addAttribute("q", q);
        model.addAttribute("type", type);
        model.addAttribute("pageMaxBoard", pageMaxBoard);
        model.addAttribute("commentCnt", commentCnt);
        model.addAttribute("totalBoard", boardService.boardCnt());
        return "boardpages/boardpagingList";
    }

    @GetMapping("/board")
    public String findById(@RequestParam("id") Long id,
                           @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                           @RequestParam(value = "q", required = false, defaultValue = "") String q,
                           @RequestParam(value = "type", required = false, defaultValue = "boardTitle") String type,
                           @RequestParam(value = "pageMaxBoard", required = false, defaultValue = "3") int pageMaxBoard,
                           Model model, HttpSession session) {
        System.out.println("보드상세조회보드id = " + id);
        boardService.boardCntHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        MemberDTO memberDTO = memberService.findMemberById(boardDTO.getMemberId());
        System.out.println("boardDTO = " + boardDTO);
        System.out.println("memberDTO = " + memberDTO);
        model.addAttribute("member", memberDTO);
        model.addAttribute("page", page);
        model.addAttribute("q", q);
        model.addAttribute("type", type);
        model.addAttribute("boardDetail", boardDTO);
        model.addAttribute("pageMaxBoard", pageMaxBoard);
        if (boardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = boardService.findBoardFile(id);
            model.addAttribute("boardFileList", boardFileDTOList);
        }
        List<CommentDTO> commentDTOList = commentService.commentFindAll(id);//보드id로 댓글가져오기
        System.out.println("댓글가져온거 있나" + commentDTOList);
        model.addAttribute("commentList", commentDTOList);
        String loginEmailchk = (String) session.getAttribute("loginEmail");
        if (loginEmailchk != null) {
            Long loginMemberId = memberService.findIdBySessionEmail(loginEmailchk);
            System.out.println("세션에 있는 로그인 이메일로 가져온 memberId" + loginMemberId);
            model.addAttribute("loginMemberId", loginMemberId);
        } else {
            System.out.println("아직 로그인전입니다.");
        }
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
        return "redirect:/pagingList";
    }

    @GetMapping("/boardDelete")
    public String boardDelete(@RequestParam("id") Long boardId) {
        boardService.boardDel(boardId);
        return "redirect:/findAll";
    }

    @GetMapping("/boardFindByEmail")
    public String boardFindByEmail(@RequestParam("loginEmail") String loginEmail, Model model) {
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
//        System.out.println("memberDTO = " + memberDTO);
        Long memberId = memberDTO.getId();
//        System.out.println("memberId = " + memberId);
        List<BoardDTO> boardDTOList = boardService.findByBoardId(memberId);
        model.addAttribute("findbyboardId", boardDTOList);
        return "memberpages/memberWriteBoard";
    }
}
