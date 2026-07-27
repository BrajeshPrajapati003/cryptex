package com.cryptex.common.response;

public record ApiResponse<T>(

        boolean success,
        String message,
        T data
) {
}
