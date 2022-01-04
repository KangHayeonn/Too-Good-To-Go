package com.toogoodtogo.domain.shop;

import com.toogoodtogo.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private String image;

    private String[] category;

    private String phone;

    private String address;

    @Embedded
    private Hours hours;

    public void update(String name, String image, String[] category, String phone, String address, Hours hours) {
        this.name = name;
        this.image = image;
        this.category = category;
        this.phone = phone;
        this.address = address;
        this.hours = hours;
    }
}
