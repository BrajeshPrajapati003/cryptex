import { ApiResponse } from "@/features/auth/types";
import { ApiError } from "@/lib/api/errors";
import { serverApiClient } from "@/lib/api/server";
import { NextResponse } from "next/server";

export async function GET(request: Request) {
  try {
    const { searchParams } = new URL(request.url);

    const token = searchParams.get("token");

    if (!token) {
      return NextResponse.json(
        {
          success: false,
          message: "Verification token is required.",
        },
        {
          status: 400,
        },
      );
    }

    const result = await serverApiClient<ApiResponse<null>>(
      `/api/v1/auth/verify?token=${encodeURIComponent(token)}`,
      {
        method: "GET",
      },
    );

    return NextResponse.json(
        {
            success: result.success,
            message: result.message,
            data: result.data,
        },{
            status: 200,
        },
    );

  } catch (error) {
    console.error("Email verification failed: ", error);

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
            message: "Unable to verify email.",
        },{
            status: 500,
        },
    );
  }
}
