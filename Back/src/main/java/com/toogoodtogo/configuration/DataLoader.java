package com.toogoodtogo.configuration;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        addShops();
        addProducts();
    }

    private void addShops() {
        Shop shop1 = new Shop();
        shop1.setName("백채김치찌개 봉천점");
        shop1.setImage("image1");
        shop1.setCategory("한식");
        shopRepository.save(shop1);

        Shop shop2 = new Shop();
        shop2.setName("노란통닭 방배점");
        shop2.setImage("image2");
        shop2.setCategory("치킨");
        shopRepository.save(shop2);
    }

    private void addProducts() {
        Shop shop1 = shopRepository.findById(1L).orElseThrow();
        Shop shop2 = shopRepository.findById(2L).orElseThrow();

        Product product1 = Product.builder()
                .shop(shop1)
                .name("백채찌개")
                .price(1000L)
                .discountedPrice(800L)
                .image("productImage1")
                .build();

        Product product2 = Product.builder()
                .shop(shop1)
                .name("달걀말이")
                .price(2000L)
                .discountedPrice(1000L)
                .image("productImage2")
                .build();

        Product product3 = Product.builder()
                .shop(shop2)
                .name("맛있는 치킨")
                .price(500L)
                .discountedPrice(500L)
                .image("productImage3")
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }
}
