package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    // spring의 transactional에 의해서 트랜잭션이 commit이 됨
    // -> 커밋이 딱 되면 JPA가 flush라는 것을 날림
    // -> 영속성 컨텍스트에 있는 엔티티중에 변경된 애가 뭔지 다 찾음
    // -> 찾아서 setter를 통해 바뀐 앤터티에 대해 update쿼리를 DB에 날려서 update 처리함
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        // 밑에 처럼 setter막 깔지 말고 이런 의미 있는 메서드 만들어서 사용해라
//        findItem.change(price, name, stockQuantity);

        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
