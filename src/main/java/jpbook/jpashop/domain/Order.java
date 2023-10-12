package jpbook.jpashop.domain;

import jpbook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    //기본키 설정
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //주문회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItme> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) //Lazy 지연로딩, 앞에서 처리하고 뒤에서 후처리로 들고오는것
    @JoinColumn(name = "dilivery_id")
    private Delivery delivery; //배송정보

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //연관관계 메서드
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItme orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItme... orderItmes){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItme orderItme : orderItmes){
            order.addOrderItem(orderItme);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    // 비즈니스 로직

    // 주문 취소
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가함");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItme orderItme : orderItems){
            orderItme.cancel();
        }

    }

    // 전체가격 조회 로직
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItme orderItme : orderItems){
            totalPrice += orderItme.getTotalPrice();
        }
        return totalPrice;
    }

}
