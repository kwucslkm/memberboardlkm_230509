package com.icia.mbboard.repository;

import com.icia.mbboard.dto.BoardDTO;
import com.icia.mbboard.dto.BoardFileDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
        System.out.println("file 찾을 id = " + id);
        List<BoardFileDTO> boardFileDTOList = sql.selectList("Mb_board.findBoardFile", id);
        return boardFileDTOList;
    }

    public void boardUpdate(BoardDTO boardDTO) {
        System.out.println("reposi까지왔어힘내"+boardDTO);
        int updateresult = sql.update("Mb_board.boardUpdate",boardDTO);
        if (updateresult==1){
            System.out.println("업데이트성공");
        }else{
            System.out.println("업데이트 실패");
        }

    }

    public void boardDel(Long boardId) {
        System.out.println("boardId = " + boardId);
        if(sql.delete("Mb_board.boardDel",boardId)==1)
            System.out.println("삭제완료");
        else
            System.out.println("삭제실패");
    }

    public List<BoardDTO> pagingList(Map<String, Object> pagingParam) {
//        pagingParam.put("start",pageStart);
//        pagingParam.put("limit",pageLimit);
        List<BoardDTO> boardDTOList = sql.selectList("Mb_board.pagingList",pagingParam);
        System.out.println("boardDTOList = " + boardDTOList);
        return boardDTOList;
    }
    public int boardCnt() {
        int boardCnt = sql.selectOne("Mb_board.boardCnt");
        return boardCnt;
    }
    public List<BoardDTO> searchList(Map<String, Object> pagingParams) {
        System.out.println("pagingParams = " + pagingParams);
        return sql.selectList("Mb_board.search", pagingParams);
    }

    public int boardSearchCount(Map<String, Object> pagingParams) {
        return sql.selectOne("Mb_board.searchCount", pagingParams);

    }
}
