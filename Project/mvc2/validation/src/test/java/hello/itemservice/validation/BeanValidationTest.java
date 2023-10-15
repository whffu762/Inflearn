package hello.itemservice.validation;

import hello.itemservice.domain.item.Item;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {

    @Test
    void beanValdation(){

        ValidatorFactory f = Validation.buildDefaultValidatorFactory();
        Validator v = f.getValidator();

        Item item = new Item();
        item.setItemName(" ");
        item.setPrice(10);
        item.setQuantity(10000);

        Set<ConstraintViolation<Item>> violations = v.validate(item);

        for(ConstraintViolation<Item> violation : violations){
            System.out.println("vio = " + violation);
            System.out.println("vio msg =" + violation.getMessage());
        }
    }
}
