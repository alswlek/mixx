package org.zerock.mixx.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
//entity는 db테이블을 뜻함, getter 은 db테이블 이름 명시
// lombok의 getter을 이용해 매소드 생성하고 빌더를 이용해서 각체생성 ,allargs,noargs를 같이 처리해야 에러발생안함
//jpa hiberate 엔티티 생성
//id는 pk 뜻함, generatedvalue 는 pk의 strategy 설정하고자 할때 사용
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    @Column(length = 500, nullable = false) //컬럼의 길이와 null허용여부
    private String title;
    @Column(length = 2000, nullable = false)
    private String content;
    @Column(length = 50, nullable = false)
    private String writer;

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }
}
