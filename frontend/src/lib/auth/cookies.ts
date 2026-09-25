// import type { ResponseCookie } from "next/dist/compiled/@edge-runtime/cookies";

export const AUTH_COOKIE_NAMES = {
    accessToken: 'cryptex_access_token',
    refreshToken: 'cryptex_refresh_token',
} as const; // as const = this is a fixed literal value.


export const ACCESS_TOKEN_COOKIE_OPTIONS = {
    httpOnly: true,
    secure: process.env.NODE_ENV === "production",
    sameSite: "lax" as const,
    path: "/",
    // maxAge: 60 * 60 * 24 * 7, // 7 days
};

export const REFRESH_TOKEN_COOKIE_OPTIONS = {
    httpOnly: true,
    secure: process.env.NODE_ENV === "production",
    sameSite: "lax" as const,
    path: "/",
    // maxAge: 60 * 60 * 24 * 30, // 30 days
};

