package com.toogoodtogo.application.shop;

import com.toogoodtogo.web.shops.ShopAddReq;
import com.toogoodtogo.web.shops.ShopDto;
import com.toogoodtogo.web.shops.ShopUpdateReq;

import java.util.List;

public interface ShopUseCase {
    List<ShopDto> findAllShops();
    List<ShopDto> findShops(Long managerId);
    ShopDto addShop(Long managerId, ShopAddReq request);
    ShopDto updateShop(Long managerId, Long shopId, ShopUpdateReq request);
    String deleteShop(Long managerId, Long shopId);
}
