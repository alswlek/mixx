package org.zerock.mixx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mixx.domain.Board;
import org.zerock.mixx.repository.search.BoardSearch;

//레파지토리 ->db 연결 or 해제, jparepository<entity,타입>상속받으면 기본적인 crud 자동 생성
//jpa repository -> hibernate 이용하기 위한 여러 api를 제공
// boardrepository 타입으로 entity, pk 타입을 지정해주면 자동으로 스프링 빈으로 등록됨.
public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

}
