import { apiClient } from "@/lib/api/client";

import type {
  ApiResponse,
  ForgotPasswordRequest,
  LoginRequest,
  LoginResponse,
  RefreshTokenRequest,
  RefreshTokenResponse,
  RegisterRequest,
  RegisterResponse,
  ResetPasswordRequest,
} from "./types";


export async function login(
  data: LoginRequest,
): Promise<ApiResponse<LoginResponse>> {
  return apiClient<ApiResponse<LoginResponse>>(
    "/api/v1/auth/login",
    {
      method: "POST",
      body: JSON.stringify(data),
    },
  );
}


export async function register(
  data: RegisterRequest,
): Promise<ApiResponse<RegisterResponse>> {
  return apiClient<ApiResponse<RegisterResponse>>(
    "/api/v1/auth/register",
    {
      method: "POST",
      body: JSON.stringify(data),
    },
  );
}


export async function refreshToken(
  data: RefreshTokenRequest,
): Promise<RefreshTokenResponse> {
  return apiClient<RefreshTokenResponse>(
    "/api/v1/auth/refresh",
    {
      method: "POST",
      body: JSON.stringify(data),
    },
  );
}


export async function logout(
  data: RefreshTokenRequest,
): Promise<void> {
  await apiClient<void>(
    "/api/v1/auth/logout",
    {
      method: "POST",
      body: JSON.stringify(data),
    },
  );
}


export async function verifyEmail(
  token: string,
): Promise<ApiResponse<null>> {
  return apiClient<ApiResponse<null>>(
    `/api/v1/auth/verify?token=${encodeURIComponent(token)}`,
    {
      method: "GET",
    },
  );
}


export async function forgotPassword(
  data: ForgotPasswordRequest,
): Promise<ApiResponse<null>> {
  return apiClient<ApiResponse<null>>(
    "/api/v1/auth/forgot-password",
    {
      method: "POST",
      body: JSON.stringify(data),
    },
  );
}


export async function resetPassword(
  data: ResetPasswordRequest,
): Promise<ApiResponse<null>> {
  return apiClient<ApiResponse<null>>(
    "/api/v1/auth/reset-password",
    {
      method: "POST",
      body: JSON.stringify(data),
    },
  );
}
