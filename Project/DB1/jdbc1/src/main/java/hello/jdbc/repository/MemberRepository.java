package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;

public interface MemberRepository {
//이런식으로 인터페이스에서도 SQLException 을 던져야 이 인터페이스의 구현체에서도 던질 수 있음
    Member save(Member member);
    Member findById(String id);
    void update(String id, int money);
    void delete(String id);
}
