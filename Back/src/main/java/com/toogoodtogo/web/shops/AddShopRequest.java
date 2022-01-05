package com.toogoodtogo.web.shops;

import com.toogoodtogo.domain.shop.Hours;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddShopRequest {
    private User user;
    private String name;
    private String image;
    private String[] category;
    private String phone;
    private String address;
    private String open;
    private String close;

//    public Shop toEntity() {
//        return Shop.builder()
//                .user(user)
//                .name(name)
//                .image(image)
//                .category(category)
//                .phone(phone)
//                .address(address)
//                .hours(new Hours(open, close))
//                .build();
//    }
}
