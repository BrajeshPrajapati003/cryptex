"use client";

import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { verifyEmail } from "../api";
import { ApiError } from "@/lib/api/errors";

interface VerifyEmailProps {
    token: string;
}

export function VerifyEmail({
    token,
}: VerifyEmailProps){
    const router = useRouter();

    const [message, setMessage] = useState<string | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [isVerifying, setIsVerifying] = useState(true);

    useEffect(()=> {
        async function verify(){
            setError(null);
            setIsVerifying(true);

            try{
                const response = await verifyEmail(token);

                setMessage(
                    response.message ??
                    "Email verified successfully.",
                );

            }catch(error){
                setError(
                    error instanceof ApiError || error instanceof Error
                    ? error.message
                    : "Unable to verify email.",
                );
            }finally{
                setIsVerifying(false);
            }
        }

        verify();
    }, [token]);

    return (
  <section className="text-center">
    {isVerifying && (
      <div role="status" className="py-4">
        <div className="mx-auto mb-5 h-10 w-10 animate-spin rounded-full border-2 border-zinc-700 border-t-emerald-400" />

        <p className="text-sm text-zinc-400">
          Verifying your email...
        </p>
      </div>
    )}

    {message && (
      <div className="space-y-6">
        <div className="mx-auto flex h-16 w-16 items-center justify-center rounded-full border border-emerald-400/20 bg-emerald-400/10">
          <svg
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="2"
            className="h-7 w-7 text-emerald-400"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="m5 12 4 4L19 6"
            />
          </svg>
        </div>

        <div>
          <h2 className="text-xl font-semibold text-white">
            Email verified
          </h2>

          <p
            role="status"
            className="mt-2 text-sm leading-6 text-zinc-400"
          >
            {message}
          </p>
        </div>

        <button
          type="button"
          onClick={() => router.replace("/login")}
          className="w-full rounded-xl bg-emerald-400 px-4 py-3 text-sm font-semibold text-black transition hover:bg-emerald-300"
        >
          Continue to login
        </button>
      </div>
    )}

    {error && (
      <div className="space-y-6">
        <div className="mx-auto flex h-16 w-16 items-center justify-center rounded-full border border-red-400/20 bg-red-400/10">
          <span className="text-2xl text-red-400">!</span>
        </div>

        <div>
          <h2 className="text-xl font-semibold text-white">
            Verification failed
          </h2>

          <p
            role="alert"
            className="mt-2 text-sm leading-6 text-red-300"
          >
            {error}
          </p>
        </div>

        <button
          type="button"
          onClick={() => router.replace("/login")}
          className="w-full rounded-xl border border-white/[0.1] bg-white/[0.04] px-4 py-3 text-sm font-semibold text-white transition hover:bg-white/[0.08]"
        >
          Back to login
        </button>
      </div>
    )}
  </section>
);
}
