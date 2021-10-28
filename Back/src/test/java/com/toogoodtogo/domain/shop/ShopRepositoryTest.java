package com.toogoodtogo.domain.shop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ShopRepositoryTest {
    @Autowired
    ShopRepository shopRepository;

    @Test
    public void shopAddTest() throws Exception {
        //given
        Shop shopA = new Shop();
        shopA.setId(123L);
        shopA.setName("shopA");
        shopRepository.save(shopA);
        //when
        Shop findShop1 = shopRepository.findById(shopA.getId()).get();
        //then
        Assertions.assertThat(findShop1).isEqualTo(shopA);

    }
}