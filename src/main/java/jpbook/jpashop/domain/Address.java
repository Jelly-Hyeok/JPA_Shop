package jpbook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address(){ //기본생성자 =
    }

    public Address(String city, String street, String zipcode){
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}
/*
엔티티 설계시 주의점
    1. 엔티티에는 가급적 Setter를 사용 X
        : (예를 들어 Setter가 다 있다고 침)
        : 변경/수정 될수있는 포인트가 너무 많음
        : 유지보수가 어렵다.
        : 리팩토링할때 /으로 Setter 제거

*/
