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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
        if (filePath.equals("default.png")) fileName = s3Uploader.get("productDefault.png"); // 기본 이미지
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
        if (!deleteProduct.getImage().contains("productDefault.png")) s3Uploader.deleteFileS3(deleteProduct.getImage());

        choiceProductRepository.deleteByProductId(productId);
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDto choiceProduct(Long managerId, Long shopId, Long productId) {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        Product choiceProduct = productRepository.findByShopIdAndId(shopId, productId).orElseThrow(CProductNotFoundException::new);
        Shop shop = shopRepository.findById(shopId).orElseThrow(CShopNotFoundException::new);
        choiceProductRepository.findByShopId(shop.getId()).updateProduct(choiceProduct);
        return new ProductDto(choiceProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> recommendProducts() {
        return productRepositorySupport.recommendProducts();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> productsPerCategory(String category, String method, Pageable pageable) {
        List<ProductDto> data = new ArrayList<>();
//        if (StringUtils.hasText(category)) { // 카테고리가 있으면
//            choiceProductList.forEach(row -> {
//                if (row.getShop().getCategory().contains(category)) { // 해당 shop category 에 해당하면
//                    if (row.getProduct() == null) { // 만약 선택한 product 가 없으면
//                        //가게에서 가장 할인율 높은거
//                        data.add(new ProductDto(
//                                productRepositorySupport.choiceHighestRateProductPerShop(row.getShop().getId())));
//                    }
//                    else data.add(new ProductDto(row.getProduct())); // 선택한 product 가 있으면
//                }
//            });
//        }
//        else { // 카테고리가 없는 전체 보기면
//            choiceProductList.forEach(row -> {
//                if (row.getProduct() == null) { // 만약 선택한 product 가 없으면
//                    //가게에서 가장 할인율 높은거
//                    data.add(new ProductDto(
//                            productRepositorySupport.choiceHighestRateProductPerShop(row.getShop().getId())));
//                }
//                else data.add(new ProductDto(row.getProduct())); // 선택한 product 가 있으면
//            });
//        }
        if (StringUtils.hasText(category)) { // 카테고리가 있으면
            List<ChoiceProduct> all = choiceProductRepository.findProductsByShopCategory(category);
            all.forEach(row -> {
                if (row.getProduct() != null) // 추천 상품 있으면
                    data.add(new ProductDto(row.getProduct()));
                else // 추천 상품이 없다면
                {
                    if (productRepository.existsByShopId(row.getShop().getId())) { // 상품이 있으면
                        data.add(new ProductDto(productRepositorySupport.choiceHighestRateProductPerShop(row.getShop().getId())));
                    }
                }
            });
        }
        else { // 카테고리가 없는 전체 보기면
            Page<ChoiceProduct> choiceProductList = choiceProductRepository.findAll(pageable);
            choiceProductList.forEach(row -> {
                if (row.getProduct() != null) // 추천 상품 있으면
                    data.add(new ProductDto(row.getProduct()));
                else // 추천 상품이 없다면
                {
                    if (productRepository.existsByShopId(row.getShop().getId())) { // 상품이 있으면
                        data.add(new ProductDto(productRepositorySupport.choiceHighestRateProductPerShop(row.getShop().getId())));
                    }
                }
            });
        }
        List<ProductDto> sortData;
        if (!StringUtils.hasText(method)) { // method 가 없는 기본 값이면 최신순 (id 높은 순으로)
            log.info("method : null");
            sortData =  data.stream().sorted(Comparator.comparing(ProductDto::getId).reversed()).collect(Collectors.toList());
        }
        else if (method.equals("rate")) {
            log.info("method : " + method);
            sortData = data.stream()
                    .sorted((a, b) -> (int) (((a.getPrice() - a.getDiscountedPrice()) / a.getPrice().doubleValue() * 100) -
                            ((b.getPrice() - b.getDiscountedPrice()) / b.getPrice().doubleValue() * 100))).collect(Collectors.toList());

        }
        else if (method.equals("discount")) {
            log.info("method : " + method);
            sortData =  data.stream().sorted(Comparator.comparing(ProductDto::getDiscountedPrice)).collect(Collectors.toList());
        } else throw new CValidCheckException("method 가 잘못 되었습니다.");
        return sortData;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findProductsPerShopSortByPriority(Long shopId) {
        if (shopRepository.findById(shopId).isEmpty()) throw new CShopNotFoundException();

        // 해당 shop 의 모든 product 들
        List<Product> products = productRepository.findAllByShopId(shopId);

        // 아직 가게에 등록된 상품이 없으면 빈값 return
        if (products.isEmpty()) return new ArrayList<>();

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