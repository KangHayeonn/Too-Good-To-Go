package com.toogoodtogo.application.shop;

import com.toogoodtogo.domain.shop.exceptions.CShopNotFoundException;
import com.toogoodtogo.domain.user.exceptions.CUserNotFoundException;
import com.toogoodtogo.advice.exception.CValidCheckException;
import com.toogoodtogo.domain.shop.Hours;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.web.shops.dto.AddShopRequest;
import com.toogoodtogo.web.shops.dto.ShopDto;
import com.toogoodtogo.web.shops.dto.UpdateShopRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService implements ShopUseCase {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ShopDto> findAllShops() {
        return shopRepository.findAll()
                .stream()
                .map(ShopDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShopDto> findShops(Long managerId) {
        User manager = userRepository.findById(managerId).orElseThrow(CUserNotFoundException::new);
        return shopRepository.findByUser(manager)
                .stream().map(ShopDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShopDto addShop(Long managerId, AddShopRequest request) {
        User manager = userRepository.findById(managerId).orElseThrow(CUserNotFoundException::new); //예외 처리!!
        if(shopRepository.findByAddressAndName(request.getAddress(), request.getName()).isPresent())
            throw new CValidCheckException("이미 있는 가게입니다.");
        Shop shop = Shop.builder()
                .user(manager)
                .name(request.getName())
                .image(request.getImage())
                .category(request.getCategory())
                .phone(request.getPhone())
                .address(request.getAddress())
                .hours(new Hours(request.getOpen(), request.getClose()))
                .build();
        return new ShopDto(shopRepository.save(shop));
    }

    @Override
    @Transactional
    public ShopDto updateShop(Long managerId, Long shopId, UpdateShopRequest request) {
        Shop modifiedShop = shopRepository.findByUserIdAndId(managerId, shopId).orElseThrow(CShopNotFoundException::new);
        modifiedShop.update(request.getName(), request.getImage(), request.getCategory(), request.getPhone(), request.getAddress(), new Hours(request.getOpen(), request.getClose()));
        return new ShopDto(modifiedShop);
    }

    @Override
    @Transactional
    public String deleteShop(Long managerId, Long shopId) {
        Shop deleteShop = shopRepository.findByUserIdAndId(managerId, shopId).orElseThrow(CShopNotFoundException::new);
        productRepository.deleteByShopId(deleteShop.getId());
        shopRepository.deleteById(deleteShop.getId());
        return "success";
    }
}
