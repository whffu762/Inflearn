package hello.servlet.domain.member;
import lombok.Getter;
import lombok.Setter;

//Member 클래스, Member의 스펙 및 생성은 여기서 함
@Getter
@Setter
public class Member {

    private Long id;
    private String username;
    private int age;

    public Member(){

    }
    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
