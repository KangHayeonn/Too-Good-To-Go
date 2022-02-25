package com.toogoodtogo.domain.shop.product;

import com.toogoodtogo.domain.shop.Shop;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private Long discountedPrice;
    private String image;

    public void update(String name, Long price, Long discountedPrice) {
        this.name = name;
        this.price = price;
        this.discountedPrice = discountedPrice;
    }

    public void updateImage(String image) {
        this.image = image;
    }
}
