"use client";

import { useRouter } from "next/navigation";
import React, { useState } from "react";
import { resetPassword } from "../api";
import { ApiError } from "@/lib/api/errors";

interface ResetPasswordFormProps{
    token: string;
}

export function ResetPasswordForm({
    token,
}: ResetPasswordFormProps){
    const router = useRouter();

    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [error, setError] = useState<string | null>(null);
    const [isSubmitting, setIsSubmitting] = useState(false);

    async function handleSubmit(
        event: React.FormEvent<HTMLFormElement>,
    ){
        event.preventDefault();
        setError(null);

        if(newPassword !== confirmPassword){
            setError("Passwords do not match.");
            return;
        }

        setIsSubmitting(true);

        try{
            await resetPassword({
                token,
                newPassword,
            });

            router.replace("/login?reset=success");
        }catch(error){
            setError(
                error instanceof ApiError || error instanceof Error
                ? error.message
                : "Unable to reset password.",
            );
        }finally{
            setIsSubmitting(false);
        }
    }

    return (
  <form onSubmit={handleSubmit} className="space-y-6">
    <div>
      <label
        htmlFor="password"
        className="mb-2 block text-sm font-medium text-zinc-200"
      >
        New password
      </label>

      <input
        type="password"
        name="password"
        id="password"
        autoComplete="new-password"
        placeholder="Enter your new password"
        value={newPassword}
        onChange={(e) => setNewPassword(e.target.value)}
        minLength={8}
        required
        className="w-full rounded-xl border border-white/[0.1] bg-white/[0.04] px-4 py-3 text-sm text-white outline-none placeholder:text-zinc-600 transition focus:border-emerald-400/50 focus:bg-white/[0.06] focus:ring-2 focus:ring-emerald-400/10"
      />
    </div>

    <div>
      <label
        htmlFor="confirmPassword"
        className="mb-2 block text-sm font-medium text-zinc-200"
      >
        Confirm password
      </label>

      <input
        type="password"
        name="confirmPassword"
        id="confirmPassword"
        autoComplete="new-password"
        placeholder="Confirm your new password"
        value={confirmPassword}
        onChange={(e) => setConfirmPassword(e.target.value)}
        minLength={8}
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
      {isSubmitting ? "Resetting..." : "Reset password"}
    </button>
  </form>
);
}
