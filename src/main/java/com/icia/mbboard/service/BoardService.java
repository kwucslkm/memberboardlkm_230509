package com.icia.mbboard.service;

import com.icia.mbboard.dto.BoardDTO;
import com.icia.mbboard.dto.BoardFileDTO;
import com.icia.mbboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    public List<BoardDTO> boardFindAll() {

        List<BoardDTO> boardDTOList = boardRepository.boardFindAll();
        return boardDTOList;
    }

    public void boardSave(BoardDTO boardDTO) throws IOException {
        if(boardDTO.getBoardFile().get(0).isEmpty()){
            //file 없음
            boardDTO.setFileAttached(0);
            boardRepository.boardSave(boardDTO);
        }else{
            boardDTO.setFileAttached(1);
            BoardDTO dto = boardRepository.boardSave(boardDTO);
            for(MultipartFile multipartFile : boardDTO.getBoardFile()){
                String boarFileName = multipartFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis()+"-"+boarFileName;
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setBoardFileName(boarFileName);
                boardFileDTO.setBoardStoredFileName(storedFileName);
                boardFileDTO.setBoardId(dto.getId());
                String savePath = "D:\\springframework_img"+storedFileName;
                multipartFile.transferTo(new File(savePath));
                boardRepository.boardSaveFile(boardFileDTO);
            }
        }
    }

    public void boardCntHits(Long id) {
        boardRepository.boardCntHits(id);
    }
    public BoardDTO findById(Long id) {
        BoardDTO boardDTO = boardRepository.findById(id);
        return boardDTO;
    }
    public List<BoardFileDTO> findBoardFile(Long id) {
        List<BoardFileDTO> boardFileDTOList = boardRepository.findBoardFile(id);
        return boardFileDTOList;
    }
}
