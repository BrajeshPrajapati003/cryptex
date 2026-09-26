import { ApiError } from "./errors";

export async function apiClient<T>(
  endpoint: string,
  options: RequestInit = {},
): Promise<T> {

  const response = await fetch(
    endpoint,
    {
      ...options,

      credentials: "include",

      headers: {
        "Content-Type": "application/json",
        ...options.headers,
      },
    },
  );

  const contentType = response.headers.get("content-type");

  let data: unknown = undefined;

  if(response.status !== 204){

    if(contentType?.includes("application/json")){
      data = await response.json();
    }else{
      data = await response.text();
    }
  }

  if(!response.ok){

    let message = `Request failed with status ${response.status}`;

    if(
      typeof data === "object" &&
      data !== null &&
      "message" in data &&
      typeof data.message === "string"
    ){
      message = data.message;
    }

    throw new ApiError(
      message,
      response.status,
      data,
    );
  }

  if(response.status === 204){
    return undefined as T;
  }

  return data as T;
}
