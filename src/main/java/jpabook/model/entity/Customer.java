package jpabook.model.entity;

import javax.persistence.*;

@Entity
public class Customer {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;
    private String username;

    // 연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Customer() {
    }

    public Customer(String id, String username) {
        this.id = id;
        this.username = username;
    }

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

    public Team getTeam() {
        return team;
    }

    // 연관 관계 설정
    public void setTeam(Team team) {
        this.team = team;
    }
}
