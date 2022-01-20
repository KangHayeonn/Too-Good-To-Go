package com.toogoodtogo.web.shops.products.dto;

import com.toogoodtogo.advice.ValidationGroups;
import com.toogoodtogo.advice.exception.CAuthenticationEntryPointException;
import com.toogoodtogo.advice.exception.CValidCheckException;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.product.Product;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProductRequest {
    @NotBlank(message = "이름은 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private String name;
    @NotNull(message = "가격은 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Positive(message = "가격은 0보다 커야 합니다.", groups = ValidationGroups.PatternCheckGroup.class)
    private Long price;
    @NotNull(message = "할인가격은 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Positive(message = "가격은 0보다 커야 합니다.", groups = ValidationGroups.PatternCheckGroup.class)
    private Long discountedPrice;
    private String image;

    public Product toEntity(Shop shop) {
        return Product.builder()
                .shop(shop)
                .name(name)
                .price(price)
                .discountedPrice(discountedPrice)
                .image(image)
                .build();
    }
}