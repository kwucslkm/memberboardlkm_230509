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
            BoardDTO dto = boardRepository.boardSave(boardDTO);// id값을 증가 시켜 가져온 dto 를 담는다.
            for(MultipartFile multipartFile : boardDTO.getBoardFile()){
                String boardFileName = multipartFile.getOriginalFilename();
                String boardStoredFileName = System.currentTimeMillis()+"-"+boardFileName;
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setBoardFileName(boardFileName);
                boardFileDTO.setBoardStoredFileName(boardStoredFileName);
                boardFileDTO.setBoardId(dto.getId());
                String savePath = "D:\\springframework_img\\"+boardStoredFileName;
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

    public void boardUpdate(BoardDTO boardDTO) {
        System.out.println("컨트롤러에서 여기서비스로넘어온"+boardDTO);
        boardRepository.boardUpdate(boardDTO);

    }

    public void boardDel(Long boardId) {
        boardRepository.boardDel(boardId);

    }
}
