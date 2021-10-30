package com.toogoodtogo.configuration;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final ShopRepository shopRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        addShops();
    }

    private void addShops() {
        Shop shop1 = new Shop();
        shop1.setName("shop1");
        shop1.setImage("image1");
        shop1.setCategory("category1");
        shopRepository.save(shop1);

        Shop shop2 = new Shop();
        shop2.setName("shop2");
        shop2.setImage("image2");
        shop2.setCategory("category2");
        shopRepository.save(shop2);
    }
}
