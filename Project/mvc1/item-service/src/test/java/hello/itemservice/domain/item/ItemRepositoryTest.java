package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

//    @AfterEach
//   void afterEach(){itemRepository.clearStore();}

    @Test
    void save() {
        Item item = new Item("A", 10000, 10);

        Item savedItem = itemRepository.save(item);

        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        Item item1 = new Item("A", 10000, 10);
        Item item2 = new Item("B", 1000, 1);

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> result = itemRepository.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1, item2);

    }

    @Test
    void update() {
        Item item = new Item("A", 10000, 10);

        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        Item updateParam = new Item("B", 200, 2);
        itemRepository.update(itemId, updateParam);

        Item finditem = itemRepository.findById(itemId);

        assertThat(finditem.getItemName()).isEqualTo(updateParam.getItemName());

        assertThat(finditem.getPrice()).isEqualTo(updateParam.getPrice());

        assertThat(finditem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

}