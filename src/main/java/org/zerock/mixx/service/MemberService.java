package org.zerock.mixx.service;

import org.zerock.mixx.dto.MemberJoinDTO;

public interface MemberService {
    static class MidExistException extends Exception {

    }

    void join(MemberJoinDTO memberJoinDTO)throws MidExistException ;

}