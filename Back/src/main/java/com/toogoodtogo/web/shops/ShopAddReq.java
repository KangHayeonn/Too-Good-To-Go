package com.toogoodtogo.web.shops;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopAddReq {
    private String name;
    private String image;
    private String[] category;
    private String phone;
    private String address;
    private String open;
    private String close;
}
