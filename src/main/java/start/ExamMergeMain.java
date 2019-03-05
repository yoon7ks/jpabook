package start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ExamMergeMain {
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        Member member = createMember("memberA", "회원1");

        // 이름을 변경했지만 준영속 상테인 member 엔티티를 관리하는 영속성 컨텍스트가 더는 존재하지 않으므로
        // 수정사항을 디비에 반영할 수 없다..
        member.setUsername("회원명 변경");

        // 준영속상태의 엔티티를 수정하려면 준영속 상태를 다시 영속상태로 변경해야 하는데 이때 병합(merge)을 사용한다.
        mergeMember(member);

    }

    private static void mergeMember(Member member) {
        // == 영속성 컨텍스트 2 시작 == //
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        EntityTransaction tx2 = entityManager2.getTransaction();

        tx2.begin();

        Member mergeMember = entityManager2.merge(member); // 파라메터 member는 계속 준영속..이므로 이제 사용할 필요가 없으므로
        member = entityManager2.merge(member); // 이런식으로 참조하도록 변경하는 것이 안전하다.

        // 새로운 영속성 컨텍스트 2를 시작하고 merge를 호출해서 준영속 상태의 member엔티티를 2가 관리하는 영속상태로 변경했다.
        // 영속성태이므로 트랜잭션을 커밋할 때, 수정했던 회원명이 디비에 반영된다.
        // 정확히는, 준영속에서 영속으로 member엔티티가 변경되는 것이 아니고 mergeMember라는 새로운 영속상태의 엔티티가 반환된다.
        tx2.commit();

        // 준영속 상태
        System.out.println("준영속 상태 :: member=" + member.getUsername());

        // 영속 상태
        System.out.println("영속 상태 :: mergerMember=" + mergeMember.getUsername());

        System.out.println("em2 contains member=" + entityManager2.contains(member));
        System.out.println("em2 contains mergeMember=" + entityManager2.contains(mergeMember));

        //Member member1 = new Member();
        //Member newMember = entityManager2.merge(member1); // 비영속 병합
        //tx2.commit();
        // 병합은 파라미터로 넘어온 엔티티의 식별자 값으로 영속성 컨텍스트를 조회하고 찾는 엔터티가 없으면 디비에서 조회한다.
        // 디비에서도 발견하지 못하면 새로운 엔티티를 생성해서 병합한다.
        // 병합은 준영속, 비영속을 신경쓰지 않는다. save or update 기능을 수행한다.

        entityManager2.close();
        // == 영속성 컨텍스트 2 종료 == //
    }

    private static Member createMember(String id, String username) {
        // -- 영속성 컨텍스트 1 시작 -- //
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityTransaction tx1 = entityManager1.getTransaction();
        tx1.begin();

        Member member = new Member();
        member.setId(id);
        member.setUsername(username);

        entityManager1.persist(member);
        tx1.commit();

        entityManager1.close(); // 영속성 컨텍스트 1 종료.
                                // member 엔티티는 준영속 상태가 된다.
        // -- 영속성 컨텍스트 1 종료 -- //

        // 준영속상태의 member엔티티 반환.
        return member;
    }
}
