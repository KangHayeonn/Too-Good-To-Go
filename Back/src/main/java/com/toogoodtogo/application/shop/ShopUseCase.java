package com.toogoodtogo.application.shop;

import com.toogoodtogo.web.shops.dto.AddShopRequest;
import com.toogoodtogo.web.shops.dto.ShopDto;
import com.toogoodtogo.web.shops.dto.UpdateShopRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ShopUseCase {
    List<ShopDto> findAllShops();
    List<ShopDto> findShops(Long managerId);
    ShopDto addShop(Long managerId, MultipartFile file, AddShopRequest request) throws IOException;
    ShopDto updateShop(Long managerId, Long shopId, MultipartFile file, UpdateShopRequest request) throws IOException;
    String deleteShop(Long managerId, Long shopId);
}
