package start;

import javax.persistence.*;
import java.util.Date;

 // 이 클래스를 테이블과 매핑한다고 jpa에게 알려준다. @Entity가 사용된 클래스를 엔티티 클래스라고 한다.
@Table(name = "MEMBER", uniqueConstraints = {@UniqueConstraint(
        name = "NAME_AGE_UNIQUE",
        columnNames = {"NAME", "AGE"}
)}) // 엔티티클래스에 매핑할 테이블 정보를 알려준다. (생략시 클래스이름을 테이블이름으로 매핑한다.)
public class Member {

    @Id // 엔터티클래스의 필드를 테이블의 기본키에 매핑한다. (식별자 필드)
    @Column(name = "ID", length = 50) // 필드를 컬럼에 매핑한다.
    private String id;

    @Column(name = "NAME", nullable = false, length = 10) // not null 제약조건 ,문자 크기
    private String username;

    // 매핑정보가 없는 필드
    // 매핑어노테이션을 생략하면 필드명을 사용해서 컬럼명으로 매핑한다.
    // name은 대소문자 구분 확인하고 해줘야함.
    private Integer age;

    private String firstName;
    private String lastName;

    // 기본은 필드 접근방식이고 getFullName만 프로퍼티 접근방식을 사용한다.
    // 회원 엔터티를 저장하면 회원 테이블의 fullName 컬럼에 fisrtName+lastName의 결과가 저장된다.
    @Access(AccessType.PROPERTY)
    public String getFullName() {
        return firstName + lastName;
    }

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
