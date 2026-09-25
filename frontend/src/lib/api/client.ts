import { ApiError } from "./errors";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

if (!API_BASE_URL) {
  throw new Error(
    "NEXT_PUBLIC_API_BASE_URL is not configured",
  );
}

export async function apiClient<T>(
  endpoint: string,
  options: RequestInit = {},
): Promise<T> {
  const response = await fetch(
    `${API_BASE_URL}${endpoint}`,
    {
      ...options,

      headers: {
        "Content-Type": "application/json",
        ...options.headers,
      },
    },
  );

  /*
   * Try to read the response body.
   *
   * Some endpoints return JSON.
   * Logout returns HTTP 204 with no body.
   */
  let responseData: unknown = undefined;

  if (response.status !== 204) {
    const contentType = response.headers.get("content-type");

    if (contentType?.includes("application/json")) {

        // if response says it's JSON, parse it as JSON
      responseData = await response.json();
    } else {
      responseData = await response.text();
    }
  }

  /*
   * HTTP status outside the 2xx range.
   */
  if (!response.ok) {
    const message =
      typeof responseData === "object" &&
      responseData !== null &&
      "message" in responseData &&
      typeof responseData.message === "string"
        ? responseData.message
        : `API request failed with status ${response.status}`;

    throw new ApiError(
      message,
      response.status,
      responseData,
    );
  }

  /*
   * HTTP 204 = No Content.
   */
  if (response.status === 204) {
    return undefined as T;
  }

  return responseData as T;
}
