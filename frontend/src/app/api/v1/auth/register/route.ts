import { ApiResponse, RegisterRequest, RegisterResponse } from "@/features/auth/types";
import { ApiError } from "@/lib/api/errors";
import { serverApiClient } from "@/lib/api/server";
import { NextResponse } from "next/server";

export async function POST(request: Request){
    try{
        const body = (await request.json()) as RegisterRequest;

        const result = await serverApiClient<ApiResponse<RegisterResponse>>(
            "/api/v1/auth/register",
            {
                method: "POST",
                body: JSON.stringify(body),
            },
        );

        return NextResponse.json(
            {
                success: result.success,
                message: result.message,
                data: result.data,
            },{
                status: 201,
            },
        );
    }catch(error){

        console.error("========== REGISTRATION ERROR ==========");
        console.error("Registration failed: ", error);

        if(error instanceof ApiError){

            console.error("Status: ", error.status);
            console.error("Message: ", error.message);
            console.error("Data: ", error.data);
        }

        console.error("=========================================");

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
                message: "Unable to register. Please try again.",
            },{
                status: 500,
            },
        );
    }
}
