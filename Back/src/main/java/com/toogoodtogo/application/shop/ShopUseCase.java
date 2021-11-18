package com.toogoodtogo.application.shop;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.web.shops.ShopDto;

import java.util.List;

public interface ShopUseCase {

    List<ShopDto> findAllShops();

    Shop addShop(String name, String shopCategory, String image);

}
