package com.toogoodtogo.web.shops;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopUpdateReq {
    @NotBlank(message = "이름은 필수 값입니다.")
    private String name;
    @NotBlank(message = "이미지는 필수 값입니다.")
    private String image;
    @NotEmpty(message = "카테고리는 필수 값입니다.")
    private String[] category;
    @NotBlank(message = "전화번호는 필수 값입니다.")
    @Pattern(regexp = "01([0|1|6|7|8|9]?)-?([0-9]{4})-?([0-9]{4})", message = "전화번호 양식이 틀렸습니다.")
    private String phone;
    @NotBlank(message = "주소는 필수 값입니다.")
    private String address;
    @NotBlank(message = "오픈 시간은 필수 값입니다.")
    @Pattern(regexp = "([01][0-9]|2[0-3]):([0-5][0-9])", message = "오픈 시간 양식이 틀렸습니다.")
    private String open;
    @NotBlank(message = "마감 시간은 필수 값입니다.")
    @Pattern(regexp = "([01][0-9]|2[0-3]):([0-5][0-9])", message = "마감 시간 양식이 틀렸습니다.")
    private String close;
}
