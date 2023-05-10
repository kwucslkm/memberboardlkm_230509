package com.icia.mbboard.repository;

import com.icia.mbboard.dto.BoardDTO;
import com.icia.mbboard.dto.BoardFileDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepository {
    @Autowired
    private SqlSessionTemplate sql;



    public List<BoardDTO> boardFindAll() {
        List<BoardDTO> boardDTOList = sql.selectList("Mb_board.boardFindAll");
        System.out.println("디비에서 가져온게 = " + boardDTOList);
        return boardDTOList;
    }

    public BoardDTO boardSave(BoardDTO boardDTO) {
        sql.insert("Mb_board.boardSave", boardDTO);
        return boardDTO;
    }

    public void boardSaveFile(BoardFileDTO boardFileDTO) {
        if (sql.insert("Mb_board.boardFileSave", boardFileDTO) == 1)
            System.out.println("게시글파일저장성공");
    }
    public void boardCntHits(Long id) {
        System.out.println((sql.update("Mb_board.boardCntHits",id)));
    }

    public BoardDTO findById(Long id) {
        BoardDTO boardDTO = sql.selectOne("Mb_board.boardFindbyId",id);
        return boardDTO;
    }

    public List<BoardFileDTO> findBoardFile(Long id) {
        List<BoardFileDTO> boardFileDTOList = sql.selectList("Mb_board.findBoardFile", id);
        return boardFileDTOList;
    }
}
