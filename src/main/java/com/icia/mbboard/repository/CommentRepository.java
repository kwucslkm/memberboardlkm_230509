package com.icia.mbboard.repository;

import com.icia.mbboard.dto.CommentDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {
@Autowired
private SqlSessionTemplate sql;
    public void commentSave(CommentDTO commentDTO) {
        System.out.println("reposi_commentDTO = " + commentDTO);
        int resultCommentSave = sql.insert("Mb_Comment.commentSave",commentDTO);
        System.out.println("resultCommentSave = " + resultCommentSave);
    }

    public List<CommentDTO> commentFindAll(Long boardId) {
//        System.out.println("reposi댓글찾아올id  = " + boardId);
        List<CommentDTO> dtoList = sql.selectList("Mb_Comment.findAll",boardId);
//        for (CommentDTO l : dtoList){
//            System.out.println("댓글값"+l.getBoardId());
//            System.out.println(l.toString());
//        }
//        System.out.println("디비에서 받아온commentDTO 첫번째 = " + dtoList.get(0).toString());
        return dtoList;
    }
}
