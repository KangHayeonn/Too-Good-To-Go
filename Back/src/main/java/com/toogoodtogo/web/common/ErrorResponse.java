package com.toogoodtogo.web.common;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public final class ErrorResponse {
    private final String reason;
    private final String message;
}
