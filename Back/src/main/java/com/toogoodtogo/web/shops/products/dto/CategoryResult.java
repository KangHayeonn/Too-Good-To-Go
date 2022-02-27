package com.toogoodtogo.web.shops.products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResult {
    private List<ProductDto> products;
    private Long totalNum;
}
