export type Role = "USER" | "ADMIN";

export interface UserProfile {
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    role: Role;
}

export interface UpdateUserRequest {
    firstName: string;
    lastName: string;
}
