package com.toogoodtogo.application.shop;

import com.toogoodtogo.web.shops.dto.AddShopRequest;
import com.toogoodtogo.web.shops.dto.ShopDto;
import com.toogoodtogo.web.shops.dto.UpdateShopRequest;

import java.util.List;

public interface ShopUseCase {
    List<ShopDto> findAllShops();
    List<ShopDto> findShops(Long managerId);
    ShopDto addShop(Long managerId, AddShopRequest request);
    ShopDto updateShop(Long managerId, Long shopId, UpdateShopRequest request);
    String deleteShop(Long managerId, Long shopId);
}
