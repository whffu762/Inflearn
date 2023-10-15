package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.URI_TYPE;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach(){
        //항상 새로운 커넥션 생성
        //DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        //커넥션 풀 사용
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        //나머지는 default
        
        //DriverManagerDataSource 를 쓰건 HikariDataSource 를 쓰건 
        //Repository 에 전달되는 DataSource 의 종류와 상관없이 Repository 는 제대로 동작함
        //커넥션 생성 방식과 커넥션 풀 방식의 인터페이스가 통일됐기 때문에 가능
        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void save() throws SQLException {
        Member member = new Member("memberV3", 10000);
        repository.save(member);

        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember = {} ", findMember);
        log.info("member == findMember {} ", member == findMember); //false
        log.info("member equals findMember {} ", member.equals(findMember));    //true
        assertThat(findMember).isEqualTo(member);

        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        repository.delete(member.getMemberId());
        assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);

    }
}