package com.toogoodtogo.web.shops.shopboard;

import com.toogoodtogo.domain.shop.Shop;
import lombok.Getter;

@Getter
public class AddShopBoardRequest {
    private Shop shop;
    private String name;
    private Long price;
    private Long discountedPrice;
    private String image;
}