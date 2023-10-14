package jpbook.jpashop.service;

import jpbook.jpashop.domain.Member;
import jpbook.jpashop.repository.MemberRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
// JPA의 모든 데이터변경 로직은 가급적 트랜잭션에서 실행되어야 한다.
// 클래스레벨에서 어노테이션을 걸면 모든 퍼블릭 메서드가 트랜잭션이 걸린다.
// 스프링 어노테이션을 이용해야함(javax는 안됨!)
// readOnly는 조회시 성능 최적화(조회시에만!)
@RequiredArgsConstructor // final이 붙은 필드 생성자를 자동생성함
public class MemberService {

    // @Autowired 쓰면 안됨.
    // 필드 인젝션(생성자 주입) 자동적으로 set을 생성하기 때문에 쓰면 안된다.
    private final MemberRepositoy memberRepositoy;

    // setter 인젝션 예시 (사용 지양)
    // @Autowired
    // public void setMemberRepositoy(MemberRepositoy memberRepositoy){
    //      this.memberRepository = memberRepositoy;
    // }

    // Autowired 생성자 주입 -> 그나마 이게 적절함
    // lombok 어노테이션 으로 인해서 생성자 생략
    // public MemberService(MemberRepositoy memberRepositoy) {
    //      this.memberRepository = memberRepositoy;
    // }

    //회원가입
    @Transactional // readOnly가 되면 안되므로 따로 걸음
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepositoy.seve(member);
        return member.getId();
    }

    // 멀티쓰레드 상황을 고려해서 DB에 name을 유니크 제약조건을 거는것이 좋다
    // 그렇지 않으면 두 회원이 동일한 이름으로 동시에 가입 하는 경우, validate를 통과할수도 있다
    private void validateDuplicateMember(Member member){

        List<Member> finMembers = memberRepositoy.findByName(member.getName());
        if(!finMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }


    // 회원 전체조회
    public List<Member> finMembers(){return memberRepositoy.findAll();}

    public Member findOne(Long memberId) {return memberRepositoy.findOne(memberId);}

    @Transactional

    public void update(Long id, String name){
        Member member = memberRepositoy.findOne(id);
        member.setName(name); // 변경감지
    }


}
