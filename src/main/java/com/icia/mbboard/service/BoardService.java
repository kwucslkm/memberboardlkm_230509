package com.icia.mbboard.service;

import com.icia.mbboard.dto.BoardDTO;
import com.icia.mbboard.dto.BoardFileDTO;
import com.icia.mbboard.dto.PageDTO;
import com.icia.mbboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public List<BoardDTO> boardFindAll() {
        List<BoardDTO> boardDTOList = boardRepository.boardFindAll();
        return boardDTOList;
    }

    public void boardSave(BoardDTO boardDTO) throws IOException {
        if (boardDTO.getBoardFile().get(0).isEmpty()) {
            //file 없음
            boardDTO.setFileAttached(0);
            boardRepository.boardSave(boardDTO);
        } else {
            boardDTO.setFileAttached(1);
            BoardDTO dto = boardRepository.boardSave(boardDTO);// id값을 증가 시켜 가져온 dto 를 담는다.
            for (MultipartFile multipartFile : boardDTO.getBoardFile()) {
                String boardFileName = multipartFile.getOriginalFilename();
                String boardStoredFileName = System.currentTimeMillis() + "-" + boardFileName;
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setBoardFileName(boardFileName);
                boardFileDTO.setBoardStoredFileName(boardStoredFileName);
                boardFileDTO.setBoardId(dto.getId());
                String savePath = "D:\\springframework_img\\" + boardStoredFileName;
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
        System.out.println("컨트롤러에서 여기서비스로넘어온" + boardDTO);
        boardRepository.boardUpdate(boardDTO);

    }

    public void boardDel(Long boardId) {
        boardRepository.boardDel(boardId);
    }

    public Map<String, Object> pagingListmap(int page, String type, String q) {
        int pageLimit = 5;//한페이지에 보여 줄 글 갯수
        int pageStart = (page - 1) * pageLimit;
        Map<String, Object> pagingParam = new HashMap<>();
        pagingParam.put("start", pageStart);
        pagingParam.put("limit", pageLimit);
        pagingParam.put("q", q);
        pagingParam.put("type", type);
        return pagingParam;
    }
    public List<BoardDTO> pagingList(int page, String type, String q) {
        Map<String, Object> pagingParam = pagingListmap(page, type, q);
        List<BoardDTO> boardDTOList;
        if (q.equals("")) {
            boardDTOList = boardRepository.pagingList(pagingParam);
        } else {
            boardDTOList = boardRepository.searchList(pagingParam);
        } return boardDTOList;
    }

    public PageDTO pagingSearchParam(int page, String type, String q) {
        int pageLimit = 5; // 한페이지에 보여줄 글 갯수
        int blockLimit = 3; // 하단에 보여줄 페이지 번호 갯수
        int boardCnt = 0;
        if (q.equals("")) {
            boardCnt = boardRepository.boardCnt();

        } else {
            Map<String, Object> pagingParams = new HashMap<>();
            pagingParams.put("q", q);
            pagingParams.put("type", type);
            boardCnt = boardRepository.boardSearchCount(pagingParams);
        }
        int maxPage = (int) (Math.ceil((double) boardCnt / pageLimit));
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = startPage + blockLimit - 1;
        if (endPage > maxPage) {
            endPage = maxPage;
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        pageDTO.setMaxPage(maxPage);
        pageDTO.setStartPage(startPage);
        pageDTO.setEndPage(endPage);
        return pageDTO;
    }
    //    public List<BoardDTO> searchList(int page, String type, String q) {
//        Map<String, Object> pagingParams = pagingListmap(page);
//        pagingParams.put("q", q);
//        pagingParams.put("type", type);
//        List<BoardDTO> boardDTOList = boardRepository.searchList(pagingParams);
//        return boardDTOList;
//    }

//    public PageDTO pagingParam(int page) {
//        int pageLimit = 5; //한페이지에 보여줄 글 갯수
//        int blockLimit = 3; //하단에 페이지 갯수
//        //전체 글 갯수 조회
//        int boardCnt = boardRepository.boardCnt();
//        int maxPage = (int) (Math.ceil((double) boardCnt / pageLimit));
//        int startPage = ((int) (Math.ceil((double) page / blockLimit)) - 1) * blockLimit + 1;
//        int endPage = startPage + blockLimit - 1;
//        if (endPage > maxPage) {
//            endPage = maxPage;
//        }
//        PageDTO pageDTO = new PageDTO();
//        pageDTO.setPage(page);
//        pageDTO.setMaxPage(maxPage);
//        pageDTO.setStartPage(startPage);
//        pageDTO.setEndPage(endPage);
//        return pageDTO;
//    }
//    public PageDTO pagingSearchParam(int page, String type, String q) {
//        int pageLimit = 5; // 한페이지에 보여줄 글 갯수
//        int blockLimit = 3; // 하단에 보여줄 페이지 번호 갯수
//        Map<String, Object> pagingParams = new HashMap<>();
//        pagingParams.put("q", q);
//        pagingParams.put("type", type);
//        // 전체 글 갯수 조회
//        int boardCount = boardRepository.boardSearchCount(pagingParams);
//        // 전체 페이지 갯수 계산
//        int maxPage = (int) (Math.ceil((double)boardCount / pageLimit));
//        // 시작 페이지 값 계산(1, 4, 7, 10 ~~)
//        int startPage = (((int)(Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
//        // 마지막 페이지 값 계산(3, 6, 9, 12 ~~)
//        int endPage = startPage + blockLimit - 1;
//        // 전체 페이지 갯수가 계산한 endPage 보다 작을 때는 endPage 값을 maxPage 값과 같게 세팅
//        if (endPage > maxPage) {
//            endPage = maxPage;
//        }
//        PageDTO pageDTO = new PageDTO();
//        pageDTO.setPage(page);
//        pageDTO.setMaxPage(maxPage);
//        pageDTO.setEndPage(endPage);
//        pageDTO.setStartPage(startPage);
//        return pageDTO;
//    }
}
