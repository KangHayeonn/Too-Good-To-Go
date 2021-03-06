package com.toogoodtogo.application.shop;

import com.toogoodtogo.application.S3Uploader;
import com.toogoodtogo.application.UploadFileConverter;
import com.toogoodtogo.domain.exceptions.CUploadImageInvalidException;
import com.toogoodtogo.domain.security.exceptions.CAccessDeniedException;
import com.toogoodtogo.domain.shop.exceptions.CShopNotFoundException;
import com.toogoodtogo.domain.shop.product.*;
import com.toogoodtogo.domain.shop.product.exceptions.CProductNotFoundException;
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
    private final HighestRateProductRepository highestRateProductRepository;

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
            throw new CValidCheckException("?????? ?????? ???????????????.");

        // ?????? ??????
        String filePath = uploadFileConverter.parseFileInfo(file, "shopsImage", managerId);

        String fileName;
        if (filePath.equals("default.png")) fileName = s3Uploader.get("shopDefault.png"); // ?????? ?????????
        else fileName = s3Uploader.upload(file, filePath); // ?????? ?????? ?????? ??? ?????? ?????????

        Shop newShop = Shop.builder()
                .user(manager)
                .name(request.getName())
                .image(fileName)
                .category(request.getCategory())
                .phone(request.getPhone())
                .address(request.getAddress())
                .hours(new Hours(request.getOpen(), request.getClose()))
                .build();
        highestRateProductRepository.save(HighestRateProduct.builder().shop(newShop).product(null).build());
        return new ShopDto(shopRepository.save(newShop));
    }

    private boolean checkAccessOfShop(Long managerId, Long shopId) {
        return shopRepository.findById(shopId).orElseThrow(CShopNotFoundException::new).getUser().getId().equals(managerId);
    }

    @Transactional
    public ShopDto updateShop(Long managerId, Long shopId, UpdateShopRequest request) {
        // ???????????? ????????? ?????? shop ??? ?????? ?????? ???????????? ??????
        if(!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        if(shopRepository.findByAddressAndName(request.getAddress(), request.getName()).isPresent() &&
                !shopRepository.findByAddressAndName(request.getAddress(), request.getName()).get().getId().equals(shopId))
            throw new CValidCheckException("?????? ?????? ?????? ???????????????."); // ???????????? ????????? ????????? ???
        Shop modifiedShop = shopRepository.findById(shopId).orElseThrow(CShopNotFoundException::new);
        modifiedShop.update(request.getName(), request.getCategory(),
                request.getPhone(), request.getAddress(), new Hours(request.getOpen(), request.getClose()));
        return new ShopDto(modifiedShop);
    }

    @Transactional
    public String updateShopImage(Long managerId, Long shopId, MultipartFile file) throws IOException {
        // ???????????? ????????? ?????? shop ??? ?????? ?????? ???????????? ??????
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        Shop modifiedImageShop = shopRepository.findById(shopId).orElseThrow(CShopNotFoundException::new);
        String filePath = uploadFileConverter.parseFileInfo(file, "shopsImage", managerId);
        if(filePath.equals("default.png")) throw new CUploadImageInvalidException();
        String fileName = s3Uploader.updateS3(file, modifiedImageShop.getImage(), filePath);
        modifiedImageShop.updateImage(fileName);
        return fileName;
    }

    @Transactional
    public void deleteShopImage(Long managerId, Long shopId) {
        // ???????????? ????????? ?????? shop ??? ?????? ?????? ???????????? ??????
        if (!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        Shop deleteImageShop = shopRepository.findById(shopId).orElseThrow(CShopNotFoundException::new);
        if (!deleteImageShop.getImage().contains("shopDefault.png")) { // ?????? ???????????? ????????? ????????? S3?????? ????????? ??????
            s3Uploader.deleteFileS3(deleteImageShop.getImage());
            deleteImageShop.updateImage(s3Uploader.get("shopDefault.png")); // ?????? ???????????? ??????
        } else throw new CValidCheckException("?????? ??????????????????.");
    }

    @Transactional
    public void deleteShop(Long managerId, Long shopId) {
        // ???????????? ????????? ?????? shop ??? ?????? ?????? ???????????? ??????
        if(!checkAccessOfShop(managerId, shopId)) throw new CAccessDeniedException();
        // ????????? shop
        Shop deleteShop = shopRepository.findById(shopId).orElseThrow(CShopNotFoundException::new);

        // ?????? shop ??? product ??? ????????? ??????
        s3Uploader.deleteFolderS3("productsImage/" + deleteShop.getId() + "/");
        highestRateProductRepository.deleteByShopId(deleteShop.getId());
        choiceProductRepository.deleteByShopId(deleteShop.getId());
        productRepository.deleteByShopId(deleteShop.getId());

        // ?????? ???????????? ????????? S3?????? ????????? ??????
        if (!deleteShop.getImage().contains("shopDefault.png")) s3Uploader.deleteFileS3(deleteShop.getImage());
        displayProductRepository.deleteByShopId(deleteShop.getId());
        shopRepository.deleteById(deleteShop.getId());
    }

    @Transactional(readOnly = true)
    public List<ShopDto> findShopsBySearch(String keyword) {
        // ????????? ???????????? ?????? ??????...?
        return shopRepository.findByNameContaining(keyword).stream()
                .map(ShopDto::new)
                .collect(Collectors.toList());
    }
}
