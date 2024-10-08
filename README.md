## JPA  
**자바 ORM 표준 JPA 프로그래밍**이라는 책을 바탕으로 예제 코드를 작성해 보았습니다.

### StudyJpa
이 디렉터리에는 책에서 나온 예제를 보고 따라 입력해 본 것입니다.
- **BeforeCh06**  
  해당 디렉터리에는 엔티티 클래스 생성 및 컬럼, 테이블 설정 같은 기본적인 예제를 작성했습니다.
- **CH06**  
  해당 디렉터리에는 연관 관계 매핑을 활용하여 작성했습니다.
- **CH07**  
  해당 디렉터리에는 고급 매핑을 활용하여 작성했습니다.
   1. 상속 관계 매핑: 객체의 상속 관계를 어떻게 데이터베이스에 매핑하는지 배웁니다.
   2. @MappedSuperclass: 등록일, 수정일 같이 여러 엔티티에서 공통으로 사용하는 매핑 정보만 상속받고 싶을 때 이 기능을 사용합니다.
   3. 복합 키와 식별 관계 매핑: 데이터베이스의 식별자가 하나 이상일 때 매핑하는 방법을 다룹니다. 그리고 데이터베이스 설계에서 이야기하는 식별 관계와 비식별 관계에 대해서도 설명합니다.
   4. 조인 테이블: 테이블은 외래 키 하나로 연관 관계를 맺을 수 있지만, 연관 관계를 관리하는 연결 테이블을 두는 방법도 있습니다. 여기서는 이 연결 테이블을 매핑하는 방법을 다룹니다.
   5. 엔티티 하나에 여러 테이블 매핑하기: 보통 엔티티 하나에 테이블 하나를 매핑하지만, 엔티티 하나에 여러 테이블을 매핑하는 방법도 다룹니다. 
