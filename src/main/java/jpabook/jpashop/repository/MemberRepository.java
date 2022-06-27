package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
/**
 * RequiredArgsConstructor 은 final이 붙은 녀석들만 한정으로 lombok 의 생성자 생성해주는 애너테이션
 * 스프링 데이터 JPA 가 다른 의존성 주입처럼 EntityManager 도 DI 할수있게 해줌
 * **/
@RequiredArgsConstructor
public class MemberRepository {

    /**
     * @PersistenceContext 를 붙이면 스프링이 JPA ENTITY MANAGER 를 주입해줌
     **/
//    @PersistenceContext 이렇게 해도 되고 아니면 일관성있게 위처럼 해도 됨
    private final EntityManager em;

    /**
     * 맴버 저장
     **/
    public void save(Member member) {
        em.persist(member);
    }

    /**
     * 맴버 단건 조회
     **/
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    /**
     * 맴버 여러건 조회
     **/
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    /**
     * 이름으로 맴버 조회
     **/
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
