package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

/**
 * 스프링 통합 테스트 하게 해주는 에너테이션 두개
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
/** 트렌젝션 에너테이션은 데이터 변경을 할수있게 해줌**/
@Transactional
public class MemberServiceTest {

    /**
     * 테스트 코드라 DI를 필드주입으로 해도 무관
     **/
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long memberId = memberService.join(member);

        //then
        em.flush();
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(memberId));
//        assertEquals(member,memberRepository.findOne(memberId));
    }

    /**
     * Test 에너테이션이 try-catch 문 역할 해줌
     **/
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);
//        try {
//            memberService.join(member2); //예외가 발생해야 한다.
//        } catch (IllegalStateException e) {
//            return ;
//        }

        //then
        Assert.fail("예외가 발생해야 한다.");
    }
}