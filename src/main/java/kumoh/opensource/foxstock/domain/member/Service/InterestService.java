package kumoh.opensource.foxstock.domain.member.Service;


import kumoh.opensource.foxstock.domain.member.Domain.Interest;
import kumoh.opensource.foxstock.domain.member.Domain.Member;
import kumoh.opensource.foxstock.domain.member.Dto.InterestDto;
import kumoh.opensource.foxstock.domain.member.Repository.InterestRepository;
import kumoh.opensource.foxstock.domain.member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InterestService {
    @Autowired
    private final InterestRepository interestRepository;

    @Autowired
    private final MemberRepository memberRepository;

    Interest interest = new Interest();

    public String addInterest(InterestDto request) {
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());

        interest.setId(null);
        interest.setUserId(member.get().getId());
        interest.setSrtnCd(request.getSrtnCd());

        interestRepository.save(interest);

        return "Success";
    }

    public String deleteInterest(InterestDto request) {
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());
        Optional<Interest> inter = interestRepository.findByUserIdAndSrtnCd(member.get().getId(),request.getSrtnCd());

        interestRepository.deleteById(inter.get().getId());

        return "Success";
    }
}
