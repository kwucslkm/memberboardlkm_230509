package com.icia.mbboard.service;

import com.icia.mbboard.dto.CommentDTO;
import com.icia.mbboard.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public void commentSave(CommentDTO commentDTO) {
        commentRepository.commentSave(commentDTO);


    }

    public List<CommentDTO> commentFindAll(Long boardId) {
//        System.out.println("service댓글찾아올id = " + boardId);
        List<CommentDTO> commentDTOList = commentRepository.commentFindAll(boardId);
        return commentDTOList;
    }

    public void commentDelete(Long id) {
        commentRepository.commentDelete(id);
    }

    public Integer findCommentCntByBoardId(Long id) {
        return commentRepository.findCommentCntByboardId(id);
    }
}
