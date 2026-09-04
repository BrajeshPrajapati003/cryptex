import apiClient from '../api-client';
import { UpdateUserRequest, UserProfile } from '@/types/user';

export const getCurrentUser = async (): Promise<UserProfile> => {
    const response = await apiClient.get<UserProfile>(
        "/api/v1/users/me"
    );

    return response.data;
};


export const updateCurrentUser = async (
    request: UpdateUserRequest
): Promise<UserProfile> => {
    const response = await apiClient.patch<UserProfile>(
        "/api/v1/users/me",
        request
    );

    return response.data;
};
