package org.zerock.mixx.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.mixx.domain.Member;
import org.zerock.mixx.domain.MemberRole;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //일반회원 추가 테스트
    @Test
    public void insertMembers() {

        IntStream.rangeClosed(1,10 ).forEach(i -> {

            Member member = Member.builder()
                    .mid("member" + i)
                    .mpw(passwordEncoder.encode("abc123456!"))
                    .name("member" + i)
                    .email("email" + i + "@aaa.bbb")
                    .name("member" + i)
                    .build();

            member.addRole(MemberRole.USER);
            if (i >= 9) {
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }
//    회원조회 테스트 멤버10이 유저 권한, 어드민 권한 을 다가지고 잇음
    @Test
    public void testRead(){

        Optional<Member> result = memberRepository.getWithRoles("member10");
        Member member = result.orElseThrow();

        log.info(member);
        log.info(member.getRoleSet());

        member.getRoleSet().forEach(memberRole -> log.info(memberRole.name()));

    }
    }
