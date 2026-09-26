import { apiClient } from "@/lib/api/client";

import type {
    UpdateUserRequest,
    UserProfileResponse,
} from "./types";

export async function getCurrentUser(): Promise<UserProfileResponse> {
    return apiClient<UserProfileResponse>(
        "/api/v1/users/me",
        {
            method: "GET",
        },
    );
}

export async function updateCurrentUser(
    data: UpdateUserRequest,
): Promise<UserProfileResponse> {
    return apiClient<UserProfileResponse>(
        "/api/v1/users/me",
        {
            method: "PATCH",
            body: JSON.stringify(data),
        },
    );
}

