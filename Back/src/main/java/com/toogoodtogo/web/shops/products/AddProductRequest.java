package com.toogoodtogo.web.shops.products;

import com.toogoodtogo.domain.shop.Shop;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProductRequest {
    private String name;
    private Long price;
    private Long discountedPrice;
    private String image;

//    public toServiceDto() {
//
//    }
}