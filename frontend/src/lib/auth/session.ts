import { cookies } from "next/headers";
import { AUTH_COOKIE_NAMES } from "./cookies";
import { UserProfileResponse } from "@/features/user/types";
import { serverApiClient } from "../api/server";

export async function getCurrentUser(): Promise<UserProfileResponse | null>{
    const cookieStore = await cookies();

    const accessToken = cookieStore.get(
        AUTH_COOKIE_NAMES.accessToken,
    )?.value;

    if(!accessToken){
        return null;
    }

    try{
        return await serverApiClient<UserProfileResponse>(
            "/api/v1/users/me",
            {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
            },
        );
        
    }catch {
        return null;
    }
}
