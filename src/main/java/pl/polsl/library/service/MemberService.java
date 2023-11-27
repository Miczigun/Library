package pl.polsl.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.library.model.Member;
import pl.polsl.library.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> getMembers(){
        return memberRepository.findAll();
    }

    public Member getMember(long id){
        return memberRepository.findById(id).orElseThrow();
    }

    public Member getMemberByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    public Member addMember(Member member){
        return memberRepository.save(member);
    }

    public void deleteMember(long id){
        memberRepository.deleteById(id);
    }

}
