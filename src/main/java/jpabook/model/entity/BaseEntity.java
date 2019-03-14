package jpabook.model.entity;

import javax.persistence.MappedSuperclass;
import java.util.Date;

// @MappedSuperclass 를 사용해서 부모클래스로 상속시켜 컬럼을 추가한다.
@MappedSuperclass
public class BaseEntity {
    private Date createDate; // 등록일
    private Date lastModifiedDate; // 수정일

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
}
