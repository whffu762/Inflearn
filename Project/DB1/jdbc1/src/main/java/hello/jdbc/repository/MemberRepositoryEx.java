package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;

public interface MemberRepositoryEx {
//이런식으로 인터페이스에서도 SQLException 을 던져야 이 인터페이스의 구현체에서도 던질 수 있음
    Member save(Member member) throws SQLException;
    Member findById(String id) throws SQLException;
    void update(String id) throws SQLException;
    void delete(String id) throws SQLException;
}
