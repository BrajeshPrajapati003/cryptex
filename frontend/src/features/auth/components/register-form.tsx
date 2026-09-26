"use client"

import { useRouter } from "next/navigation";
import React, { useState } from "react";
import { register } from "../api";
import { ApiError } from "@/lib/api/errors";
import Link from "next/link";

export function RegisterForm(){
    const router = useRouter();

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [message, setMessage] = useState<string | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [isSubmitting, setIsSubmitting] = useState(false);

    async function handleSubmit(
        event: React.FormEvent<HTMLFormElement>,
    ){
        event.preventDefault();

        setMessage(null);
        setError(null);
        setIsSubmitting(true);

        try {
      const response = await register({
        firstName,
        lastName,
        email,
        password,
        });

            setMessage(
                response.message ??
                "Registration successful. Please verify your email.",
            );
            router.push(`/verify?email=${encodeURIComponent(email)}`);

        }catch(error){
            setError(error instanceof ApiError || error instanceof Error
                ? error.message
                : "Unable to register. Please try again.",
            );
        }finally{
            setIsSubmitting(false);
        }
    }
    
    return (
    <section>
    <form onSubmit={handleSubmit} className="space-y-5">
      <div className="grid grid-cols-2 gap-4">
        <div>
          <label
            htmlFor="firstName"
            className="mb-2 block text-sm font-medium text-zinc-200"
          >
            First name
          </label>

          <input
            type="text"
            id="firstName"
            placeholder="Brajesh"
            value={firstName}
            onChange={(event) => setFirstName(event.target.value)}
            required
            className="w-full rounded-xl border border-white/[0.1] bg-white/[0.04] px-4 py-3 text-sm text-white outline-none placeholder:text-zinc-600 transition focus:border-emerald-400/50 focus:bg-white/[0.06] focus:ring-2 focus:ring-emerald-400/10"
          />
        </div>

        <div>
          <label
            htmlFor="lastName"
            className="mb-2 block text-sm font-medium text-zinc-200"
          >
            Last name
          </label>

          <input
            type="text"
            id="lastName"
            placeholder="Prajapati"
            value={lastName}
            onChange={(event) => setLastName(event.target.value)}
            required
            className="w-full rounded-xl border border-white/[0.1] bg-white/[0.04] px-4 py-3 text-sm text-white outline-none placeholder:text-zinc-600 transition focus:border-emerald-400/50 focus:bg-white/[0.06] focus:ring-2 focus:ring-emerald-400/10"
          />
        </div>
      </div>

      <div>
        <label
          htmlFor="email"
          className="mb-2 block text-sm font-medium text-zinc-200"
        >
          Email address
        </label>

        <input
          type="email"
          id="email"
          placeholder="you@example.com"
          value={email}
          onChange={(event) => setEmail(event.target.value)}
          required
          className="w-full rounded-xl border border-white/[0.1] bg-white/[0.04] px-4 py-3 text-sm text-white outline-none placeholder:text-zinc-600 transition focus:border-emerald-400/50 focus:bg-white/[0.06] focus:ring-2 focus:ring-emerald-400/10"
        />
      </div>

      <div>
        <label
          htmlFor="password"
          className="mb-2 block text-sm font-medium text-zinc-200"
        >
          Password
        </label>

        <input
          type="password"
          id="password"
          placeholder="Create a password"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
          required
          className="w-full rounded-xl border border-white/[0.1] bg-white/[0.04] px-4 py-3 text-sm text-white outline-none placeholder:text-zinc-600 transition focus:border-emerald-400/50 focus:bg-white/[0.06] focus:ring-2 focus:ring-emerald-400/10"
        />

        <p className="mt-2 text-xs text-zinc-600">
          Use at least 8 characters.
        </p>
      </div>

      {error && (
        <div
          role="alert"
          className="rounded-xl border border-red-400/20 bg-red-400/10 px-4 py-3 text-sm text-red-300"
        >
          {error}
        </div>
      )}

      {message && (
        <div
          role="status"
          className="rounded-xl border border-emerald-400/20 bg-emerald-400/10 px-4 py-3 text-sm text-emerald-300"
        >
          {message}
        </div>
      )}

      <button
        type="submit"
        disabled={isSubmitting}
        className="w-full rounded-xl bg-emerald-400 px-4 py-3 text-sm font-semibold text-black transition hover:bg-emerald-300 disabled:cursor-not-allowed disabled:opacity-50"
      >
        {isSubmitting ? "Creating account..." : "Create account"}
      </button>
    </form>

    <p className="mt-6 text-center text-sm text-zinc-500">
      Already have an account?{" "}
      <Link
        href="/login"
        className="font-medium text-emerald-400 hover:text-emerald-300"
      >
        Sign in
      </Link>
    </p>
  </section>
);
}
