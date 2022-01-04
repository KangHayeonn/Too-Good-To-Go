package com.toogoodtogo.domain.shop.product;

import com.toogoodtogo.domain.shop.Shop;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private String name;

    private Long price;

    private Long discountedPrice;

    private String image;

    @Builder
    public Product(Shop shop, String name, Long price, Long discountedPrice, String image) {
        this.shop = shop;
        this.name = name;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.image = image;
    }

    public void update(String name, Long price, Long discountedPrice, String image) {
        this.name = name;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.image = image;
    }
}
