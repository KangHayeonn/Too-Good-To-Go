package com.toogoodtogo.application.shop;

import com.toogoodtogo.domain.shop.Shop;

import java.util.List;

public interface ShopUseCase {

    List<Shop> findAllShops();

}
