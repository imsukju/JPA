### **1. jpaDemoOne**
[JPA 설명 모음](https://github.com/imsukju/MyStudyNote/tree/main/JPA)

#### 설명
- JPA의 기본 설정과 연관 관계 매핑 학습.
- 단방향 및 양방향 매핑 실습.

#### 주요 기능
- 엔티티 설계 및 매핑.
- 기본 CRUD 연산 수행.

#### 디렉토리 구조
```
jpaDemoOne/
├── src/main/java/com/example/
│   ├── Main.java            # 메인 실행 클래스
│   ├── entity/
│   │   ├── Address.java     # 주소 엔티티
│   │   └── Member.java      # 멤버 엔티티
├── src/main/resources/
│   └── META-INF/persistence.xml  # JPA 설정
└── pom.xml                  # Maven 프로젝트 설정
```

---

### **2. jpaDemoTwo**

#### 설명
- 다대다(N:M) 관계를 실습하고 복합 키를 다루는 프로젝트.

#### 주요 기능
- N:M 관계를 중간 테이블로 구현.
- 복합 키 매핑 학습.

#### 디렉토리 구조
```
jpaDemoTwo/
├── src/main/java/com/example/
│   ├── Main.java            # 메인 실행 클래스
│   ├── entity/
│   │   ├── Member.java      # 멤버 엔티티
│   │   ├── Product.java     # 제품 엔티티
│   │   └── MemberProduct.java # 중간 매핑 엔티티
└── pom.xml                  # Maven 프로젝트 설정
```

---

### **3. ch06**

#### 설명
- JPA의 고급 매핑과 관계 설정 실습.

#### 주요 기능
- 1:1, 1:N, N:M 관계 구현.
- 조인 테이블 및 다양한 매핑 전략 학습.

#### 디렉토리 구조
```
ch06/
├── src/main/java/com/example/
│   ├── Main.java            # 메인 실행 클래스
│   ├── entity/
│   │   ├── Member.java      # 멤버 엔티티
│   │   ├── Product.java     # 제품 엔티티
│   │   └── Order.java       # 주문 엔티티
└── pom.xml                  # Maven 프로젝트 설정
```

---

### **4. ch07**

#### 설명
- JPA의 기본 엔티티 설계와 매핑 실습.

#### 주요 기능
- 기본적인 엔티티 및 연관 관계 설정.
- 데이터베이스 연동 및 CRUD 테스트.

#### 디렉토리 구조
```
ch07/
├── src/main/java/com/example/
│   ├── Main.java            # 메인 실행 클래스
│   ├── entity/
│   │   ├── Album.java       # 앨범 엔티티
│   │   ├── Book.java        # 책 엔티티
│   │   └── Movie.java       # 영화 엔티티
└── pom.xml                  # Maven 프로젝트 설정
```

---

### **5. ch09**

#### 설명
- JPA의 상속 매핑과 고급 연관 관계 설정.

#### 주요 기능
- 상속 관계 매핑.
- 고급 엔티티 설계 및 테스트.

#### 디렉토리 구조
```
ch09/
├── src/main/java/com/example/
│   ├── Main.java            # 메인 실행 클래스
│   ├── entity/
│   │   ├── Address.java     # 주소 엔티티
│   │   ├── Member.java      # 멤버 엔티티
│   │   └── Team.java        # 팀 엔티티
└── pom.xml                  # Maven 프로젝트 설정
```

---

### **6. ch10**

#### 설명
- JPA의 QueryDSL 사용 및 동적 쿼리 생성.

#### 주요 기능
- QueryDSL을 활용한 동적 쿼리 학습.
- 고급 데이터 접근 및 필터링.

#### 디렉토리 구조
```
ch10/
├── src/main/java/com/example/
│   ├── Main.java            # 메인 실행 클래스
│   ├── entity/
│   │   ├── Address.java     # 주소 엔티티
│   │   ├── Post.java        # 게시물 엔티티
│   │   ├── Comment.java     # 댓글 엔티티
└── pom.xml                  # Maven 프로젝트 설정
```

---

### **7. JpaPracticeOne**

#### 설명
- 간단한 JPA 연관 관계 학습.

#### 주요 기능
- 기본 CRUD 연산.
- 엔티티 간 1:N 관계 테스트.

#### 디렉토리 구조
```
JpaPracticeOne/
├── src/main/java/com/example/
│   ├── Main.java            # 메인 실행 클래스
│   ├── entity/
│   │   ├── Member.java      # 멤버 엔티티
│   │   └── Orders.java      # 주문 엔티티
└── pom.xml                  # Maven 프로젝트 설정
```

---

### **8. JpaPracticeTwo**

#### 설명
- 다양한 매핑과 관계 설정 학습.

#### 주요 기능
- 1:1, N:M 관계 매핑 테스트.
- 복합 키와 조인 테이블 실습.

#### 디렉토리 구조
```
JpaPracticeTwo/
├── src/main/java/com/example/
│   ├── Main.java            # 메인 실행 클래스
│   ├── entity/
│   │   ├── User.java        # 사용자 엔티티
│   │   ├── Bank.java        # 은행 엔티티
│   │   └── UserBank.java    # 중간 매핑 엔티티
└── pom.xml                  # Maven 프로젝트 설정
```

---

## 실행 방법

1. **필수 조건**
   - Java 17 이상
   - Maven 또는 Gradle 설치

2. **실행**
   - 프로젝트 디렉토리로 이동:
     ```bash
     cd [프로젝트명]
     ```
   - Maven으로 빌드 및 실행:
     ```bash
     mvn spring-boot:run
     ```

3. **테스트**
   - IDE를 통해 각 프로젝트의 `Main.java`를 실행하여 결과를 확인.

---

## 사용 기술

- **Java 17**
- **Spring Data JPA**
- **Hibernate**
- **QueryDSL**

---

필요한 추가 정보나 수정 사항이 있으면 말씀해주세요!