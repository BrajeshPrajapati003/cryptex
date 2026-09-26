import { ApiError } from "@/lib/api/errors";
import { serverApiClient } from "@/lib/api/server";
import { AUTH_COOKIE_NAMES } from "@/lib/auth/cookies";
import { cookies } from "next/headers";
import { NextResponse } from "next/server";

export async function POST() {
    const cookieStore = await cookies();

    const refreshToken = cookieStore.get(
        AUTH_COOKIE_NAMES.refreshToken,
    )?.value;

    try{
        if(refreshToken){
            await serverApiClient<void>(
                "/api/v1/auth/logout",
                {
                    method: "POST",
                    body: JSON.stringify({
                        refreshToken,
                    }),
                },
            );
        }
    }catch(error){
        if(error instanceof ApiError){
            console.error("Backend logout failed: ", error.message);
        }else{
            console.error("Logout failed: ", error);
        }
    }

    cookieStore.delete(AUTH_COOKIE_NAMES.accessToken);
    cookieStore.delete(AUTH_COOKIE_NAMES.refreshToken);

    return NextResponse.json({
        success: true,
        message: "Logged out successfully.",
    });
}
