package com.toogoodtogo.domain.shop.product;

import com.toogoodtogo.domain.ListToStringConverter;
import com.toogoodtogo.domain.shop.Shop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class DisplayProduct {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(nullable = false)
    @Convert(converter = ListToStringConverter.class)
    private List<String> priority = new ArrayList<String>();

    public void addPrior(Long productId) {
        this.priority.add(String.valueOf(productId));
    }

    public void update(List<String> priority) {
        this.priority = priority;
    }
}
