package com.toogoodtogo.application.shop;

import com.toogoodtogo.application.S3Uploader;
import com.toogoodtogo.application.UploadFileConverter;
import com.toogoodtogo.domain.security.exceptions.CAccessDeniedException;
import com.toogoodtogo.domain.shop.exceptions.CShopNotFoundException;
import com.toogoodtogo.domain.shop.product.*;
import com.toogoodtogo.domain.user.exceptions.CUserNotFoundException;
import com.toogoodtogo.advice.exception.CValidCheckException;
import com.toogoodtogo.domain.shop.Hours;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.web.shops.dto.AddShopRequest;
import com.toogoodtogo.web.shops.dto.ShopDto;
import com.toogoodtogo.web.shops.dto.UpdateShopRequest;
import com.toogoodtogo.web.shops.products.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService implements ShopUseCase {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final UploadFileConverter uploadFileConverter;
    private final S3Uploader s3Uploader;
    private final ChoiceProductRepository choiceProductRepository;
    private final DisplayProductRepository displayProductRepository;

    @Transactional(readOnly = true)
    public List<ShopDto> findAllShops() {
        return shopRepository.findAll()
                .stream()
                .map(ShopDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ShopDto> findShops(Long managerId) {
        User manager = userRepository.findById(managerId).orElseThrow(CUserNotFoundException::new);
        return shopRepository.findByUser(manager)
                .stream().map(ShopDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ShopDto findShop(Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(CShopNotFoundException::new);
        return new ShopDto(shop);
    }

    @Override
    @Transactional
    public ShopDto addShop(Long managerId, MultipartFile file, AddShopRequest request) throws IOException {
        User manager = userRepository.findById(managerId).orElseThrow(CUserNotFoundException::new);
        if(shopRepository.findByAddressAndName(request.getAddress(), request.getName()).isPresent())
            throw new CValidCheckException("이미 있는 가게입니다.");

        // 파일 경로
        String filePath = uploadFileConverter.parseFileInfo(file, "shopsImage", managerId);

        String fileName;
        if (filePath.equals("default.png")) fileName = "default.png"; // 기본 이미지
        else fileName = s3Uploader.upload(file, filePath); // 최종 파일 경로 및 파일 업로드

        Shop newShop = Shop.builder()
                .user(manager)
                .name(request.getName())
                .image(fileName)
                .category(request.getCategory())
                .phone(request.getPhone())
                .address(request.getAddress())
                .hours(new Hours(request.getOpen(), request.getClose()))
                .build();

        return new ShopDto(shopRepository.save(newShop));
    }

    private boolean checkAccessOfShop(Long managerId, Long shopId) {
        return shopRepository.findById(shopId).orElseThrow(CShopNotFoundException::new).getUser().getId().equals(managerId);
    }

    @Override
    @Transactional
    public ShopDto updateShop(Long managerId, Long shopId, MultipartFile file, UpdateShopRequest request) throws IOException {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if(!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        // 수정할 shop
        Shop modifiedShop = shopRepository.findById(shopId).orElseThrow(CShopNotFoundException::new);

        String filePath;
        String fileName;
        if (file.isEmpty()) { // 넘어온 사진이 없으면
            fileName = modifiedShop.getImage(); // 기존 이미지
        }
        else { // 넘어온 사진이 있으면
            filePath = uploadFileConverter.parseFileInfo(file, "productsImage", modifiedShop.getId());
            fileName = s3Uploader.updateS3(file, modifiedShop.getImage(), filePath);
        }
        
        // update 리팩토링 필요
        modifiedShop.update(request.getName(), fileName, request.getCategory(),
                request.getPhone(), request.getAddress(), new Hours(request.getOpen(), request.getClose()));
        return new ShopDto(modifiedShop);
    }

    @Override
    @Transactional
    public void deleteShop(Long managerId, Long shopId) {
        // 로그인한 유저가 해당 shop 에 대해 권한 가졌는지 체크
        if(!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        // 삭제할 shop
        Shop deleteShop = shopRepository.findById(shopId).orElseThrow(CShopNotFoundException::new);

        // 해당 shop 의 product 들 이미지 삭제
        s3Uploader.deleteFolderS3("productsImage/" + deleteShop.getId() + "/");
        productRepository.deleteByShopId(deleteShop.getId());

        // 기본 이미지가 아니면 S3에서 이미지 삭제
        if (!deleteShop.getImage().equals("default.png")) s3Uploader.deleteFileS3(deleteShop.getImage());

        displayProductRepository.deleteByShopId(deleteShop.getId());
        choiceProductRepository.deleteByShopId(deleteShop.getId());
        shopRepository.deleteById(deleteShop.getId());
    }

    @Transactional(readOnly = true)
    public List<ShopDto> findShopsBySearch(String keyword) {
        // 검색량 저장하는 기능 추가...?
        return shopRepository.findByNameContaining(keyword).stream()
                .map(ShopDto::new)
                .collect(Collectors.toList());
    }
}
