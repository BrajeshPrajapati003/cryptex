import { ApiError } from "@/lib/api/errors";
import { serverApiClient } from "@/lib/api/server";
import { NextResponse } from "next/server";

export async function POST(request: Request){
    try{
        const body = await request.text();

        const response = await serverApiClient(
            "/api/v1/auth/reset-password",
            {
                method: "POST",
                body,
            },
        );

        return NextResponse.json(response);
    }catch(error){
        console.error("Reset password request failed: ", error);
        
        if(error instanceof ApiError){
            return NextResponse.json(
                {
                    success: false,
                    message: error.message,
                },
                {
                    status: error.status
                },
            );
        }

        return NextResponse.json(
            {
                success: false,
                message: "Unable to reset password.",
            },{
                status: 500,
            },
        );
    }
}
