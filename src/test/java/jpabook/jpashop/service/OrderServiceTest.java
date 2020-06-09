package jpabook.jpashop.service;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;

    @Autowired  OrderService orderService;
    @Autowired  OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //Given
        Member member = new Member();
        member.setName("member2");
        member.setAddress(new Address("seoul","mincho","123-123"));
        em.persist(member);
        //When
       Book book = new Book();
       book.setName("mincho JPA");
       book.setPrice(10000);
       book.setStockQuantity(10);
       em.persist(book);

       int orderCount=2;
       //when
       Long orderId = orderService.order(member.getId(), book.getId(),orderCount);
       //then
       Order getOrder=orderRepository.findOne(orderId);
       Assert.assertEquals("Ordering item status are ORDER", OrderStatus.ORDER,getOrder.getStatus());
    }
}
