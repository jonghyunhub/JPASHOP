package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
/** JPA 의 데이터 변경이 로직은 트렉젝션안에서 이루어져야 하므로 Transactional 애너테이션을 붙여준다.
 * 클래스 레벨에서 Transactional 애너테이션을 넣어주면 모든 메서드에 트렌젝션이 걸려들어간다.
 *  데이터 조회만 하는곳에서는 readonly = true 속성을 넣어주면 읽기전용으로 최적화를 해줌
 *      * 이때, 데이터를 변경해주는곳에서는 넣어주면 안된다!!
 *      RequiredArgsConstructor 은 final이 붙은 녀석들만 한정으로 lombok의 생성자 생성해주는 애너테이션
 * **/
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;


    /**
     * 회원 가입
     * 데이터 변경을 하는곳에서는 readonly 속성을 넣어주면 안되므로 Transactional 를 덮어써준다
     * 디폴트 값은 readonly = false
     **/
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 맴버 중복 검증 매서드
     **/
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    /**
     * 회원 전체 조회
     **/
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 맴버 하나 조회
     **/
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
