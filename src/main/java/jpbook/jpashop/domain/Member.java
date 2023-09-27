package jpbook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
@Entity
@Getter
@Setter
public class Member {

    //idenitiy = GeneratedValue
    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    //우리가 만든 자료형을 매핑해서 쓸려고 하는 어노테이션
    @Embedded
    private Address address;

    //1대 다 관계로 쓸려고 하는 것이다
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


}
