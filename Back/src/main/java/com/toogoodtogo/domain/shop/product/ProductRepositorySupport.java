package com.toogoodtogo.domain.shop.product;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toogoodtogo.web.shops.products.dto.ProductDto;
import com.toogoodtogo.web.shops.products.dto.ProductTmp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.toogoodtogo.domain.shop.QShop.shop;
import static com.toogoodtogo.domain.shop.product.QProduct.*;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductRepositorySupport {
    private final JPAQueryFactory queryFactory;

    // 상점 아이디, 이름 포함
    public List<List<ProductDto>> productsPerCategory(String category) { //method String 대신 enum?
        List<Tuple> results = queryFactory.select(shop.id, shop.category).from(shop).fetch();
        List<Long> shopList = new ArrayList<>();
        results.forEach(tuple -> {
            if (tuple.get(shop.category).contains(category)) {
                shopList.add(tuple.get(shop.id));
            }
        });
//        for (Tuple tuple : results) {
//            Long id = tuple.get(shop.id);
//            List<String> categories = tuple.get(shop.category);
//            log.info("SHOP id : {}, category : {}", id, categories);
//            if (categories.contains(category)) {
//                shopList.add(tuple.get(shop.id));
//            }
//        }
        List<List<ProductDto>> data = new ArrayList<>();
        shopList.forEach(id -> {
            data.add(queryFactory.select(Projections.fields(ProductDto.class,
                    shop.id.as("shopId"),
                    shop.name.as("shopName"),
                    product.id,
                    product.name,
                    product.price,
                    product.discountedPrice,
                    product.image))
                    .from(product) // 맞나?
                    .innerJoin(product.shop, shop)
                    .where(eqShopId(id))
                    .fetch());
        });
        return data;
    }

    public List<ProductDto> productsPerCategory2(String category, String method) { //method String 대신 enum?
//        List<Product> fetch = queryFactory.select(product)
//                .from(product)
//                .innerJoin(product.shop, shop)
//                .where(product.priority.eq(0L))
//                .orderBy(orderType(method))
//                .fetch();
//        List<ProductDto> productList = new ArrayList<>();
//        fetch.forEach(p -> {
//            if (p.getShop().getCategory().contains(category)) {
//                productList.add(new ProductDto(p));
//            }
//        });
//        return productList;
        return queryFactory.select(Projections.fields(ProductDto.class,
                shop.id.as("shopId"),
                shop.name.as("shopName"),
                product.id,
                product.name,
                product.price,
                product.discountedPrice,
                product.image))
                .from(product) // 맞나?
                .innerJoin(product.shop, shop)
                .fetch();
    }

    public List<Product> findProductsPerShopSortByPriority(Long shopId) { //method String 대신 enum?
        return queryFactory.selectFrom(product)
                .innerJoin(product.shop, shop)
                .where(eqShopId(shopId))
                .orderBy(orderType("/priority"))
                .fetch();
    }

    public List<ProductDto> sortProductsPerShop(Long shopId, String method) { //method String 대신 enum?
        return queryFactory.select(Projections.fields(ProductDto.class,
                shop.id.as("shopId"),
                shop.name.as("shopName"),
                product.id,
                product.name,
                product.price,
                product.discountedPrice,
                product.image))
                .from(product) // 맞나?
                .innerJoin(product.shop, shop)
                .where(eqShopId(shopId))
                .orderBy(orderType(method))
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
        else if (method.equals("/priority")) {
            log.info("priority sort");
            return product.priority.asc();
        }
        else return product.id.desc(); // 디폴트값 최신순. BaseTimeEntity 상속 or Id 순 "update"?
    }

    public List<ProductTmp> recommendProducts() { // 가게별 할인율 가장 낮은 1개 구하고 그 중 10개
        //queryDSL에선 form 서브쿼리 지원안함....
        QProduct p1 = new QProduct("p1");
        QProduct p2 = new QProduct("p2");

        List<Long> productIdList = queryFactory.select(p1.id)
                .from(p1)
                .innerJoin(p1.shop, shop)
                .leftJoin(p2)
                .on(p1.shop.id.eq(p2.shop.id)
                        .and(p1.price.subtract(p1.discountedPrice).mod(p1.price).multiply(100L)
                                .lt(p2.price.subtract(p2.discountedPrice).mod(p2.price).multiply(100L))))
                .where(p2.id.isNull()) // ??
                .orderBy(p1.price.subtract(p1.discountedPrice).mod(p1.price).multiply(100L).desc()) //그 중에서도 할인율순
                .limit(1)
                .fetch();//??;

        return queryFactory.select(Projections.fields(ProductTmp.class, // or .constructor
                shop.id.as("shopId"),
                shop.name.as("shopName"),
                p1.id,
                p1.name,
                p1.price,
                p1.discountedPrice,
                p1.price.subtract(p1.discountedPrice).divide(p1.price).multiply(100L).as("rate"),
                p1.image))
                .from(p1)
//                .innerJoin(p1.shop, shop)
//                .fetchJoin()
//                .innerJoin(p2.shop, shop)
                .leftJoin(p2)
                .fetchJoin()
                .on(p1.shop.id.eq(p2.shop.id)
                        .and(p1.price.subtract(p1.discountedPrice).mod(p1.price).multiply(100L)
                                .lt(p2.price.subtract(p2.discountedPrice).mod(p2.price).multiply(100L))))
                .where(p2.id.isNull()) // ??
                .orderBy(p1.price.subtract(p1.discountedPrice).mod(p1.price).multiply(100L).desc()) //그 중에서도 할인율순
                .limit(5)
                .fetch();//??;
    }

    public BooleanExpression eqProductName(String productName) {
        return hasText(productName) ? product.name.eq(productName) : null;
    }

    public BooleanExpression eqShopName(String shopName) {
        return hasText(shopName) ? shop.name.eq(shopName) : null;
    }

    public BooleanExpression eqShopId(Long shopId) {
        return shopId != null ? shop.id.eq(shopId) : null;
    }

    public BooleanExpression eqCategory(String category) {
//        log.info(String.valueOf(shop.category.getType()));
//        log.info(String.valueOf(shop.category.getClass()));
//        return hasText(category) ? shop.category.any().stringValue().contains(category) : null;
        return hasText(category) ? Expressions.stringPath(String.valueOf(shop.category)).contains(category) : null;
    }
}
