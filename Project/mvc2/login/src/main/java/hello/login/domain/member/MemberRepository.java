package hello.login.domain.member;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();

    private static long seq = 0L;

    public Member save(Member member){

        member.setId(++seq);
        log.info("save: member = {}", member);
        store.put(member.getId(), member);

        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public Optional<Member> findByLoginId(String loginId){

        /*
        List<Member> all = findAll();
       for(Member m : all){
            if(m.getLoginId().equals(loginid)){
                return Optional.of(m);
            }
        }
        return Optional.empty();
        */

        //위 로직을 stream 과 람다를 이용해서 간소화
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    public void clearStore(){
        store.clear();
    }


}
