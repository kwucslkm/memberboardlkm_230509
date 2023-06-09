package com.icia.mbboard.controller;

import com.icia.mbboard.dto.CommentDTO;
import com.icia.mbboard.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/commentSave")
    public ResponseEntity<List<CommentDTO>> commentSave(@ModelAttribute CommentDTO commentDTO){

        System.out.println("commentDTO = " + commentDTO);

        commentService.commentSave(commentDTO);
        Long id = commentDTO.getBoardId();
//        System.out.println("댓글찾아올id = " + id);
        List<CommentDTO> commentDTOList = commentService.commentFindAll(id);
//        System.out.println("commentDTOList = " + commentDTOList);
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }
    @PostMapping("/commentDel")
    public ResponseEntity<List<CommentDTO>> commentDelete(@ModelAttribute CommentDTO commentDTO){
//        System.out.println("삭제할 댓글 id"+commentDTO.getId());
        commentService.commentDelete(commentDTO.getId());
//        System.out.println("다시찾아올 보드id"+commentDTO.getBoardId());
        List<CommentDTO> commentDTOList = commentService.commentFindAll(commentDTO.getBoardId());
//        System.out.println("commentDTOList = " + commentDTOList);
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }
}
