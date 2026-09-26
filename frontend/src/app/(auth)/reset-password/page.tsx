import Link from "next/link";
import { ResetPasswordForm } from "@/features/auth/components/reset-password-form";
import { AuthCard } from "@/features/auth/components/auth-card";

interface ResetPasswordPageProps {
  searchParams: Promise<{
    token?: string;
  }>;
}

export default async function ResetPasswordPage({
  searchParams,
}: ResetPasswordPageProps) {
  const { token } = await searchParams;

  if (!token) {
    return (
      <AuthCard
        title="Invalid reset link"
        description="This password reset link is missing the required token."
        footer={
          <Link
            href="/forgot-password"
            className="font-medium text-emerald-400 transition hover:text-emerald-300"
          >
            Request a new reset link
          </Link>
        }
      >
        <div className="rounded-xl border border-red-400/20 bg-red-400/10 p-4">
          <p className="text-sm leading-6 text-red-300">
            The password reset token is missing or invalid. Please request a
            new password reset link.
          </p>
        </div>
      </AuthCard>
    );
  }

  return (
    <AuthCard
      title="Create a new password"
      description="Choose a new password for your CRYPTEX account."
    >
      <ResetPasswordForm token={token} />
    </AuthCard>
  );
}