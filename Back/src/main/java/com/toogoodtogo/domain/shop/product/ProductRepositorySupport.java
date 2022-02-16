package com.toogoodtogo.domain.shop.product;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toogoodtogo.web.shops.products.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.toogoodtogo.domain.shop.QShop.shop;
import static com.toogoodtogo.domain.shop.product.QChoiceProduct.choiceProduct;
import static com.toogoodtogo.domain.shop.product.QProduct.*;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public Product choiceHighestRateProductPerShop(Long shopId) {
        return queryFactory.selectFrom(product)
                .where(product.shop.id.eq(shopId))
                .orderBy(orderType("/rate"))
                .limit(1)
                .fetchOne();
    }

    public List<ProductDto> recommendProducts() { // 가게별 할인율 가장 낮은 1개 구하고 그 중 10개
        return queryFactory.select(Projections.fields(ProductDto.class,
                shop.id.as("shopId"),
                shop.name.as("shopName"),
                product.id,
                product.name,
                product.price,
                product.discountedPrice,
                product.image))
                .from(choiceProduct)
                .innerJoin(choiceProduct.product, product)
                .innerJoin(choiceProduct.shop, shop)
                .orderBy(orderType("/rate"))
                .limit(10)
                .fetch();
    }

    private OrderSpecifier<?> orderType(String method) {
        log.info("orderTest : " + method);
        if(method.equals("/rate")){
            //할인율 = (p-dp)/p*100
            return product.price.subtract(product.discountedPrice).divide(product.price).multiply(100L).desc();
        }
        else if (method.equals("/discount")) {
            return product.discountedPrice.asc();
        }
        else return product.id.desc(); // 디폴트값 최신순. BaseTimeEntity 상속 or Id 순 "update"?
    }
}
