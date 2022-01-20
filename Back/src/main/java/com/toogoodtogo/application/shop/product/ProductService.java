package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.advice.exception.CProductNotFoundException;
import com.toogoodtogo.advice.exception.CShopNotFoundException;
import com.toogoodtogo.advice.exception.CValidCheckException;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import com.toogoodtogo.domain.shop.product.ProductRepositorySupport;
import com.toogoodtogo.web.shops.products.dto.AddProductRequest;
import com.toogoodtogo.web.shops.products.dto.ProductDto;
import com.toogoodtogo.web.shops.products.dto.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ProductRepositorySupport productRepositorySupport;

    @Autowired
    private ShopRepository shopRepository;

    @Transactional(readOnly = true)
    public List<ProductDto> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductDto(product.getShop().getId(), product.getShop().getName(), product))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findProducts(Long shopId) {
        return productRepository.findAllByShopId(shopId)
                .stream()
                .map(product -> new ProductDto(product.getShop().getId(), product.getShop().getName(), product))
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto addProduct(Long managerId, Long shopId, AddProductRequest request) {
        Shop shop = shopRepository.findByUserIdAndId(managerId, shopId).orElseThrow(CShopNotFoundException::new);
        if(productRepository.findByShopIdAndName(shopId, request.getName()).isPresent())
            throw new CValidCheckException("이미 있는 상품입니다.");
//        Product product = Product.builder()
//                .shop(shop)
//                .name(request.getName())
//                .price(request.getPrice())
//                .discountedPrice(request.getDiscountedPrice())
//                .image(request.getImage())
//                .build();
//        return new ProductDto(productRepository.save(product));
        // ProductCard로 하고 product.shop.id 하면 안되나?
        return new ProductDto(shop.getId(), shop.getName(), productRepository.save(request.toEntity(shop)));
    }

    @Transactional
    public ProductDto updateProduct(Long managerId, Long productId, UpdateProductRequest request) {
        Product modifiedProduct = productRepository.findByUserIdAndId(managerId, productId).orElseThrow(CProductNotFoundException::new);
        modifiedProduct.update(request.getName(), request.getPrice(), request.getDiscountedPrice(), request.getImage());
        return new ProductDto(modifiedProduct.getShop().getId(), modifiedProduct.getShop().getName() ,modifiedProduct);
    }

    @Transactional
    public String deleteProduct(Long managerId, Long productId) {
        if (!productRepository.findByUserIdAndId(managerId, productId).isPresent()) throw new CProductNotFoundException();
        productRepository.deleteById(productId);
        return "success";
    }

    @Transactional(readOnly = true)
    public List<ProductDto> recommendProducts() {
        return productRepositorySupport.recommendProducts();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> sortProductsPerCategory(String category, String method) {
        return productRepositorySupport.sortProductsPerCategory(category, method);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> sortProductsPerShop(Long shopId, String method) {
        return productRepositorySupport.sortProductsPerShop(shopId, method);
    }
}