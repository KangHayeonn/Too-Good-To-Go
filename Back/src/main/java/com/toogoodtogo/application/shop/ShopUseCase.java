package com.toogoodtogo.application.shop;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.web.shops.AddShopRequest;
import com.toogoodtogo.web.shops.ShopDto;
import com.toogoodtogo.web.shops.UpdateShopRequest;

import java.util.List;

public interface ShopUseCase {
    List<ShopDto> findAllShops();
    List<ShopDto> findShops(Long managerId);
    ShopDto addShop(Long managerId, AddShopRequest request);
    ShopDto updateShop(Long managerId, Long shopId, UpdateShopRequest request);
    void deleteShop(Long managerId, Long shopId);
}
