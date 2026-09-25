/**
 * REQUEST DTOs
 */

export interface LoginRequest{
    email: string;
    password: string;
}

export interface RegisterRequest{
    firstName: string;
    lastName: string;
    email: string;
    password: string;
}

export interface RefreshTokenRequest{
    refreshToken: string;
}

export interface ForgotPasswordRequest{
    email: string;
}

export interface ResetPasswordRequest{
    token: string;
    newPassword: string;
}

export interface SendNotificationRequest{
    recipient: string;
    type: string;
    variables?: Record<string, unknown>; // Map<String, Object> = Record<string, unknown> 
}


/**
 * RESPONSE DTOs
 */

export interface LoginResponse{
    accessToken: string;
    refreshToken: string;
    tokenType: string;
}

export interface RefreshTokenResponse{
    accessToken: string;
    refreshToken: string;
    tokenType: string;
}

export interface RegisterResponse{
    id: string; // There is no UUID in js/ts, UUID = a string
    firstName: string;
    lastName: string;
    email: string;
}

/**
 * GENERIC API RESPONSE
 */

export interface ApiResponse<T>{
    success: boolean;
    message: string;
    data: T;
}

