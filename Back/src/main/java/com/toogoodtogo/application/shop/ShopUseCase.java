package com.toogoodtogo.application.shop;

import com.toogoodtogo.web.shops.dto.AddShopRequest;
import com.toogoodtogo.web.shops.dto.ShopDto;
import com.toogoodtogo.web.shops.dto.UpdateShopRequest;
import com.toogoodtogo.web.shops.products.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ShopUseCase {
    List<ShopDto> findAllShops();
    List<ShopDto> findShops(Long managerId);
    ShopDto findShop(Long shopId);
    ShopDto addShop(Long managerId, MultipartFile file, AddShopRequest request) throws IOException;
    ShopDto updateShop(Long managerId, Long shopId, UpdateShopRequest request);
    String updateShopImage(Long managerId, Long shopId, MultipartFile file) throws IOException;
    void deleteShopImage(Long managerId, Long shopId);
    void deleteShop(Long managerId, Long shopId);
    List<ShopDto> findShopsBySearch(String keyword);
}
