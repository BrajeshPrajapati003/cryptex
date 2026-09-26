"use client";

import { useRouter } from "next/navigation";
import { useState } from "react";
import { login } from "../api";
import { ApiError } from "@/lib/api/errors";
import Link from "next/link";

export function LoginForm() {
  const router = useRouter();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [error, setError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  async function handleSubmit(
    event: React.FormEvent<HTMLFormElement>
) {
    event.preventDefault();

    setError(null);
    setIsSubmitting(true);

    try {
      await login({
        email,
        password,
      });

      /**
       * Login Route handler has already stored
       * the access & refresh tokens as HttpOnly cookies.
       *
       * The browser JS never receives either token.
       */

      router.replace("/dashboard");
      router.refresh();
    } catch (error) {
      if (error instanceof ApiError || error instanceof Error) {
        setError(error.message);
      } else {
        setError("Unable to login. Please try again.");
      }
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
  <section>
    <form onSubmit={handleSubmit} className="space-y-5">
      <div>
        <label
          htmlFor="email"
          className="mb-2 block text-sm font-medium text-zinc-200"
        >
          Email address
        </label>

        <input
          id="email"
          name="email"
          type="email"
          autoComplete="email"
          placeholder="you@example.com"
          value={email}
          onChange={(event) => setEmail(event.target.value)}
          required
          className="w-full rounded-xl border border-white/[0.1] bg-white/[0.04] px-4 py-3 text-sm text-white outline-none placeholder:text-zinc-600 transition focus:border-emerald-400/50 focus:bg-white/[0.06] focus:ring-2 focus:ring-emerald-400/10"
        />
      </div>

      <div>
        <div className="mb-2 flex items-center justify-between">
          <label
            htmlFor="password"
            className="text-sm font-medium text-zinc-200"
          >
            Password
          </label>

          <Link
            href="/forgot-password"
            className="text-xs text-zinc-500 transition hover:text-emerald-400"
          >
            Forgot password?
          </Link>
        </div>

        <input
          type="password"
          name="password"
          id="password"
          autoComplete="current-password"
          placeholder="Enter your password"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
          required
          className="w-full rounded-xl border border-white/[0.1] bg-white/[0.04] px-4 py-3 text-sm text-white outline-none placeholder:text-zinc-600 transition focus:border-emerald-400/50 focus:bg-white/[0.06] focus:ring-2 focus:ring-emerald-400/10"
        />
      </div>

      {error && (
        <div
          role="alert"
          className="rounded-xl border border-red-400/20 bg-red-400/10 px-4 py-3 text-sm text-red-300"
        >
          {error}
        </div>
      )}

      <button
        type="submit"
        disabled={isSubmitting}
        className="w-full rounded-xl bg-emerald-400 px-4 py-3 text-sm font-semibold text-black transition hover:bg-emerald-300 disabled:cursor-not-allowed disabled:opacity-50"
      >
        {isSubmitting ? "Signing in..." : "Sign in"}
      </button>
    </form>

    <p className="mt-6 text-center text-sm text-zinc-500">
      Don't have an account?{" "}
      <Link
        href="/register"
        className="font-medium text-emerald-400 hover:text-emerald-300"
      >
        Create one
      </Link>
    </p>
  </section>
);
}
