package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/**
 * 트랜잭션 템플릿 - 예외 여부에 따른 commit(), rollback() 제거
 * 하지만 쿼리 실행 도중 예외는 던져야 해서 여전이 try - catch 문이 존재
 */

//@RequiredArgsConstructor 이걸로 의존성 주입해도 되긴 함
//근데 TransactionTemplate 객체를 생성할 때 TransactionManager 를 생성자 인자로 줘서 유연성을 주기 위함
@Slf4j
public class MemberServiceV3_2 {

//    private final PlatformTransactionManager transactionManager;
    private final TransactionTemplate txTemplate;
    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepositoryV3){
        this.txTemplate = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepositoryV3;
    }
    public void accountTransfer(String fromId, String toId, int money) {

        txTemplate.executeWithoutResult((status) -> {
            try{
                bizLogic(fromId, toId, money);
            } catch (SQLException e){
                throw new IllegalStateException(e);
            }
        });
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
