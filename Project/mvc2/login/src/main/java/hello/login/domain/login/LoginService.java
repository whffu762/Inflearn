package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member login(String loginId, String password){

        /*
        Optional<Member> findMember = memberRepository.findByLoginId(loginId);
        Member member = findMember.get();
        if (member.getPassword().equals(password)) {
            return member;
        }else{
            return null;
        }
        */
        //orElse(null): filter의 조건에 해당하는 값이 있으면 그 값 반환
        //없으면 null 반환
        return memberRepository.findByLoginId(loginId)
                .filter(m ->m.getPassword().equals(password))
                .orElse(null);
    }

}
