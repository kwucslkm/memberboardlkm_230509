package com.icia.mbboard.controller;

import com.icia.mbboard.dto.BoardDTO;
import com.icia.mbboard.dto.BoardFileDTO;
import com.icia.mbboard.dto.CommentDTO;
import com.icia.mbboard.dto.MemberDTO;
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
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class boardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private  MemberService memberService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/findAll")
    public String  boardfindAll(Model model){
//        System.out.println("list 가질러 간다.");
        List<BoardDTO> boardDTOList = boardService.boardFindAll();
//            List<CommentDTO> commentDTOList
//        for(BoardDTO boardDTO : boardDTOList){
//            commentService.commentFindAll(boardDTO.getId());
//        }
//        System.out.println("cont까지온boardList = " + boardDTOList);
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
        System.out.println("멤버에서가져온데이터 = " + memberDTO);

        boardDTO.setMemberId(memberDTO.getId());
        boardDTO.setBoardWriter(memberDTO.getMemberName());
        System.out.println(" 컨트롤러딴 보드글번호"+boardDTO.getId());
        boardService.boardSave(boardDTO);
        return "redirect:/findAll";
    }
    @GetMapping("/board")
    public String findById(@RequestParam("id") Long id, Model model){
//        System.out.println("보드상세조회보드id = " + id);
        boardService.boardCntHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        MemberDTO memberDTO = memberService.findMemberById(boardDTO.getMemberId());
        model.addAttribute("member",memberDTO);
//        System.out.println("상세조회로 가져온 dto의 파일유무"+boardDTO.getFileAttached());
        model.addAttribute("boardDetail",boardDTO);
        if (boardDTO.getFileAttached() == 1){
            List<BoardFileDTO> boardFileDTOList = boardService.findBoardFile(id);
//            System.out.println("boardFileDTOList = " + boardFileDTOList);
            model.addAttribute("boardFileList", boardFileDTOList);
        }
        List<CommentDTO> commentDTOList =  commentService.commentFindAll(id);
        model.addAttribute("commentList", commentDTOList);
        return "boardpages/boardDetail";
    }
    @GetMapping("/boardUpdate")
    public  String boardUpdateform(@RequestParam("id") Long id, Model model){
        System.out.println("보드상세에서받아온id = " + id);
        BoardDTO boardDTO = boardService.findById(id);
        System.out.println("보드디비에서가져온필드값들 = " + boardDTO);
        MemberDTO memberDTO = memberService.findMemberById(boardDTO.getMemberId());
        System.out.println("회원디비에서가져온필드값들 = " + memberDTO);
        model.addAttribute("boardUpdate",boardDTO);
        model.addAttribute("bmember",memberDTO);
        return "boardpages/boardUpdateForm";
    }
    @PostMapping("/boardUpdate")
    public String boardUpdate(@ModelAttribute BoardDTO boardDTO){
        System.out.println("수정폼에서업데이트값가져온거"+boardDTO);

        boardService.boardUpdate(boardDTO);

        return "redirect:/findAll";
    }
    @GetMapping("/boardDelete")
    public String boardDelete(@RequestParam("id") Long boardId){
        System.out.println("detail에서 가져온 삭제할 id " + boardId);
        boardService.boardDel(boardId);
        return "redirect:/findAll";
    }


}
