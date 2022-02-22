package com.toogoodtogo.domain.shop.product;

import com.toogoodtogo.web.shops.products.dto.ProductDto;
import com.toogoodtogo.web.shops.products.dto.ProductSearchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Slf4j
public class JdbcTemplateProductRepository {
    private final JdbcTemplate jdbcTemplate;

    // 생성자가 단 한 개인 경우 Autowired 생략 가능
    public JdbcTemplateProductRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<ProductSearchDto> searchProductsByShopCategoryOrShopName(String keyword) {
        String sql = "select s.id as shop_id, s.name as shop_name, s.category as shop_category," +
                "p.id as product_id, p.name as product_name, p.price, p.discounted_price, p.image\n" +
                "from product p\n" +
                "inner join shop s on p.shop_id = s.id\n" +
                "where p.id in\n" +
                "(\n" +
                    "select if(not cp.product_id is null, cp.product_id, hrp.product_id) as result\n" +
                    "from choice_product cp \n" +
                    "right join highest_rate_product hrp on cp.shop_id = hrp.shop_id\n" +
                    "where hrp.shop_id in\n" +
                        "( select s.id\n" +
                        "  from shop s\n" +
                        "  where find_in_set(?, s.category) > 0 or s.name like ?\n" +
                        " )\n" +
                    "and not hrp.product_id is null\n" +
                " )";

        return jdbcTemplate.query(sql, productSearchDtoRowMapper(), keyword, "%" + keyword + "%");
    }

    private RowMapper<ProductSearchDto> productSearchDtoRowMapper() {
        return (rs, rowNum) -> ProductSearchDto.builder()
                .shopId(rs.getLong("shop_id"))
                .shopName(rs.getString("shop_name"))
                .shopCategory(rs.getString("shop_category"))
                .id(rs.getLong("product_id"))
                .name(rs.getString("product_name"))
                .price(rs.getLong("price"))
                .discountedPrice(rs.getLong("discounted_price"))
                .image(rs.getString("image")).build();
    }

    public List<ProductDto> findProductsByShopCategory(String category, Pageable pageable) {
        String sql = "select s.id as shop_id, s.name as shop_name, p.id as product_id, p.name as product_name, p.price, p.discounted_price, p.image\n" +
                "from product p\n" +
                "inner join shop s on p.shop_id = s.id\n" +
                "where p.id in\n" +
                "(\n" +
                    "select if(not cp.product_id is null, cp.product_id, hrp.product_id) as result\n" +
                    "from choice_product cp \n" +
                    "right join highest_rate_product hrp on cp.shop_id = hrp.shop_id\n" +
                    "where hrp.shop_id in\n" +
                        "( select s.id\n" +
                        "  from shop s\n" +
                        "  where find_in_set(?, s.category) > 0\n" +
                        " )\n" +
                    "and not hrp.product_id is null\n" +
                ")\n" +
                "limit ? offset ?";

        return jdbcTemplate.query(sql, productDtoRowMapper(), category, pageable.getPageSize(), pageable.getOffset());
    }

    public List<ProductDto> findProductsByShopCategoryAll(Pageable pageable) {
        String sql = "select s.id as shop_id, s.name as shop_name, p.id as product_id, p.name as product_name, p.price, p.discounted_price, p.image\n" +
                "from product p\n" +
                "inner join shop s on p.shop_id = s.id\n" +
                "where p.id in\n" +
                "(\n" +
                    "select if(not cp.product_id is null, cp.product_id, hrp.product_id) as result\n" +
                    "from choice_product cp \n" +
                    "right join highest_rate_product hrp on cp.shop_id = hrp.shop_id\n" +
                    "where not hrp.shop_id is null\n" +
                ")\n" +
                "limit ? offset ?";

        return jdbcTemplate.query(sql, productDtoRowMapper(), pageable.getPageSize(), pageable.getOffset());
    }

    private RowMapper<ProductDto> productDtoRowMapper() {
        return (rs, rowNum) -> ProductDto.builder()
                .shopId(rs.getLong("shop_id"))
                .shopName(rs.getString("shop_name"))
                .id(rs.getLong("product_id"))
                .name(rs.getString("product_name"))
                .price(rs.getLong("price"))
                .discountedPrice(rs.getLong("discounted_price"))
                .image(rs.getString("image")).build();
    }
}
