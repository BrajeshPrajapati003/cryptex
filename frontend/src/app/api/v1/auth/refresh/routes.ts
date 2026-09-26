import { RefreshTokenRequest, RefreshTokenResponse } from "@/features/auth/types";
import { ApiError } from "@/lib/api/errors";
import { serverApiClient } from "@/lib/api/server";
import { ACCESS_TOKEN_COOKIE_OPTIONS, AUTH_COOKIE_NAMES, REFRESH_TOKEN_COOKIE_OPTIONS } from "@/lib/auth/cookies";
import { cookies } from "next/headers";
import { NextResponse } from "next/server";

export async function POST(){

    try{

        const cookieStore = await cookies();

        const refreshToken = cookieStore.get(
            AUTH_COOKIE_NAMES.refreshToken,
        )?.value;

        if(!refreshToken){

            return NextResponse.json(
                {
                    success: false,
                    message: "No refresh token found.",
                },
                {
                    status: 401,
                },
            );
        }

        const result = await serverApiClient<RefreshTokenResponse>(
            "/api/v1/auth/refresh",
            {
                method: "POST",
                body: JSON.stringify({
                    refreshToken,
                } satisfies RefreshTokenRequest),
                /**
                 * "satisfies" tells TS:
                 * Verify that this object conforms to RefreshTokenRequest,
                 * but don't unnecessarily change the inferred type of the object.
                 */
            },
        );


        /**
         * Refresh-token rotation.
         * Replace BOTH tokens.
         */

        cookieStore.set(
            AUTH_COOKIE_NAMES.accessToken,
            result.accessToken,
            ACCESS_TOKEN_COOKIE_OPTIONS,
        );

        cookieStore.set(
            AUTH_COOKIE_NAMES.refreshToken,
            result.refreshToken,
            REFRESH_TOKEN_COOKIE_OPTIONS,
        );

        return NextResponse.json({
            success: true,
        });

    }catch(error){

        console.error("Token refresh failed: ", error);

        if(error instanceof ApiError){

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
                message: "Unable to refresh session.",
            },
            {
                status: 500,
            },
        );
    }
}
