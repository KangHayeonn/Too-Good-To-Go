package com.toogoodtogo.domain.shop;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Shop {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
