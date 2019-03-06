package jpabook.model.test;

import jpabook.model.entity.Customer;
import jpabook.model.entity.Team;

public class TestMain {


    public static void main(String[] args) {
        Customer customer1 = new Customer("member1", "회원1");
        Customer customer2 = new Customer("member2", "회원2");

        Team team1 = new Team("team1", "팀1");

        customer1.setTeam(team1);
        customer2.setTeam(team1);

        Team findTeam = customer1.getTeam(); // 객체는 참조를 사용해서 연관관계를 탐색가능 -> 객체 그래프 탐색

    }
}
