package com.toogoodtogo.domain.shop.shopboard;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ShopBoard {
    @Id
    @GeneratedValue
    private Long id;
    private String shopName;
    private String image;

    private String productName;
    private Long price;
    private Long discountPrice;
    private String category;

    @Builder
    public ShopBoard(String shopName, String image, String productName, Long price, Long discountPrice, String category) {
        this.shopName = shopName;
        this.image = image;
        this.productName = productName;
        this.price = price;
        this.discountPrice = discountPrice;
        this.category = category;
    }
}
