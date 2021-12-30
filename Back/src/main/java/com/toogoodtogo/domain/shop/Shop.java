package com.toogoodtogo.domain.shop;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Shop {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String image;

    private String[] category;

    @Builder
    public Shop(String name, String image, String[] category) {
        this.name = name;
        this.image = image;
        this.category = category;
    }
}
