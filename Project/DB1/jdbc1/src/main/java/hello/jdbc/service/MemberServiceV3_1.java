package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 추상화 - 트랜잭션 매니저
 * 어떤 DB 기술을 쓰건 TransactionManager 인터페이스를 이용하면 됨
 *
 * 트랜잭션 동기화 - 트랜잭션 매니저 + 트랜잭션 동기화 매니저(tsm)
 * 생성되는 모든 커넥션이 tsm 에 들어가 있어서 파라미터를 통해 전달하지 않아도 
 * 트랜잭션에 하나의 커넥션이 보장됨
 */

@RequiredArgsConstructor
@Slf4j
public class MemberServiceV3_1 {

    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepository;

    public void accountTransfer(String fromId, String toId, int money)throws SQLException {
        //트랜잭션 시작
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try{
            bizLogic(fromId, toId, money); //비즈니스 로직
            transactionManager.commit(status);                       //정상적으로 수행되면 commit
        } catch(Exception e){
            transactionManager.rollback(status);  //예외 발생하면 rollback
            throw new IllegalStateException(e);
        }
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
