export interface UserProfileResponse{
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    role: Role;
}

export interface UpdateUserRequest{
    firstName: string;
    lastName: string;
}

export enum Role{
    USER = "USER",
    ADMIN = "ADMIN",
}
