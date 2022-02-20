package com.toogoodtogo.domain.shop.product;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

class ProductRepositorySupportTest extends ControllerTest {
    @Autowired
    ProductRepositorySupport productRepositorySupport;

    @Test
    public void test () throws Exception {
        choiceProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        displayProductRepository.deleteAllInBatch();
        shopRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        //given
        User manager = userRepository.save(User.builder()
                .email("productTest@email.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .phone("01000000000")
                .role("ROLE_MANAGER")
                .build());
        Shop shop1 = Shop.builder()
                .user(manager).name("shop1").image("https://diefqsnmvol80.cloudfront.net/shopDefault.png")
                .category(Arrays.asList("한식", "중식")).build();
        shopRepository.save(shop1);

        Shop shop2 = Shop.builder()
                .user(manager).name("shop2").image("https://diefqsnmvol80.cloudfront.net/shopDefault.png")
                .category(Arrays.asList("한식", "중식")).build();
        shopRepository.save(shop2);

        Product product1 = Product.builder()
                .shop(shop1).name("김치찌개").price(10000L).discountedPrice(9000L)
                .image("https://diefqsnmvol80.cloudfront.net/productDefault.png").build();
        Product product2 = Product.builder()
                .shop(shop2).name("된장찌개").price(11000L).discountedPrice(10000L).image("test2").build();
        Product product3 = Product.builder()
                .shop(shop1).name("마라탕").price(12000L).discountedPrice(11000L).image("test3").build();
        Product product4 = Product.builder()
                .shop(shop1).name("짜글이").price(13000L).discountedPrice(12000L).image("test4").build();
        Product product5 = Product.builder()
                .shop(shop1).name("오뎅탕").price(50000L).discountedPrice(1000L).image("test5").build();
        productRepository.save(product1);
        Product save1 = productRepository.save(product2);
        Product save2 = productRepository.save(product3);
        Product save3 = productRepository.save(product4);
        Product save4 = productRepository.save(product5);

        choiceProductRepository.save(ChoiceProduct.builder().shop(shop1).product(product1).build());
        choiceProductRepository.save(ChoiceProduct.builder().shop(shop2).product(null).build());
        //when
        List<ChoiceProduct> test = choiceProductRepository.findProductsByShopCategory("한식");
        test.forEach(p-> System.out.println("p = " + p.getProduct()));
        //then

    }
}