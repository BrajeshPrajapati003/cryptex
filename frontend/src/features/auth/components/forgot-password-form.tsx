"use client";

import React, { useState } from "react";
import { forgotPassword } from "../api";
import { ApiError } from "@/lib/api/errors";

export function ForgotPasswordForm() {
  const [email, setEmail] = useState("");
  const [message, setMessage] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    setMessage(null);
    setError(null);
    setIsSubmitting(true);

    try {
      await forgotPassword({ email });

      setMessage(
        "If an account exists with this email, a password reset link has been sent.",
      );
    } catch (error) {
      setError(
        error instanceof ApiError || error instanceof Error
          ? error.message
          : "unable to process your request.",
      );
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
  <form onSubmit={handleSubmit} className="space-y-6">
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
        autoComplete="email"
        placeholder="you@example.com"
        value={email}
        onChange={(event) => setEmail(event.target.value)}
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
      {isSubmitting ? "Sending..." : "Send reset link"}
    </button>
  </form>
);
}
