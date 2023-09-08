package org.zerock.mixx.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mixx.dto.MemberJoinDTO;
import org.zerock.mixx.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {
    //의존성 주입
    private final MemberService memberService;

    @GetMapping("/join")
    public void joinGET() {

        log.info("join get...");
    }

    @PostMapping("/join")
    public String joinPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {

        log.info("join post...");
        log.info(memberJoinDTO);

        try {
            memberService.join(memberJoinDTO);
        } catch (MemberService.MidExistException e) {
            redirectAttributes.addFlashAttribute("error", "mid");
            return "redirect:/member/join";
        }
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/member/login"; //회원 가입 후 로그인
    }

    @GetMapping("/login") //post 방식은 시큐리티가 자동적으로 처리함
    public void loginGET(String errorCode, String logout) {
        log.info("login get..............");
        log.info("logout: " + logout); //로그인요청중 로그아웃요청이 없으므로 logout ==null

        if (logout != null) {
            log.info("user logout!!!!!!!!!!!!!!!");
        }
    }
//    @GetMapping("/member/login?logout") // 로그아웃을 위한 컨트롤러
//    public String logoutGET(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/index"; // 로그아웃 후 로그인 페이지로 리다이렉트
////        return "redirect:/member/login";
//    }
    @PostMapping("/logout") // POST 요청으로 로그아웃을 처리하는 컨트롤러
    public String logoutPOST(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/member/login?logout"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }

    }

