package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();   //실제로는 HashMap 쓰면 안됨(동시 접근에 취약함)
    private static long sequence = 0L;
    //둘 다 static인 이유는 싱글톤을 보장하기 위함
    //컨테이너 안에서 동작하면 어차피 싱글톤이 보장되긴 함

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);

        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }
    
    public void clearStore(){   //test를 위함
        store.clear();
    }
}

