package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    /**
     * @PersistenceContext 를 붙이면 스프링이 JPA ENTITY MANAGER 를 주입해줌
     **/
    @PersistenceContext
    private EntityManager em;

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
