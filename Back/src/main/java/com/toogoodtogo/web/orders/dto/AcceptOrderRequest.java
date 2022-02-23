package com.toogoodtogo.web.orders.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AcceptOrderRequest {
    private LocalDateTime eta;
}
