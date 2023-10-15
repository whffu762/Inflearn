package hello.servlet.domain.member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;    //alt + Enter를 통해 Assertion import 간소화

//구현한 기능이 제대로 작동하는지 테스트
//HashMap의 내장 메소드를 확인하는게 필요

public class MemberRepositoryTest {
    MemberRepository memberRepository = MemberRepository.getInstance();
//    MemberRepository memberR = new MemberRepository();
    //MemberRepository는 싱글톤이기 때문에 새로운 객체는 생성하지 못함

    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }   //순서가 보장이 안되기 때문에 한번 테스트 할 때마다 Repository 클리어 해줘야 함

    @Test
    void save(){
        Member member = new Member("hello", 20);

        Member savedMember = memberRepository.save(member); //member 생성
        Member findMember = memberRepository.findById(savedMember.getId()); //생성된 member를 가져옴

        assertThat(findMember).isEqualTo(savedMember);   //뒤에 명시되는 조건이 참인지 확인하는 패키지 Assertion - 개발자가 참이라고 생각하는 test를 작성하고 생각대로 동작하는지 확인
        //findMember의 값과 savdMember의 값이 같은지 확인
    }

    @Test
    void findAll(){
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 20);
        Member member3 = new Member("member3", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1, member2);

    }
}
