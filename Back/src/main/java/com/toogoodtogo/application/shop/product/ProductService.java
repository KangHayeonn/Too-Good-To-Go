package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts(Long shopId) { return productRepository.findAllByShopId(shopId); }

    @Override
    public List<Product> findAllShopBoards() { return productRepository.findAll(); }

    @Override
    @Transactional
    public Product addProduct(Shop shop, String name, Long price, Long discountedPrice, String image) {
        Product product = Product.builder()
                .shop(shop)
                .name(name)
                .price(price)
                .discountedPrice(discountedPrice)
                .image(image)
                .build();
        productRepository.save(product);
        return product;
    }
}