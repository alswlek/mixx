package org.zerock.mixx.service;

import org.zerock.mixx.dto.BoardDTO;
import org.zerock.mixx.dto.PageRequestDTO;
import org.zerock.mixx.dto.PageResponseDTO;

//서비스 -> 컨트롤러와 레포지토리 사이의 미들웨어
public interface BoardService {
        Long register(BoardDTO boardDTO);
        BoardDTO readOne(Long bno);
        void modify(BoardDTO boardDTO);
        void remove(Long bno);
        //boardservice 는 list 이름으로 목록 검색 가능
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
}