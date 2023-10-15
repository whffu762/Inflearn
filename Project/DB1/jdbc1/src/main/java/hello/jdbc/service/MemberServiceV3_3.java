package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/**
 * 트랜잭션 AOP - @Transactional 
 * 트랜잭션 관련 연결 설정은 하나의 커넥션 보장을 위해 비즈니스 로직에서 되야 하는데 이게 필연적으로 
 * 비즈니스 로직에 트랜잭션 관련 코드가 들어가게 됨
 * 이를 해결하기 위해 커넥션과 commit(), rollback() 등 DB 관련 로직은 프록시 패턴에서 처리하고
 * 비즈니스 로직에는 순수 자바 코드만 포함되도록 만듦
 * 
 */

//@RequiredArgsConstructor 이걸로 의존성 주입해도 되긴 함
//근데 TransactionTemplate 객체를 생성할 때 TransactionManager 를 생성자 인자로 줘서 유연성을 주기 위함
@Slf4j
public class MemberServiceV3_3 {

    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_3(MemberRepositoryV3 memberRepositoryV3){
        this.memberRepository = memberRepositoryV3;
    }

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        bizLogic(fromId, toId, money);
    }

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
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
