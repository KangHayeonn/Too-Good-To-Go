//package com.toogoodtogo.domain.shop;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@SpringBootTest
//@Transactional
//class ShopRepositoryTest {
//    @Autowired
//    ShopRepository shopRepository;
//
//    @Test
//    public void shopAddTest() {
//        //given
//        Shop shopA = new Shop();
//        shopA.setName("shopA");
//        Shop savedShop = shopRepository.save(shopA);
//
//        //when
//        Optional<Shop> findShop = shopRepository.findById(shopA.getId());
//
//        //then
//        Assertions.assertThat(findShop)
//                .isPresent()
//                .hasValue(savedShop);
//    }
//}
