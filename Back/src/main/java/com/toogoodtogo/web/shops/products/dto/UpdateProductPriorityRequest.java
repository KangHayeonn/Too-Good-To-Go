package com.toogoodtogo.web.shops.products.dto;

import com.toogoodtogo.advice.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductPriorityRequest {
    @NotNull(message = "우선순위는 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @PositiveOrZero(message = "우선순위는 0 이상이어야 합니다.", groups = ValidationGroups.PatternCheckGroup.class)
    private Long priority;
}
