package com.toogoodtogo.web.shops.products.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProductSearchDto {
    private Long shopId;
    private String shopName;
    private List<String> shopCategory;
    private Long id;
    private String name;
    private Long price;
    private Long discountedPrice;
    private String image;

    @Builder
    public ProductSearchDto(Long shopId, String shopName, String shopCategory,
                            Long id, String name, Long price, Long discountedPrice, String image) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopCategory = new ArrayList<String>(Arrays.asList(shopCategory.split(",")));
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.image = image;
    }
}
