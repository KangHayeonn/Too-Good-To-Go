package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import com.toogoodtogo.web.shops.products.AddProductRequest;
import com.toogoodtogo.web.shops.products.ProductDto;
import com.toogoodtogo.web.shops.products.UpdateProductRequest;
import com.toogoodtogo.web.users.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Transactional(readOnly = true)
    public List<ProductDto> findAllProducts(Long shopId) {
        return productRepository.findAllByShopId(shopId)
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto addProduct(Long shopId, AddProductRequest request) {
        Shop shop = shopRepository.findById(shopId).orElseThrow();
        Product product = Product.builder()
                .shop(shop)
                .name(request.getName())
                .price(request.getPrice())
                .discountedPrice(request.getDiscountedPrice())
                .image(request.getImage())
                .build();
        return new ProductDto(productRepository.save(product));
    }

    @Transactional
    public ProductDto updateProduct(Long productId, UpdateProductRequest request) {
        Product modifiedProduct = productRepository.findById(productId).orElseThrow();
        modifiedProduct.update(request.getName(), request.getPrice(), request.getDiscountedPrice(), request.getImage());
        return new ProductDto(modifiedProduct);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}