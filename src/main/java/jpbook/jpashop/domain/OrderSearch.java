package jpbook.jpashop.domain;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

}
