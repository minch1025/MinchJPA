package jpabook.jpashop.service;


import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    /**Order**/
    @Transactional
    public Long order(Long memberId ,Long itemId,int count ){
        //Searching Entity
        Member member =memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //delivery address
        Delivery delivery=new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //create orderItem
        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);

        //create order

        Order order = Order.createOrder(member,delivery,orderItem);

        //Save order
        orderRepository.save(order);
        return order.getId();
    }

    /**Cancel order**/
    @Transactional
    public void cancelOrder(Long orderId){
        //searching order entitiy
        Order order = orderRepository.findOne(orderId);

        //cancel order
        order.cancel();
    }
}
