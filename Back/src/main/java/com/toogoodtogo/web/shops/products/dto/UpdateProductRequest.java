package com.toogoodtogo.web.shops.products.dto;

import com.toogoodtogo.advice.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductRequest {
    @NotBlank(message = "이름은 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private String name;
    @NotNull(message = "가격은 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Positive(message = "가격은 0보다 커야 합니다.", groups = ValidationGroups.PatternCheckGroup.class)
    private Long price;
    @NotNull(message = "할인가격은 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Positive(message = "가격은 0보다 커야 합니다.", groups = ValidationGroups.PatternCheckGroup.class)
    private Long discountedPrice;
    private String image;
}
