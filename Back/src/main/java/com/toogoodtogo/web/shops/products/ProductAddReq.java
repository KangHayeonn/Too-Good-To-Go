package com.toogoodtogo.web.shops.products;

import com.toogoodtogo.domain.shop.Shop;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAddReq {
    @NotBlank(message = "이름은 필수 값입니다.")
    private String name;
    @NotNull(message = "가격은 필수 값입니다.")
    @Positive(message = "가격은 0보다 커야 합니다.")
    private Long price;
    @NotNull(message = "할인가격은 필수 값입니다.")
    @Positive(message = "가격은 0보다 커야 합니다.")
    private Long discountedPrice;
    @NotBlank(message = "이미지는 필수 값입니다.")
    private String image;
}