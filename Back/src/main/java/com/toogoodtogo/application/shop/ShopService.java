package com.toogoodtogo.application.shop;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.web.shops.ShopDto;
import com.toogoodtogo.web.users.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService implements ShopUseCase {
    private final ShopRepository shopRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ShopDto> findAllShops() {
        return shopRepository.findAll()
                .stream()
                .map(ShopDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Shop addShop(String name, String category, String image) {
        Shop shop = Shop.builder()
                .name(name)
                .image(image)
                .category(category)
                .build();
        shopRepository.save(shop);
        return shop;
    }
}
