package org.zerock.mixx.dto;

import lombok.Data;

@Data
public class MemberJoinDTO {

    private String mid;
    private String mpw;
    private String name;
    private String email;
    private boolean del;
    private boolean social;

}