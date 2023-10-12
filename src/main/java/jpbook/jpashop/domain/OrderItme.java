package jpbook.jpashop.domain;

import jpbook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_itme")
@Getter @Setter
public class OrderItme {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; //주문상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; //주문

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    // 생성 메서드
    public static OrderItme createOrderItem(Item item, int orderPrice, int count){
        OrderItme orderItme = new OrderItme();
        orderItme.setItem(item);
        orderItme.setOrderPrice(orderPrice);
        orderItme.setCount(count);

        item.removeStock(count);
        return orderItme;
    }

    // 비즈니스 로직
    public void cancel() {
        getItem().addStock(count);
    }

    //전체가격 비즈니스 로직
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
