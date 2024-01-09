package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        // 앤티티 조회
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장 (cascade는 주인이 관리하는 경우에만 써야함 -> delivery, orderItem은 Order말고 안씀)
        // 만약 delivery와 orderItem이 Order말고도 다른데서 쓰이면 cascade쓰면 안됨 (복잡하게 얽힘)
        // -> 별도 Repository 생성해서 persist해라
        orderRepository.save(order);

        return order.getId();
    }

    //취소
    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        // 주문 엔터티 조회
        Order order = orderRepository.findOne(orderId);

        // 여기서 JPA에 진가가 나옴
        // 기존 마이바티스 이런 것은 무언가가 바뀌면 쿼리로 직접 파라미터 보내서 다 일일히 바꿔줘야 하는데
        // JPA는 해당 엔터티안에 데이터만 바꾸면 JPA가 알아서 바뀐 변경 포인트들을(더티체킹) 데이터베이스에 update쿼리로 날라감
        // 주문 취소
        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }

}
