package org.zerock.mixx.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.zerock.mixx.security.CustomUserDetailsService;
import org.zerock.mixx.security.handler.Custom403Handler;
@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) //boardcontrlloer -> register 에 @prepost처리(사전사후권한체크)

public class CustomSecurityConfig {
    //customuserdetailservice 가 정상적으로 동작하려면 customuserdetailservice에 주입
    private final CustomUserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //인증처리를 -> userdetailservice
// filterchain 적용하면 /board/list 에 접근할수 있게
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("------------configure-------------------");
        http
                .authorizeRequests()
                    .antMatchers("/index.html").permitAll() // 로그인 없이 접근 가능한 페이지 설정
// .antMatchers("/board/**").authenticated() //로그인하면 게시판볼수있게
                    .anyRequest().permitAll()
                    .and()

                .formLogin()
                    .loginPage("/member/login") // 로그인 페이지 URL 설정
                    .defaultSuccessUrl("/index.html") // 로그인 성공 시 이동할 URL
                    .failureUrl("/member/login?error") //로그인 실패 후 이동할 URL 설정
                    .and()

                .logout()
                    .permitAll()
                    .logoutUrl("/member/logout") // 로그아웃 URL 설정
//                    .logoutSuccessUrl("/index.html") // 로그아웃 성공 시 리다이렉트 주소
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/member/login?logout")) // 로그아웃 URL 변경

                .invalidateHttpSession(true) // 세션 날리기
                .deleteCookies("JSESSIONID"); //쿠키 삭제

        http.csrf().disable(); //csrf 비활성화 -> username,password만으로 로그인가능
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()); //403
        return http.build();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new Custom403Handler();
    }

    // 정적자원처리 css,js 파일 필터 제외
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}