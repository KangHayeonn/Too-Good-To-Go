package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.application.S3Uploader;
import com.toogoodtogo.application.UploadFileConverter;
import com.toogoodtogo.domain.security.exceptions.CAccessDeniedException;
import com.toogoodtogo.domain.shop.product.*;
import com.toogoodtogo.domain.shop.product.exceptions.CProductNotFoundException;
import com.toogoodtogo.domain.shop.exceptions.CShopNotFoundException;
import com.toogoodtogo.advice.exception.CValidCheckException;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.web.shops.products.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements ProductUseCase {
    private final ProductRepository productRepository;
    private final ProductRepositorySupport productRepositorySupport;
    private final ShopRepository shopRepository;
    private final UploadFileConverter uploadFileConverter;
    private final S3Uploader s3Uploader;
    private final DisplayProductRepository displayProductRepository;
    private final ChoiceProductRepository choiceProductRepository;

    @Transactional(readOnly = true)
    public List<ProductDto> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findProducts(Long shopId) {
        return productRepository.findAllByShopId(shopId)
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto addProduct(Long managerId, Long shopId, MultipartFile file, AddProductRequest request) throws IOException {
        Shop shop = shopRepository.findByUserIdAndId(managerId, shopId).orElseThrow(CShopNotFoundException::new);
        if(productRepository.findByShopIdAndName(shopId, request.getName()).isPresent())
            throw new CValidCheckException("이미 있는 상품입니다.");

        // 파일 경로
        String filePath = uploadFileConverter.parseFileInfo(file, "productsImage", shopId);
        
        String fileName;
        if (filePath.equals("default.png")) fileName = "default.png"; // 기본 이미지
        else fileName = s3Uploader.upload(file, filePath); // 최종 파일 경로 및 파일 업로드

        Product new_product = Product.builder()
                .shop(shop)
                .name(request.getName())
                .price(request.getPrice())
                .discountedPrice(request.getDiscountedPrice())
                .image(fileName)
                .build();
        productRepository.save(new_product);

        // product 순서
        DisplayProduct dp = displayProductRepository.findByShopId(shopId);
        if(displayProductRepository.findByShopId(shopId) == null) { // 만약 displayProduct 가 없으면
            displayProductRepository.save(
                    DisplayProduct.builder().shop(shop)
                            .priority(new ArrayList<>(Arrays.asList(String.valueOf(new_product.getId())))).build());
        }
        else displayProductRepository.findByShopId(shopId).addPrior(new_product.getId());

        return new ProductDto(new_product);
//        return ProductDto.builder().product(productRepository.save(new_product)).build();
//        return ProductDto.builder()
//                .shopId(shop.getId())
//                .shopName(shop.getName())
//                .product(productRepository.save(request.toEntity(shop))).build();
    }

    private boolean checkAccessOfShop(Long managerId, Long shopId) {
        return shopRepository.findById(shopId).orElseThrow(CShopNotFoundException::new).getUser().getId().equals(managerId);
    }

    @Transactional
    public ProductDto updateProduct(Long managerId, Long shopId, Long productId, MultipartFile file, UpdateProductRequest request) throws IOException {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        Product modifiedProduct = productRepository.findByShopIdAndId(shopId, productId).orElseThrow(CProductNotFoundException::new);
        String filePath;
        String fileName;
        if (file.isEmpty()) { // 넘어온 사진이 없으면
            fileName = modifiedProduct.getImage(); // 기존 이미지
        } else { // 넘어온 사진이 있으면
            filePath = uploadFileConverter.parseFileInfo(file, "productsImage", modifiedProduct.getShop().getId());
            fileName = s3Uploader.updateS3(file, modifiedProduct.getImage(), filePath);
        }

        modifiedProduct.update(request.getName(), request.getPrice(), request.getDiscountedPrice(), fileName);
        return new ProductDto(modifiedProduct);
//        return ProductDto.builder()
//                .shopId(modifiedProduct.getShop().getId())
//                .shopName(modifiedProduct.getShop().getName())
//                .product(modifiedProduct).build();
    }

    @Transactional
    public List<String> updateProductPriority(Long managerId, Long shopId, Long productId, UpdateProductPriorityRequest request) {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        DisplayProduct displayProduct = displayProductRepository.findByShopId(shopId);
        displayProduct.update(request.getProductsId());
        return displayProduct.getPriority();
    }

    @Transactional
    public void deleteProduct(Long managerId, Long shopId, Long productId) {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        Product deleteProduct = productRepository.findByShopIdAndId(shopId, productId).orElseThrow(CProductNotFoundException::new);
        // 기본 이미지가 아니면 S3에서 이미지 삭제
        if (!deleteProduct.getImage().equals("default.png")) s3Uploader.deleteFileS3(deleteProduct.getImage());

        choiceProductRepository.deleteByProductId(productId);
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDto choiceProduct(Long managerId, Long shopId, Long productId) {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        Product choiceProduct = productRepository.findByShopIdAndId(shopId, productId).orElseThrow(CProductNotFoundException::new);
        Shop shop = shopRepository.findById(shopId).orElseThrow(CShopNotFoundException::new);
        if(choiceProductRepository.findByShopId(shop.getId()) == null) { // 고른게 없었으면 추가
            choiceProductRepository.save(ChoiceProduct.builder().shop(shop).product(choiceProduct).build());
        } else choiceProductRepository.findByShopId(shop.getId()).updateProduct(choiceProduct); // 있었으면 기존 product 교체
        return new ProductDto(choiceProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> recommendProducts() {
        return productRepositorySupport.recommendProducts();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> productsPerCategory(String category, String method) {
        List<ChoiceProduct> all = choiceProductRepository.findAll();
        List<ProductDto> data = new ArrayList<>();
        all.forEach(choiceProduct -> {
            if (choiceProduct.getShop().getCategory().contains(category)) { // 해당 shop 의 category 가 적합할 때
                if (choiceProduct.getProduct() == null) { // 만약 선택한 product 가 없으면
                    //가게에서 가장 할인율 높은거
                    data.add(new ProductDto(
                            productRepositorySupport.choiceHighestRateProductPerShop(choiceProduct.getShop().getId())));
                }
                else data.add(new ProductDto(choiceProduct.getProduct())); // 선택한 product 가 있으면
            }
        });
        return data;
//        return productRepositorySupport.productsPerCategory2(category, method);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findProductsPerShopSortByPriority(Long shopId) {
        // 해당 shop 의 모든 product 들
        List<Product> products = productRepository.findAllByShopId(shopId);

        // product 순서
        List<String> priority = displayProductRepository.findByShopId(shopId).getPriority();

        List<ProductDto> display = new ArrayList<>();
        priority.forEach(num -> {
            products.forEach(product -> {
                if(product.getId().equals(Long.valueOf(num))) {
                    display.add(new ProductDto(product));
                }
            });
        });
        return display;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findProductsBySearch(String keyword) {
        // 검색량 저장하는 기능 추가...?
        return productRepository.findByNameContaining(keyword).stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }
}