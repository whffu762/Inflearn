package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
//@ScriptAssert(lang="javascript", script = "_this.price * _this.quantity >= 10000", message = "총합의 최소는 10000원")
// 위 코드는 복잡한 조건을 작성하기엔 번거로움 그래서 컨트롤러에서 자바 코드로 작성하는게 더 편함
public class Item {

    //@NotNull(groups = UpdateCheck.class)    //수정 요구사항 1
    private Long id;

    //@NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;

    //@NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    //@Range(min = 1000, max = 1000000, groups = {SaveCheck.class, UpdateCheck.class})
    private Integer price;

    //@NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    //@Max(value = 9999, groups = SaveCheck.class) //수정 요구사항 2
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
