package jpabook.model.test;

import com.sun.tools.corba.se.idl.constExpr.Or;
import jpabook.model.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestMain {

    public static void main(String[] args) {
        /*Customer customer1 = new Customer("member1", "회원1");
        Customer customer2 = new Customer("member2", "회원2");

        Team team1 = new Team("team1", "팀1");

        customer1.setTeam(team1);
        customer2.setTeam(team1);

        Team findTeam = customer1.getTeam(); // 객체는 참조를 사용해서 연관관계를 탐색가능 -> 객체 그래프 탐색*/

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            logic(em);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }

    private static void logic(EntityManager em) {

        // 아이템 넣기
        /*
        Item item1 = new Item();
        item1.setName("맥북");
        item1.setPrice(130000);
        item1.setStockQuantity(1);
        em.persist(item1);

        Item item2 = new Item();
        item2 = em.find(Item.class, 6L);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setItem(item1);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setItem(item2);


        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        List<Order> orderList = new ArrayList<Order>();
        Order order1 = new Order();
        order1.setOrderItems(orderItemList);
        order1.setStatus(OrderStatus.ORDER);
        order1.setOrderDate(new Date());
        orderList.add(order1);
        orderItem1.setOrder(order1);
        orderItem2.setOrder(order1);

        Member yjks = new Member();
        yjks.setName("킴윤진");
        yjks.setCity("서울");
        yjks.setStreet("송파구");
        yjks.setZipcode("333-333");
        yjks.setOrders(orderList);
        order1.setMember(yjks);

        em.persist(orderItem1);
        em.persist(orderItem2);
        em.persist(order1);
        em.persist(yjks);

        Member getMember = em.find(Member.class, yjks.getId());
        System.out.println("getMember====>" + order1.getMember().getName());
        */

        /*
        Order order = em.find(Order.class, 38L);
        OrderItem orderItem = order.getOrderItems().get(1);
        Item item = orderItem.getItem();
        System.out.println("item====>" + item.getName());
        */
    }
}
