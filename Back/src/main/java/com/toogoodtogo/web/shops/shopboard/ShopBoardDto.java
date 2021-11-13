package com.toogoodtogo.web.shops.shopboard;

import com.toogoodtogo.domain.shop.shopboard.ShopBoard;
import lombok.Getter;

@Getter
public class ShopBoardDto {

    private final Long id;
    private final String shopName;
    private final String image;

    private final String productName;
    private final Long price;
    private final Long discountPrice;
    private final String[] category;

    public ShopBoardDto(ShopBoard shopBoard) {
        this.id = shopBoard.getId();
        this.shopName = shopBoard.getShopName();
        this.image = shopBoard.getImage();

        this.productName = shopBoard.getProductName();
        this.price = shopBoard.getPrice();
        this.discountPrice = shopBoard.getDiscountPrice();
        this.category = shopBoard.getCategory();
    }
}
