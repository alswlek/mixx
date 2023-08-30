package org.zerock.mixx.controller;

import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mixx.dto.BoardDTO;
import org.zerock.mixx.dto.PageRequestDTO;
import org.zerock.mixx.dto.PageResponseDTO;
import org.zerock.mixx.service.BoardService;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.Valid;


//컨트롤러 -> 매핑처리 ,view에 전달
//client 요청을 받았을때 요청에 대해 실제 업무를 수행하는 service 호출
//클라이언트가 보낸 데이터가 있다면 service를 호출할때 전달하기 쉽게 데이터 가공 후에 뷰에 전달
@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/register")
    public void registerGET(){
    }
    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        log.info("board POST register.......");

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
            return "redirect:/board/register";
        }

        log.info(boardDTO);
        Long bno  = boardService.register(boardDTO);
        redirectAttributes.addFlashAttribute("result", bno);
        return "redirect:/board/list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model){

        BoardDTO boardDTO = boardService.readOne(bno);
        log.info(boardDTO);
        model.addAttribute("dto", boardDTO);

    }
    @PreAuthorize("principal.username == #boardDTO.writer")
    @PostMapping("/modify")
    public String modify( PageRequestDTO pageRequestDTO,
                          @Valid BoardDTO boardDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){
        log.info("board modify post......." + boardDTO);

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
            redirectAttributes.addAttribute("bno", boardDTO.getBno());
            return "redirect:/board/modify?"+link;
        }

        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        return "redirect:/board/read";
    }

    @PreAuthorize("principal.username == #boardDTO.writer")
    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {

        log.info("remove post.. " + bno);
        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/board/list";
    }
}