package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.application.S3Uploader;
import com.toogoodtogo.application.UploadFileConverter;
import com.toogoodtogo.domain.exceptions.CUploadImageInvalidException;
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
    private final HighestRateProductRepository highestRateProductRepository;
    private final JdbcTemplateProductRepository jdbcTemplateProductRepository;

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

        updateHighestProduct(shopId);
        return new ProductDto(new_product);
    }

    private boolean checkAccessOfShop(Long managerId, Long shopId) {
        return shopRepository.findById(shopId).orElseThrow(CShopNotFoundException::new).getUser().getId().equals(managerId);
    }

    @Transactional
    public ProductDto updateProduct(Long managerId, Long shopId, Long productId, UpdateProductRequest request) {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        if(productRepository.findByShopIdAndName(shopId, request.getName()).isPresent() &&
                !productRepository.findByShopIdAndName(shopId, request.getName()).get().getId().equals(productId))
            throw new CValidCheckException("이미 있는 상품 이름입니다."); // 바꾸려는 이름이 중복될 때
        Product modifiedProduct = productRepository.findByShopIdAndId(shopId, productId).orElseThrow(CProductNotFoundException::new);
        modifiedProduct.update(request.getName(), request.getPrice(), request.getDiscountedPrice());
        updateHighestProduct(shopId);
        return new ProductDto(modifiedProduct);
    }

    @Transactional
    public void deleteProduct(Long managerId, Long shopId, Long productId) {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        Product deleteProduct = productRepository.findByShopIdAndId(shopId, productId).orElseThrow(CProductNotFoundException::new);
        // 기본 이미지가 아니면 S3에서 이미지 삭제
        if (!deleteProduct.getImage().contains("productDefault.png")) s3Uploader.deleteFileS3(deleteProduct.getImage());

        HighestRateProduct byShopIdAndProductId = highestRateProductRepository.findByShopIdAndProductId(shopId, productId);
        if(byShopIdAndProductId != null) byShopIdAndProductId.deleteProduct();

        choiceProductRepository.deleteByProductId(productId);
        productRepository.deleteById(productId);
        updateHighestProduct(shopId);
    }

    @Transactional
    public String updateProductImage(Long managerId, Long shopId, Long productId, MultipartFile file) throws IOException {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        Product updateImageProduct = productRepository.findByShopIdAndId(shopId, productId).orElseThrow(CProductNotFoundException::new);
        String filePath = uploadFileConverter.parseFileInfo(file, "productsImage", updateImageProduct.getShop().getId());
        log.info("filePath : " + filePath);
        if(filePath.equals("default.png")) throw new CUploadImageInvalidException();
        String fileName = s3Uploader.updateS3(file, updateImageProduct.getImage(), filePath);
        updateImageProduct.updateImage(fileName);
        return fileName;
    }

    @Transactional
    public void deleteProductImage(Long managerId, Long shopId, Long productId) {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        Product deleteImageProduct = productRepository.findByShopIdAndId(shopId, productId).orElseThrow(CProductNotFoundException::new);

        if (!deleteImageProduct.getImage().contains("productDefault.png")) { // 기본 이미지가 아니면 상품과 S3에서 이미지 삭제
            s3Uploader.deleteFileS3(deleteImageProduct.getImage());
            deleteImageProduct.updateImage(s3Uploader.get("productDefault.png")); // 기본 이미지로 변경
        } else throw new CValidCheckException("기본 이미지입니다.");
    }

    @Transactional
    public List<String> updateProductPriority(Long managerId, Long shopId, UpdateProductPriorityRequest request) {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        if(request.getProductsId().size() != productRepository.countByShopId(shopId))
            throw new CValidCheckException("우선순위 갯수가 틀립니다.");
        request.getProductsId().forEach(p -> {
            if(productRepository.findByShopIdAndId(shopId, Long.valueOf(p)).isEmpty())
                throw new CValidCheckException("해당 가게에 없는 상품 아이디가 포함되있습니다.");
        });
        DisplayProduct displayProduct = displayProductRepository.findByShopId(shopId);
        displayProduct.update(request.getProductsId());
        return displayProduct.getPriority();
    }

    private void updateHighestProduct(Long shopId) {
        Product currentHighestRateProduct = productRepositorySupport.choiceHighestRateProductPerShop(shopId);
        if(currentHighestRateProduct == null) return;
        highestRateProductRepository.findByShopId(shopId).updateProduct(currentHighestRateProduct);
    }

    @Transactional
    public ProductDto choiceProduct(Long managerId, Long shopId, AddChoiceProductRequest request) {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        Product choiceProduct = productRepository.findByShopIdAndId(shopId, request.getProductId()).orElseThrow(CProductNotFoundException::new);
        Shop shop = shopRepository.findById(shopId).orElseThrow(CShopNotFoundException::new);
        if(choiceProductRepository.findByShopId(shop.getId()) == null) //만약 고른게 없었으면 새로 등록
            choiceProductRepository.save(ChoiceProduct.builder().shop(shop).product(choiceProduct).build());
        else choiceProductRepository.findByShopId(shop.getId()).updateProduct(choiceProduct); //이미 고른게 있다면 업데이트
        return new ProductDto(choiceProduct);
    }

    @Transactional
    public void deleteChoiceProduct(Long managerId, Long shopId, Long productId) {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        if(choiceProductRepository.findByShopIdAndProductId(shopId, productId) != null)
            choiceProductRepository.deleteByShopIdAndProductId(shopId, productId);
        else throw new CValidCheckException("해당 상품은 추천된 상품이 아닙니다.");
    }

    @Transactional(readOnly = true)
    public List<ProductDto> recommendProducts() {
        return productRepositorySupport.recommendProducts();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> productsPerCategory(String category, String method, Pageable pageable) {
        List<ProductDto> data;
        if (StringUtils.hasText(category)) { // 카테고리가 있으면
            data = jdbcTemplateProductRepository.findProductsByShopCategory(category, pageable);
        }
        else { // 전체보기면
            data = jdbcTemplateProductRepository.findProductsByShopCategoryAll(pageable);
        }

        List<ProductDto> sortData;
        log.info("category sort method : " + method);
        if (!StringUtils.hasText(method)) { // method 가 없는 기본 값이면 최신순 (id 높은 순으로)
            sortData =  data.stream().sorted(Comparator.comparing(ProductDto::getId).reversed()).collect(Collectors.toList());
        }
        else if (method.equals("rate")) {
            sortData = data.stream()
                    .sorted((a, b) -> (int) (((b.getPrice() - b.getDiscountedPrice()) / b.getPrice().doubleValue() * 100) -
                            ((a.getPrice() - a.getDiscountedPrice()) / a.getPrice().doubleValue() * 100))).collect(Collectors.toList());
        }
        else if (method.equals("low")) {
            sortData =  data.stream().sorted(Comparator.comparing(ProductDto::getDiscountedPrice)).collect(Collectors.toList());
        }
        else if (method.equals("high")) {
            sortData =  data.stream().sorted(Comparator.comparing(ProductDto::getDiscountedPrice).reversed()).collect(Collectors.toList());
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
}