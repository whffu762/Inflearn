package hello.jdbc.service;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 * 기본 동작, 트랜잭션이 없어서 문제 발생
 */
class MemberServiceV1Test {

    public static final String Member_A = "memberA";
    public static final String Member_B = "memberB";
    public static final String Member_C = "memberC";
    public static final String Member_EX = "ex";

    private MemberRepositoryV1 memberRepository;
    private MemberServiceV1 memberService;

    @BeforeEach
    void before(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV1(dataSource);
        memberService = new MemberServiceV1(memberRepository);
    }
    @AfterEach
    void after() throws SQLException{
        memberRepository.delete(Member_A);
        memberRepository.delete(Member_B);
        memberRepository.delete(Member_EX);
    }

    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        //given
        Member memberA = new Member(Member_A, 10000);
        Member memberEx = new Member(Member_EX, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);
        //when

        memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000);
        //then

        Member a = memberRepository.findById(memberA.getMemberId());
        Member b = memberRepository.findById(memberEx.getMemberId());

        assertThat(a.getMoney()).isEqualTo(8000);
        assertThat(b.getMoney()).isEqualTo(10000);
    }

    @Test
    @DisplayName("비정상 이체")
    void accountTransfer2() throws SQLException {
        //given
        Member memberA = new Member(Member_A, 10000);
        Member memberEx = new Member(Member_EX, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);
        //when

        assertThatThrownBy(()-> memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);


        Member a = memberRepository.findById(memberA.getMemberId());
        Member b = memberRepository.findById(memberEx.getMemberId());

        assertThat(a.getMoney()).isEqualTo(8000);
        assertThat(b.getMoney()).isEqualTo(10000);
    }



}