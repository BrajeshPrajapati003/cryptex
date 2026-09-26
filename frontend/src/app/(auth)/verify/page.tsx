import Link from "next/link";
import { VerifyEmail } from "@/features/auth/components/verify";
import { AuthCard } from "@/features/auth/components/auth-card";

interface VerifyEmailPageProps {
  searchParams: Promise<{
    token?: string;
  }>;
}

export default async function VerifyEmailPage({
  searchParams,
}: VerifyEmailPageProps) {
  const { token } = await searchParams;

  if (!token) {
    return (
      <AuthCard
        title="Invalid verification link"
        description="We couldn't verify your email because the verification token is missing."
        footer={
          <Link
            href="/login"
            className="font-medium text-emerald-400 transition hover:text-emerald-300"
          >
            Back to login
          </Link>
        }
      >
        <div className="rounded-xl border border-red-400/20 bg-red-400/10 p-4">
          <div className="flex items-start gap-3">
            <div className="flex h-7 w-7 shrink-0 items-center justify-center rounded-full bg-red-400/10 text-sm font-semibold text-red-400">
              !
            </div>

            <div>
              <p className="text-sm font-medium text-red-300">
                Verification token missing
              </p>

              <p className="mt-1 text-xs leading-5 text-red-300/70">
                The verification link is incomplete or invalid. Please use the
                latest verification email sent to you.
              </p>
            </div>
          </div>
        </div>
      </AuthCard>
    );
  }

  return (
    <AuthCard
      title="Verify your email"
      description="We're verifying your email address. This should only take a moment."
    >
      <VerifyEmail token={token} />
    </AuthCard>
  );
}