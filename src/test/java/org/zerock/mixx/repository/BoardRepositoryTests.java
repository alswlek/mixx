package org.zerock.mixx.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.mixx.domain.Board;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer("user" + (i % 10))
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO: " + result.getBno());
        });
    }

    @Test
    public void testSelect() {
        Long bno = 2L;

        Optional<Board> result = boardRepository.findById(bno);

        if (result.isPresent()) {
            Board board = result.get();
            log.info(board);
        } else {
            log.info("Board not found with bno: " + bno);
        }

    }

    @Test
    public void testUpdate() {

        Long bno = 2L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        board.change("update..title 2", "update content 2");

        boardRepository.save(board);

    }

    @Test
    public void testDelete() {
        for (Long bno = 105L; bno <= 500L; bno++) {
            Optional<Board> boardOptional = boardRepository.findById(bno);
            if (boardOptional.isPresent()) {
                boardRepository.deleteById(bno);
            }
        }
    }

    @Test
    public void testPaging() {
        //1 page order by bno desc
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);


        log.info("total count: "+result.getTotalElements());
        log.info( "total pages:" +result.getTotalPages());
        log.info("page number: "+result.getNumber());
        log.info("page size: "+result.getSize());

        List<Board> todoList = result.getContent();

        todoList.forEach(board -> log.info(board));
    }
//
//    @Test
//    public void testSearch1() {
//
//        //2 page order by bno desc
//        Pageable pageable = PageRequest.of(1,10, Sort.by("bno").descending());
//
//        boardRepository.search1(pageable);
//
//    }
//
    @Test
    public void testSearchAll() {

        String[] types = {"t","c","w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable );
//
//    }
//
//    @Test
//    public void testSearchAll2() {
//
//        String[] types = {"t","c","w"};
//
//        String keyword = "1";
//
//        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
//
//        Page<Board> result = boardRepository.searchAll(types, keyword, pageable );
//
//        //total pages
//        log.info(result.getTotalPages());
//
//        //pag size
//        log.info(result.getSize());
//
//        //pageNumber
//        log.info(result.getNumber());
//
        //prev next
        log.info(result.hasPrevious() +": " + result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }
}

