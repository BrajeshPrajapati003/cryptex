import Link from "next/link";
import { LoginForm } from "@/features/auth/components/login-form";
import { AuthCard } from "@/features/auth/components/auth-card";

interface LoginPageProps {
  searchParams: Promise<{
    reset?: string;
  }>;
}

export default async function LoginPage({
  searchParams,
}: LoginPageProps) {
  const { reset } = await searchParams;

  return (
    <AuthCard
      title="Welcome back"
      description="Sign in to continue to your CRYPTEX account."
    >
      {reset === "success" && (
        <div
          role="status"
          className="mb-6 rounded-xl border border-emerald-400/20 bg-emerald-400/10 px-4 py-3 text-sm text-emerald-300"
        >
          Password reset successfully. Please sign in.
        </div>
      )}

      <LoginForm />
    </AuthCard>
  );
}