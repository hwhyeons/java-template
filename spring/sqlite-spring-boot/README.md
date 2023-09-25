
### 환경
JDK 17
Spring Boot 3.14


### build.gralde
```
	implementation 'org.springframework.boot:spring-boot-starter'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-jdbc'
	implementation 'org.hibernate.orm:hibernate-community-dialects'
	implementation 'org.xerial:sqlite-jdbc:3.34.0'
```


### application.properties
```
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
spring.thymeleaf.check-template-location=false
spring.datasource.url=jdbc:sqlite:test.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.datasource.username=admin
spring.datasource.password=admin
```
test.db 자리에 원하는 경로 설정 (따로 설정 안하면 기본 프로젝트 경로)


여기까지하고 Entity 만들고 스프링부트 구동시 자동으로 DB파일 생성

### 추가
이전 버전에서는 sqlite dialect를 지원하지 않음
