package start;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // 이 클래스를 테이블과 매핑한다고 jpa에게 알려준다. @Entity가 사용된 클래스를 엔티티 클래스라고 한다.
@Table(name = "MEMBER") // 엔티티클래스에 매핑할 테이블 정보를 알려준다. (생략시 클래스이름을 테이블이름으로 매핑한다.)
public class Member {

    @Id // 엔터티클래스의 필드를 테이블의 기본키에 매핑한다. (식별자 필드)
    @Column(name = "ID") // 필드를 컬럼에 매핑한다.
    private String id;

    @Column(name = "NAME")
    private String username;

    // 매핑정보가 없는 필드
    // 매핑어노테이션을 생략하면 필드명을 사용해서 컬럼명으로 매핑한다.
    // name은 대소문자 구분 확인하고 해줘야함.
    private Integer age;

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
}
