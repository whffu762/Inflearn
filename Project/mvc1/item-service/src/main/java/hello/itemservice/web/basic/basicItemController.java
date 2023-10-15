package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class basicItemController {

    private final ItemRepository itemRepository;

    @PostConstruct
    public void init(){
        itemRepository.save(new Item("A", 10000, 10));
        itemRepository.save(new Item("B", 20000, 20));
    }

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);

        return "basic/items";   //여기 경로는 /templates 로 통하기 때문에 다른 디렉토리에 있는 파일들은 적용 안됨
    }

    //PRG 방식에서 Redirect 되는 요청이 여기임(/add5, /edit)
    @GetMapping("/{itemId}")    //여기서의 itemId는 items의 th:href에서 연결됨
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){

        return "basic/addForm";
    }

    @PostMapping("/add")
    public String save(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }
    //위 addForm()과 save() 방식 처럼 같은 요청 URL을 이용해서 
    //데이터 입력 양식은 get으로 받아오고 입력은 post로 처리되게끔 하는게 좋음

    @PostMapping("/add2")
    public String save2(@ModelAttribute("item") Item item, Model model){
        //req의 key Item의 필드를 서로 매핑해서 item 객체를 초기화함

        itemRepository.save(item);  //초기화된 item 객체를 save()

        //model.addAttribute("item", item); 원래는 결과 동작을 model에 addAttribute 해줘야 했지만
        //@ModelAttribute의 동작으로 위 코드를 대신해줌

        return "basic/item";
    }

    @PostMapping("/add3")
    public String save3(@ModelAttribute Item item){
        //(Model_key)를 생략한 경우 데이터 클래스의 첫 글자를 소문자로 바꿔서 model_key로 만들어서 model에 추가해줌
        itemRepository.save(item);

        return "basic/item";
    }

    @PostMapping("/add4")
    public String save4(Item item){
        //(Model_key)를 생략한 경우 데이터 클래스의 첫 글자를 소문자로 바꿔서 model_key로 만들어서 model에 추가해줌
        itemRepository.save(item);

        return "basic/item";
    }

    @PostMapping("/add5")
    public String save5(@ModelAttribute Item item){
        itemRepository.save(item);

        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add6")
    public String save6(@ModelAttribute Item item, RedirectAttributes redirectAttributes){
        Item saved = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", saved.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){

        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";    //view의 파일명이 아니라 요청 URL임
    }

}
