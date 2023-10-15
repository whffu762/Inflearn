package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import hello.jdbc.repository.MemberRepositoryV3;
import hello.jdbc.repository.MemberRepositoryV4_1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * MemberRepository 추상화 - Repository 에서 예외를 기술 독립적으로 구현함으로써 인터페이스를 통한 의존성 주입
 * MemberRepositoryV4_1, _2 가 인터페이스의 구현체임
 */

@Slf4j
public class MemberServiceV4 {

    private final MemberRepository memberRepository;

    public MemberServiceV4(MemberRepository memberRepositoryV){
        this.memberRepository = memberRepositoryV;
    }

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) {
        bizLogic(fromId, toId, money);
    }

    private void bizLogic(String fromId, String toId, int money)  {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }


    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("exception!!!!!!");
        }
    }
}
