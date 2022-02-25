package com.toogoodtogo.web.shops.products.dto;

import com.toogoodtogo.advice.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddChoiceProductRequest {
    @NotNull(message = "상품 번호는 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private Long productId;
}
