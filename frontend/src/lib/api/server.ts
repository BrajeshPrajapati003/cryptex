/*
This module must never be imported on the client side
because this file uses: process.env.API_BASE_URL, which is only available on the server side.
*/
import "server-only";

const API_BASE_URL = process.env.API_BASE_URL;

if(!API_BASE_URL) {
    throw new Error("API_BASE_URL is not configured.");
}

export async function serverApiClient<T>(
    endpoint: string,
    options: RequestInit = {},
): Promise<T> {

    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            ...options.headers,
        },
        cache: "no-store", // Disable caching for server-side requests
        },
    )

    if (!response.ok) {
        throw new Error(
            `Backend request failed ${response.status}`,
        );
    }

    if (response.status === 204) {
        return undefined as T;
    }

    return response.json() as Promise<T>;
}

