/**
 * NOTE: Map<String, Object> = Record<string, unknown> 
 * 
 * Keys are strings, and values can be of any type (unknown).
 * we could technically use Record<string, any>
 * 
 * any: Stop checking this.
 * unknown: We don't know what this is, 
 *  so we have to check it before using it.
 */




/**
 * There is no UUID in js/ts; UUID = a string
 */




ACCESS & REFRESH TOKEN IN LOCALSTORAGE:
For a production application, putting long-lived authentication credentials into localStorage creates unnecessary exposure to token theft if malicious JavaScript executes in the browser.




WHY WE ADD ApiResponse<T>?

new ApiResponse<>(
    true,
    "Login successful",
    response
)

So the actual JSON from login is presumably something like:

{
  "success": true,
  "message": "Login successful",
  "data": {
    "accessToken": "...",
    "refreshToken": "...",
    "tokenType": "Bearer"
  }
}

Therefore this: LoginResponse

describes only:

{
  "accessToken": "...",
  "refreshToken": "...",
  "tokenType": "Bearer"
}

It does not describe the entire HTTP response.

That's why we need: ApiResponse<LoginResponse>

which means:
An API response whose data property contains a LoginResponse.




  /**
   * logout returns @ResponseStatus(HttpStatus.NO_CONTENT) 
   * which means HTTP 204; there is NO JSON BODY.
   * if blindly called response.json() on a 204 response, it will throw SyntaxError: Unexpected end of JSON input
   */




We have:

let responseData: unknown = undefined;

Why not:

let responseData: any;

Because unknown forces us to check what we're dealing with.

For example, this isn't allowed:

responseData.message

because TypeScript says:

"Bro, you told me you don't know what this is. Prove it first." 😄

So we do:

typeof responseData === "object"

and:

responseData !== null

and:

"message" in responseData

and finally:

typeof responseData.message === "string"

Only after all those checks do we safely use:

responseData.message

That's type narrowing.





Next.js explicitly supports setting cookies from Route Handlers, and httpOnly prevents client-side JavaScript from accessing the cookie.



User
 │
 │ email + password
 ▼
Next.js /api/auth/login
 │
 │ server-to-server request
 ▼
Spring Auth Service
 │
 │ LoginResponse
 │
 ├── accessToken
 └── refreshToken
 │
 ▼
Next.js
 │
 ├── HttpOnly access_token cookie
 └── HttpOnly refresh_token cookie
 │
 ▼
Browser

The browser **never needs to see the raw tokens**.





**What happens when the access token expires?**

Browser
   │
   ▼
Next.js
   │
   ▼
Spring API
   │
   │ 401 Unauthorized
   ▼
Next.js
   │
   │ refresh_token
   ▼
Spring /api/v1/auth/refresh
   │
   │ new access token
   │ new refresh token
   ▼
Next.js
   │
   ├── update access_token
   └── update refresh_token
   │
   ▼
retry original request
   │
   ▼
Browser gets response

From the user's perspective:

Nothing happened.

They stay logged in.




**But there's another problem**

Imagine the access token expires and five requests happen simultaneously:

Request A ────────┐
Request B ────────┤
Request C ────────┼──> 401
Request D ────────┤
Request E ────────┘

We don't want:

refresh
refresh
refresh
refresh
refresh

That creates a refresh-token race condition.

Eventually we'll implement a **single-flight refresh strategy** so only one refresh operation happens and the others wait for it.

That's production thinking.




**Why secure depends on production**

We have:

secure: process.env.NODE_ENV === "production"

During development: http://localhost:3000
doesn't use HTTPS.

If we forced: secure: true
the browser wouldn't send the cookie over plain HTTP.

In production: https://cryptex.com
we want: secure: true
so the cookie is only transmitted over HTTPS.

Next.js documents secure specifically for ensuring cookies are sent only over HTTPS.



**Why SameSite: "lax"?**

We use:

sameSite: "lax"
as a sensible default.

It helps reduce cross-site cookie sending while still allowing normal top-level navigation.

Later, depending on how Cryptex is deployed—especially if frontend and API are on different sites—we may need to revisit:

SameSite
Secure
CSRF protection
CORS

These four need to be considered together.





**import "server-only";**

It tells Next.js:
This module must never be imported into a Client Component.

Why?
Because this file uses: process.env.API_BASE_URL
which we intentionally want to keep server-side.

Our .env.local will now eventually contain: 
API_BASE_URL=http://localhost:8080

instead of:
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080

for backend communication.

Difference

NEXT_PUBLIC_...
means: This variable may be exposed to browser-side code.

Whereas:

API_BASE_URL is server-only.




Next.js Route Handlers are defined using route.ts files inside the App Router and support HTTP methods such as GET, POST, PATCH and DELETE.





