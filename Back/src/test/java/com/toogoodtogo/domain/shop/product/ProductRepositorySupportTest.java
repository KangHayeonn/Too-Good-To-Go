package com.toogoodtogo.domain.shop.product;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.ControllerTest;
import com.toogoodtogo.web.shops.products.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class ProductRepositorySupportTest extends ControllerTest {
    @Autowired
    ProductRepositorySupport productRepositorySupport;

    @Autowired
    JdbcTemplateProductRepository jdbcTemplateProductRepository;

    @Test
    public void test () throws Exception {
        highestRateProductRepository.deleteAllInBatch();
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

//        Shop shop0 = Shop.builder()
//                .user(manager).name("shop0").image("https://diefqsnmvol80.cloudfront.net/shopDefault.png")
//                .category(Arrays.asList("중식")).build();
//        shopRepository.save(shop0);

        Shop shop1 = Shop.builder()
                .user(manager).name("shop1").image("https://diefqsnmvol80.cloudfront.net/shopDefault.png")
                .category(Arrays.asList("한식", "중식")).build();
        shopRepository.save(shop1);

        Shop shop2 = Shop.builder()
                .user(manager).name("shop2").image("https://diefqsnmvol80.cloudfront.net/shopDefault.png")
                .category(Arrays.asList("한식", "중식")).build();
        shopRepository.save(shop2);

        Shop shop3 = Shop.builder()
                .user(manager).name("shop3").image("https://diefqsnmvol80.cloudfront.net/shopDefault.png")
                .category(Arrays.asList("한식")).build();
        shopRepository.save(shop3);

        Shop shop4 = Shop.builder()
                .user(manager).name("shop4").image("https://diefqsnmvol80.cloudfront.net/shopDefault.png")
                .category(Arrays.asList("한식")).build();
        shopRepository.save(shop4);

        Shop shop5 = Shop.builder()
                .user(manager).name("shop5").image("https://diefqsnmvol80.cloudfront.net/shopDefault.png")
                .category(Arrays.asList("중식")).build();
        shopRepository.save(shop5);


//        Product product0 = Product.builder()
//                .shop(shop0).name("찌개").price(10000L).discountedPrice(9000L)
//                .image("https://diefqsnmvol80.cloudfront.net/productDefault.png").build();
        Product product1 = Product.builder()
                .shop(shop1).name("김치찌개").price(10000L).discountedPrice(9000L)
                .image("https://diefqsnmvol80.cloudfront.net/productDefault.png").build();
        Product product2 = Product.builder()
                .shop(shop2).name("된장찌개").price(11000L).discountedPrice(10000L).image("test2").build();
        Product product3 = Product.builder()
                .shop(shop1).name("마라탕").price(12000L).discountedPrice(11000L).image("test3").build();
        Product product4 = Product.builder()
                .shop(shop2).name("짜글이").price(13000L).discountedPrice(12000L).image("test4").build();
        Product product5 = Product.builder()
                .shop(shop4).name("오뎅탕").price(50000L).discountedPrice(1000L).image("test5").build();
        Product product6 = Product.builder()
                .shop(shop4).name("김피탕").price(80000L).discountedPrice(8000L).image("test6").build();
//        productRepository.save(product0);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);

        highestRateProductRepository.save(HighestRateProduct.builder()
                .shop(shop1).product(product3).build());
        highestRateProductRepository.save(HighestRateProduct.builder()
                .shop(shop2).product(product2).build());
        highestRateProductRepository.save(HighestRateProduct.builder()
                .shop(shop4).product(product5).build());

        choiceProductRepository.save(ChoiceProduct.builder().shop(shop2).product(product4).build());
        choiceProductRepository.save(ChoiceProduct.builder().shop(shop4).product(product6).build());
        //when
//        List<ProductDto> test = choiceProductRepository.findProductsByShopCategory("한식").stream()
//                .map(ProductDto::new).collect(Collectors.toList());
        List<ProductDto> test = jdbcTemplateProductRepository.findProductsByShopCategory("한식", PageRequest.of(0, 10));
        test.forEach(p -> System.out.println("p_id " + p.getId()));
        //then
        highestRateProductRepository.deleteAllInBatch();
        choiceProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        displayProductRepository.deleteAllInBatch();
        shopRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }
}