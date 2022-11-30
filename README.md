# Team.여우비 오픈소스 프로젝트 

### 시연 영상 링크 : 

------------

### 프로젝트명

여우의 주식 레시피 : 투자 정보 제공 APP

------------

### Description
  -  숭실대학교 경제학과 서준식 교수님의 '다시 쓰는 주식 투자 교과서'를 기반으로 각 주식 종목 별로 예상 수익률을 계산해주는 어플리케이션을 개발하였다.
    개인이 직접 수식을 이용하여 계산을 시도하고자 한다면 찾아 봐야할 정보와 시간이 많이 투자될 것이다. 그런 불편함을 최소화해주기 위해 해당 앱을 사용하면 종목별로
    계산된 값을 한 눈에 볼 수 있도록 제작하였다.
     최근 주식투자에 대한 접근이 많이 올라감에 따라 처음 시도하는 초보 투자자들이 대거 유입되었다. 이런 사용자들을 주타킷으로 이 정도의 수익을 위해서 어떤 가격에
    매수를 해야하는지 참고용으로 알려주는 이정표가 되고자 한다.

    예상 수익률 : 현재가에서 구매했을 때 1년 뒤 예상 수익률
    추천 구매가 : 1년 뒤 15%의 수익을 내기 위해 추천하는 주식 가격

------------

### Environment
  -  해당 앱은 안드로이드, IOS, 웹에서 동작한다. 사용자들이 불편함을 겪지 않도록 어느 환경에서도 동작할 수 있는 크로스플랫폼을 기반으로 개발하였다. 
  -  Frontend
      > React Native
      + React Navigation
      + React Query
      + paper
    
  - Backend
    + Spring - JPA - MySQL
    
------------

### Prerequisite
  -  작성한 코드를 실행하기 전에 설치해야할 pakage및 의존성
  1. Java17
  2. MySQL
  3. Spring 2.7.4(Gradle)
  
```java
plugins {
    id 'org.springframework.boot' version '2.7.4'
    id 'io.spring.dependency-management' version '1.0.14.RELEASE'
    id 'java'
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-jdbc'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'com.googlecode.json-simple:json-simple:1.1.1'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa:2.7.4'
    implementation 'mysql:mysql-connector-java'

    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

------------
### Getting Started
다음은 Java에서 Spring Data Repositories를 사용하는 애플리케이션의 간단한 티저입니다.
```java

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByName(String name);
}


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;

    Member member = new Member();

    public String login(MemberDto request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent())
        {
            Optional<Member> member = memberRepository.findByEmail(request.getEmail());
            log.info("db password = {}, input password = {}",member.get().getPassword(),request.getPassword());
            if (member.get().getPassword().equals(request.getPassword())) {
                return "Success";
            }
            else{
                return "비밀번호가 일치하지 않습니다.";
            }
        }
        else {
            return "존재하지 않는 아이디입니다.";
        }
    }
    (중략)
}

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @PostMapping("/login")
    @ResponseBody
    public boolean login(@RequestBody MemberDto request) {
        log.info("userId = {}, password = {}", request.getEmail(), request.getPassword());

        if(memberService.login(request).equals("Success")) {
            return true;
        }

        return false;
    }
}

```

------------

### OpenSource 및 오픈API    
  -  오픈API
   1. [금융위원회_주식시세정보](https://www.data.go.kr/data/15094808/openapi.do)
   2. [금융위원회_KRX상장종목정보](https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15094775)

------------
 
### Lisence
  - 대학 실습 과제로 진행한 프로젝트로 해당 앱을 사용하여 상업활동 및 이윤 창출이 불가능합니다.

------------
