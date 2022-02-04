package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.application.S3Uploader;
import com.toogoodtogo.application.UploadFileConverter;
import com.toogoodtogo.domain.security.exceptions.CAccessDeniedException;
import com.toogoodtogo.domain.shop.product.exceptions.CProductNotFoundException;
import com.toogoodtogo.domain.shop.exceptions.CShopNotFoundException;
import com.toogoodtogo.advice.exception.CValidCheckException;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import com.toogoodtogo.domain.shop.product.ProductRepositorySupport;
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
    public ProductDto addProduct(Long managerId, Long shopId, MultipartFile file, AddProductRequest request) throws IOException {
        Shop shop = shopRepository.findByUserIdAndId(managerId, shopId).orElseThrow(CShopNotFoundException::new);
        if(productRepository.findByShopIdAndName(shopId, request.getName()).isPresent())
            throw new CValidCheckException("이미 있는 상품입니다.");

        // 파일 경로
        String filePath = uploadFileConverter.parseFileInfo(file, "productsImage", shopId);
        
        String fileName;
        if (filePath.equals("default.png")) fileName = "default.png"; // 기본 이미지
        else fileName = s3Uploader.upload(file, filePath); // 최종 파일 경로 및 파일 업로드

        // 해당 shop 의 product 갯수
        Long productPriority = productRepository.countProductByShopId(shopId);

        Product new_product = Product.builder()
                .shop(shop)
                .name(request.getName())
                .price(request.getPrice())
                .discountedPrice(request.getDiscountedPrice())
                .image(fileName)
                .priority(productPriority)
                .build();
        return new ProductDto(productRepository.save(new_product));
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
    public List<ProductDto> updateProductPriority(Long managerId, Long shopId, Long productId, UpdateProductPriorityRequest request) {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        Product modifiedProduct = productRepository.findByShopIdAndId(shopId, productId).orElseThrow(CProductNotFoundException::new);;
        // 해당 shop 의 priority 순으로 정렬된 product 들.
        List<Product> products = productRepositorySupport.findProductsPerShopSortByPriority(shopId);
        // 해당 shop 의 product 들 불러서 priority 로 정렬. prior 바뀌면 영향 가는 것들 순회하면서 +1 혹은 -1
        Long beforePriority = modifiedProduct.getPriority();
        Long afterPriority = request.getPriority();
        // 바꾸려는 product 의 우선순위가 더 낮을 때 / 숫자가 더 클 때 / 앞으로 이동할 때
        if (beforePriority > afterPriority) {
            for (Product product : products) {
                if (afterPriority <= product.getPriority() && product.getPriority() < beforePriority) {
                    product.updatePriority(product.getPriority() + 1);
                }
            }
            modifiedProduct.updatePriority(request.getPriority());
        }
        // 바꾸려는 product 의 우선순위가 더 높을 때 / 숫자가 더 낮을 때 / 뒤로 이동할 때
        else if (beforePriority < afterPriority) {
            for (Product product : products) {
                if (beforePriority < product.getPriority() && product.getPriority() < afterPriority) {
                    product.updatePriority(product.getPriority() - 1);
                }
            }
            modifiedProduct.updatePriority(request.getPriority()-1);
        }
        return productRepositorySupport.findProductsPerShopSortByPriority(shopId)
                .stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @Transactional
    public String deleteProduct(Long managerId, Long shopId, Long productId) {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        Product deleteProduct = productRepository.findByShopIdAndId(shopId, productId).orElseThrow(CProductNotFoundException::new);
        // 기본 이미지가 아니면 S3에서 이미지 삭제
        if (!deleteProduct.getImage().equals("default.png")) s3Uploader.deleteFileS3(deleteProduct.getImage());
        productRepository.deleteById(productId);
        return "success";
    }

    @Transactional(readOnly = true)
    public List<ProductTmp> recommendProducts() {
        return productRepositorySupport.recommendProducts();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> productsPerCategory(String category, String method) {
        return productRepositorySupport.productsPerCategory2(category, method);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findProductsPerShopSortByPriority(Long shopId) {
        return productRepositorySupport.findProductsPerShopSortByPriority(shopId)
                .stream().map(ProductDto::new).collect(Collectors.toList());
    }
}