package com.toogoodtogo.domain.shop;

import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;
import com.toogoodtogo.domain.ListToStringConverter;
import com.toogoodtogo.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shop")
public class Shop {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private String image;

    @QueryType(PropertyType.STRING)
    @Convert(converter = ListToStringConverter.class)
    private List<String> category;

    private String phone;

    private String address;

    @Embedded
    private Hours hours;

    public void update(String name, String image, List<String> category, String phone, String address, Hours hours) {
        this.name = name;
        this.image = image;
        this.category = category;
        this.phone = phone;
        this.address = address;
        this.hours = hours;
    }
}
