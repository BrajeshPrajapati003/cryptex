import { NextResponse } from "next/server";
import { cookies } from "next/headers";

import { serverApiClient } from "@/lib/api/server";

import type {
    ApiResponse,
    LoginRequest,
    LoginResponse,
} from "@/features/auth/types";

import { 
    ACCESS_TOKEN_COOKIE_OPTIONS, 
    AUTH_COOKIE_NAMES, 
    REFRESH_TOKEN_COOKIE_OPTIONS 
} from "@/lib/auth/cookies";
import { ApiError } from "@/lib/api/errors";
import { serializeUseCacheCacheStore } from "next/dist/server/resume-data-cache/cache-store";


export async function POST(request: Request){
    try{
        const body = (await request.json()) as LoginRequest;

        const result = 
            await serverApiClient<ApiResponse<LoginResponse>>(
                "/api/v1/auth/login",
                {
                    method: "POST",
                    body: JSON.stringify(body),
                },
            );

        const cookieStore = await cookies();

        /*
        store as HttpOnly cookies
        */

        cookieStore.set(
            AUTH_COOKIE_NAMES.accessToken,
            result.data.accessToken,
            ACCESS_TOKEN_COOKIE_OPTIONS,
        );

        cookieStore.set(
            AUTH_COOKIE_NAMES.refreshToken,
            result.data.refreshToken,
            REFRESH_TOKEN_COOKIE_OPTIONS,
        );

        return NextResponse.json({
            success: result.success,
            message: result.message,
        });

    } catch (error){
        console.error("Login failed: ", error);

        if (error instanceof ApiError) {
            
            return NextResponse.json(
                {
                    success: false,
                    message: error.message,
                },
                {
                    status: error.status,
                },
            );
        }

        return NextResponse.json(
            {
                success: false,
                message: "Unable to login. Please try again.",
            },
            {
                status: 500,
            },
        );
    }
}

/**@
 * We don't return accessToken, refreshToken to the browser JS.
 * that means, 
 * Login React component doesn't need: 
 * localStorage, setAccessToken(...), and document.cookie
 */
