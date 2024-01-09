package jpabook.jpashop.service.query;

import jpabook.jpashop.api.OrderApiController;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

// 애플리케이션이 커지면 이렇게 분리하는 게 좋음
// spring.jpa.open-in-view: false OSIV 종료 한 경우에
// 이런 식으로 하면 트랜젝션 안에서 동작하기에 Lazy loading exception 발생 X
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;

//    public List<OrderDto> ordersV3(){
//        public List<OrderDto> ordersV3(){
//            List<Order> orders = orderRepository.findAllWithItem();
//            List<OrderApiController.OrderDto> result = orders.stream()
//                    .map(o -> new OrderApiController.OrderDto(o))
//                    .collect(toList());
//            return result;
//        }
//    }
}
