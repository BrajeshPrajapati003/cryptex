/*
This module must never be imported on the client side
because this file uses: process.env.API_BASE_URL, which is only available on the server side.
*/
import "server-only";
import { ApiError } from "./errors";


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

    let responseData: unknown = undefined;

    if (response.status !== 204) {
        
        const contentType = response.headers.get("content-type");

        if (contentType?.includes("application/json")){
            responseData = await response.json();
        }else{
            responseData = await response.text();
        }
    }

    if (!response.ok) {

        let message = 
        `Backend request failed with status ${response.status}`;

        if(
            typeof responseData === "object" &&
            responseData !== null &&
            "message" in responseData &&
            typeof responseData.message === "string"
        ){
            message = responseData.message;
        }

        throw new ApiError(
            message,
            response.status,
            responseData
        );
    }

    if (response.status === 204) {
        return undefined as T;
    }

    return responseData as T;
}
