package start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    /**
     * 엔터티메니저팩토리는 애플리케이션 전체에서 딱 한번만 생성하고 공유해서 사용한다.
     * 앤터티매니저팩토리는 여러 스레드가 동시에 접근해도 안전하므로 서로 다른 스레드 간에 공유해도 된다.
     *
     * 엔터티매니저를 사용해서 엔터티를 데이터베이스에 등록/수정/삭제/조회를 할 수 있다.
     * 엔터티매니저는 데이터베이스 커넥션과 밀접한 관계가 있으므로 스레드간에 공유하거나 재사용하면 안된다.
     */
    public static void main(String[] args) {

        // [엔티티 매니저 팩토리] - 생성
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("jpabook");
        // [엔티티 매니저] - 생성
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // [트랜잭션] - 획득
        EntityTransaction tx = entityManager.getTransaction();
        // 엔터티매니저는 데이터 변경시 트랜잭션을 시작해야 한다.
        try {
            tx.begin(); // [트랜잭션] - 시작
            logic(entityManager); // [비지니스 로직] - 실행
            //testDetached(entityManager);
            // 커밋하는 순간 데이터베이스에 insert sql을 보낸다. (내부 쿼리 저장소에 insert sql을 차곡차곡 모아두고
            // 트랜잭션을 커밋할때 모아둔 쿼리를 데이터베이스에 보내는데 이것을 '트랜잭션을 지원하는 쓰기 지연(transactional write-behind)이라 한다.
            tx.commit(); // [트랜잭션] - 커밋
            // 트랜잭션을 커밋하면 영속성 컨텍스트를 플러시한다. 플러시는 영속성 컨텍스트의 변경내용을 디비에 동기화 하는 작업
            // 등록, 수정, 삭제한 엔터티를 데이터베이스에 반영
            // 쓰기 지연 SQL 저장소에 모인 쿼리를 데이터베이스에 보낸다.
            // 디비에 동기화 한 뒤에 실제 디비 트랜잭션을 커밋한다.
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); // [트랜잭션] - 롤백
        } finally {
            // 영속성컨텍스트가 종료되어 더이상 member가 관리되지 않는다.
            entityManager.close(); // [엔티티 매니저] - 종료
        }

        entityManagerFactory.close(); // [엔터티 메니저 팩토리] - 종료

    }

    private static void logic(EntityManager entityManager) throws Exception {

        String id = "id1";
        String id2 = "id2";
        Member member = new Member();
        member.setId(id);
        member.setUsername("윤진");
        member.setAge(30);

        // 등록
        // 1차 캐시에 저장됨
        entityManager.persist(member);
        // 여기까지 insert sql을 데이터베이스에 보내지 않는다.
        // 수정
        member.setAge(31);

        // 1건 조회
        // 1차 캐시에서 조회
        // find()를 호출하면 먼저 1차 캐시에서 엔터티를 찾고 만약 찾는 엔터티가 1차캐시에 없으면 DB에서 조회한다.
        // 조회한 데이터로 member2엔터티를 생성해서 1차 캐시에 저장한다.(영속상태)
        // 이제 member1와 member2 엔터티 인스턴스는 1차캐시에 있다. 따라서 이 엔터티를 조회하면
        // 메모리에 있는 1차 캐시에서 바로 불러온다. --> 성능상 이점
        Member findMember = entityManager.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        Member findMember2 = entityManager.find(Member.class, id2);

        if (findMember2 != null) {
            System.out.println("findMember2=" + findMember2.getUsername() + ", age=" + findMember2.getAge());
        }

        Member a = entityManager.find(Member.class, id);
        Member b = entityManager.find(Member.class, id);

        // 반복해서 호출해도 영속성 컨텍스는 1차 캐시에 있는 같은 엔터티 인스턴스를 반환한다.
        // 결과는 당연히 참이다.
        // 영속성 컨텍스는 성능상 이점과 엔터티의 동일성을 보장한다.
        // 동일성:identity:실제 인스턴스가 같다. == 같은 값이 된다.
        // 동등성:equality:실제 인스턴스는 다를 수 있지만 가지고 있는 값이 같다. equals()메서드 비교
        System.out.println(a == b); // 동일성 비교

        // 목록 조회
        List<Member> members = entityManager.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("member.size=" + members.size());

        entityManager.remove(member);

        Board board = new Board();
        entityManager.persist(board);
        System.out.println("board.id=" + board.getId());

    }

    private static void testDetached(EntityManager entityManager) {
        // 회원 엔티티생성, 비영속 상태
        Member member = new Member();
        member.setId("memberA");
        member.setUsername("회원A");

        // 회원 엔티티 영속상태
        entityManager.persist(member);

        // 회원 엔티티를 영속성컨텍스트에서 분리, 준영속 상태
        // 더이상 해당 엔터티를 관리하지 말라는 것.
        // "1차 캐시부터 쓰기 지연 SQL 저장소까지" 해당 엔티티를 관리하기 위한 모든 정보가 제거된다.
        // 영속상태였다가~ 더는 영속성컨텍스트가 관리하지 않는 상태. -> 준영속 상태
        entityManager.detach(member);

        Member member1 = entityManager.find(Member.class, "memberA");
        // 영속성 컨텍스트 초기화
        entityManager.clear();
        // 준영속 상태이므로 변경감지는 동작하지 않는다. 따라서 이름을 변경해도 디비에 반영되지 않는다.
        member1.setUsername("changeName");

    }

}
