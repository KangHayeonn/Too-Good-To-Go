package com.toogoodtogo.configuration;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import com.toogoodtogo.domain.shop.shopboard.ShopBoard;
import com.toogoodtogo.domain.shop.shopboard.ShopBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final ShopBoardRepository shopBoardRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        addShops();
        addProducts();
        addShopBoards();
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

    private void addShopBoards() {
        ShopBoard shopBoard1 = new ShopBoard();
        shopBoard1.setShopName("김치찌개전문점");
        shopBoard1.setImage("https://t1.daumcdn.net/cfile/tistory/997B7F415AC1F5343F");
        shopBoard1.setProductName("돼지고기 듬뿍 김치찌개");
        shopBoard1.setPrice(15000L);
        shopBoard1.setDiscountPrice(13400L);
        shopBoard1.setCategory(new String[]{"한식"});
        shopBoardRepository.save(shopBoard1);

        ShopBoard shopBoard2 = new ShopBoard();
        shopBoard2.setShopName("감자탕전문점");
        shopBoard2.setImage("https://ww.namu.la/s/c936ceb9f23d22e50b151bf10d0734b265868128b6746f81c959ffb0b544573cd4a9da7eff22969f39c0961c49325796c7ed09a0b6726e7733be0c294aa71a82287dac5e5de6edd376a13695b267401496f534ad04fb71a268094b812282c923");
        shopBoard2.setProductName("기본 감자탕");
        shopBoard2.setPrice(25000L);
        shopBoard2.setDiscountPrice(19900L);
        shopBoard2.setCategory(new String[]{"한식", "찜탕"});
        shopBoardRepository.save(shopBoard2);

        ShopBoard shopBoard3 = new ShopBoard();
        shopBoard3.setShopName("전통전주비빔밥");
        shopBoard3.setImage("https://www.menupan.com/restaurant/restimg/007/zzmenuimg/h5026674_z.jpg");
        shopBoard3.setProductName("전주비빔밥");
        shopBoard3.setPrice(7000L);
        shopBoard3.setDiscountPrice(5000L);
        shopBoard3.setCategory(new String[]{"한식"});
        shopBoardRepository.save(shopBoard3);

        ShopBoard shopBoard4 = new ShopBoard();
        shopBoard4.setShopName("기사식당");
        shopBoard4.setImage("https://cdn.cashfeed.co.kr/attachments/335e6bcd04.jpg");
        shopBoard4.setProductName("고등어구이정식");
        shopBoard4.setPrice(8000L);
        shopBoard4.setDiscountPrice(4000L);
        shopBoard4.setCategory(new String[]{"한식"});
        shopBoardRepository.save(shopBoard4);

        ShopBoard shopBoard5 = new ShopBoard();
        shopBoard5.setShopName("BBQ");
        shopBoard5.setImage("https://cdn.onews.tv/news/photo/202102/46760_49203_4846.jpg");
        shopBoard5.setProductName("황금올리브치킨");
        shopBoard5.setPrice(19000L);
        shopBoard5.setDiscountPrice(13900L);
        shopBoard5.setCategory(new String[]{"치킨", "패스트푸드"});
        shopBoardRepository.save(shopBoard5);
    }
}
