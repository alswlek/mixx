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
import org.zerock.mixx.security.handler.Custom403Handler;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) //boardcontrlloer -> register 에 @prepost처리
public class CustomSecurityConfig {
//customuserdetailservice 가 정상적으로 동작하려면 bean 지정하고 customuserdetailservice에 주입
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //인증처리를 위한 userdetailservice
//    filterchain 적용하면 /board/list 에 접근할수 있게
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("------------configure-------------------");
        http.formLogin().loginPage("/member/login"); // board의register 가면 /member/login 이동 -> post 방식으로 처리p699
        http.formLogin().defaultSuccessUrl("/index.html"); // 로그인 성공 시 이동할 URL
        http.formLogin().failureUrl("/member/login?error"); // 페이지 만들어야 함
        http.csrf().disable(); //csrf 비활성화 -> username,password만으로 로그인가능

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()); //403
        return http.build();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new Custom403Handler();
    }

    //    정적자원처리 css,js 파일 필터 제외
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("------------web configure-------------------");
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}