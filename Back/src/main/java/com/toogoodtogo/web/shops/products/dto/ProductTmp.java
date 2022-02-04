package com.toogoodtogo.web.shops.products.dto;

import com.toogoodtogo.domain.shop.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductTmp {
    private Long shopId;
    private String shopName;
    private Long id;
    private String name;
    private Long price;
    private Long discountedPrice;
    private Long rate;
    private String image;
}