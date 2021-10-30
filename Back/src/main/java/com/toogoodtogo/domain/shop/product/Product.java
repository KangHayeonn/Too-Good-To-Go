package com.toogoodtogo.domain.shop.product;

import com.toogoodtogo.domain.shop.Shop;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Shop shop;

    private String name;

    private Long price;

    private Long discountedPrice;

    private String image;
}
