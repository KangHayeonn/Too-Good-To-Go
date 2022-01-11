package com.toogoodtogo.web.shops.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateReq {
    private String name;
    private Long price;
    private Long discountedPrice;
    private String image;
}
