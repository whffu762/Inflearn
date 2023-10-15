package hello.servlet.domain.member;


import javax.sound.midi.Sequence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려..?
 */

//Member를 이용한 작업(회원 생성, 조회)를 구현
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();   //hashmap으로 멤버 저장 및 관리
    private static long sequence = 0L;  //Member 순서를 통해 구분

    private static final MemberRepository instance = new MemberRepository();//싱글톤으로 만들기
    //스태틱이어서 클래스 명으로 접근가능

    public static MemberRepository getInstance(){
        return instance;
    }
    private MemberRepository(){
        //MemberRepository를 아무나 생성하지 못하게 private으로 생성자 선언 다른 클래스에선 생성하지 못하고 getInstance로만 접근 가능
    }

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);

        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values()); //store의 값들이 새로운 ArrayList에 복사되서 저장됨
        //store.values()의 값을 최대한 건드리지 않기 위함
    }
    public void clearStore(){
        store.clear();
    }


}
