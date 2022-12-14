# ezenJPA Ver.
personal project that converting https://github.com/hms200/teamproject into JPA version which use JPA hibernate instead of mybatis.

- [기존 팀 프로젝트](https://github.com/hms200/teamproject)를 Spring data jpa 기반으로 변경한 개인 프로젝트입니다.
- 팀프로젝트에서 이용한 Oracle cloud DB를 변경없이 그대로 사용, jpa로 mapping 하여 작성하였습니다.


- com.ezenjpa.ezenjpaver.entity package는 다음과 같은 코드를 사용하여 작성되었습니다.
  - @sequenceGenerator 를 작성하여 Oracle sequence 이용
  - @sqlResultSetMapping, @NamedNativeQuery 를 사용하여 vo에 maaping (CartEntity, ReviewEntity)
  - Entity to DTO converter 를 작성하여 DTO로 쉽게 변환할 수 있게 하였습니다.(UserEntity, NoticeEntity, OneToOneEntity, GoodsEntity, QuestionEntity)
  - Table간 양방향, 단방향 연관관계 mapping


- com.ezenjpa.ezenjpaver.repository package는 다음과 같은 코드를 사용하여 작성되었습니다.
  - @Query 를 이용한 jpql 사용 (UserRepository)
  - @Query 를 이용한 native sql 사용 (ReviewRepository)


- com.ezenjpa.ezenjpaver.servie.EntityUpdateUtil
  - Java Reflection 을 이용하여 DTO를 Entity로 변환 한 후 DB에 UPDATE 하는 동작을 수행하도록 작성하였습니다.


- 기존 프로젝트에서 변경된 점은 다음과 같습니다.
  - 기존 프로젝트의 com.ezen.service.Pagenation 클래스를 spring data 에서 제공하는 Page 를 사용하도록 변경하였습니다.
  - 기존 프로젝트의 com.ezen.service.MainService 클래스를 MainServie와 NoticeService로 분리하였습니다.
  - NPE 발생을 방지하기 위해 작성된 try/catch 구문 일부를 Optional을 사용한 코드로 변경하였습니다.
  - forEach를 사용한 구문 일부를 Stream을 사용한 코드로 변경하였습니다.
  - 이벤트목록, 카테고리목록, 주문상황을 Enum으로 작성하여 해당 목록이 작성된 코드의 일관성을 유지하도록 하였습니다.
  - 일부 JSP 페이지는 JSTL을 이용하여 객체 그래프 탐색을 사용, 연관된 table의 data를 직접 읽어오도록 변경하였습니다. 






