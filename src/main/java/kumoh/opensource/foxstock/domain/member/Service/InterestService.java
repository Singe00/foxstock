package kumoh.opensource.foxstock.domain.member.Service;


import kumoh.opensource.foxstock.domain.member.Domain.Interest;
import kumoh.opensource.foxstock.domain.member.Domain.Member;
import kumoh.opensource.foxstock.domain.member.Dto.InterestDto;
import kumoh.opensource.foxstock.domain.member.Repository.InterestRepository;
import kumoh.opensource.foxstock.domain.member.Repository.MemberRepository;
import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import kumoh.opensource.foxstock.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InterestService {
    @Autowired
    private final InterestRepository interestRepository;

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final StockRepository stockRepository;

    Interest interest = new Interest();

    public String addInterest(InterestDto request) {

        Optional<Member> member = memberRepository.findByEmail(request.getEmail());

        if (interestRepository.findByUserIdAndSrtnCd(member.get().getId(),request.getSrtnCd()).isPresent())
        {
            log.info("이미 목록에 있는 종목입니다.");
            return "Fail";
        }
        else {
            interest.setId(null);
            interest.setUserId(member.get().getId());
            interest.setSrtnCd(request.getSrtnCd());

            interestRepository.save(interest);

            return "Success";
        }
    }

    public String deleteInterest(InterestDto request) {
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());
        Optional<Interest> inter = interestRepository.findByUserIdAndSrtnCd(member.get().getId(),request.getSrtnCd());

        interestRepository.deleteById(inter.get().getId());

        return "Success";
    }

    public List<Stock> returnInterest(InterestDto request) {
        Member member = memberRepository.findByEmail(request.getEmail()).get();
        List<String> srtnCds = interestRepository.findAllByUserId(member.getId()).stream()
                .map(Interest::getSrtnCd).toList();

        return stockRepository.findAllById(srtnCds);

    }

    public List<String> returnInterest2(InterestDto request) {
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());

        List<Interest> inters = interestRepository.findAllByUserId(member.get().getId());
        List<String> interList = new ArrayList<>();

        for(Interest inter : inters){
            String interestDto = inter.getSrtnCd();

            interList.add(interestDto);
        }
        log.info("{}",interList);
        return interList;
    }
}
