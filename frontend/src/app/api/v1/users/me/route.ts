import { UserProfileResponse } from "@/features/user/types";
import { ApiError } from "@/lib/api/errors";
import { serverApiClient } from "@/lib/api/server";
import { AUTH_COOKIE_NAMES } from "@/lib/auth/cookies";
import { cookies } from "next/headers";
import { NextResponse } from "next/server";

export async function GET(){
    try{
        
        const cookieStore = await cookies();

        const accessToken = cookieStore.get(
            AUTH_COOKIE_NAMES.accessToken,
        )?.value;

        if(!accessToken){

            return NextResponse.json(
                {
                    success: false,
                    message: "Authentication required.",
                },{
                    status: 401,
                },
            );
        }

        const user = 
        await serverApiClient<UserProfileResponse>(
            "/api/v1/users/me",
            {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
            },
        );

        return NextResponse.json(user);

    }catch(error){
        console.error("Failed to fetch current user:", error);

        if(error instanceof ApiError){

            return NextResponse.json(
                {
                    success: false,
                    message: error.message,
                },{
                    status: error.status,
                },
            );
        }

        return NextResponse.json(
            {
                success: false,
                message: "Unable to fetch current user.",
            },{
                status: 500,
            },
        );
    }
}


export async function PATCH(request: Request){
    try{
        const cookieStore = await cookies();

        const accessToken = cookieStore.get(
            AUTH_COOKIE_NAMES.accessToken,
        )?.value;

        if(!accessToken){
            return NextResponse.json(
                {
                    success: false,
                    message: "Authentication required.",
                },{
                    status: 401
                },
            );
        }

        const body = await request.text();

        const user = await serverApiClient<UserProfileResponse>(
            "/api/v1/users/me",
            {
                method: "PATCH",
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
                body,
            },
        );

        return NextResponse.json(user);
    }catch(error){
        console.error("Failed to update current user: ", error);

        if(error instanceof ApiError){
            return NextResponse.json(
                {
                    success: false,
                    message: error.message,
                },
                { status: error.status },
            );
        }

        return NextResponse.json(
            {
                success: false,
                message: "Unable to update current user.",
            },
            {status: 500},
        );
    }

}

