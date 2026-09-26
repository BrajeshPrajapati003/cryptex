import Link from "next/link";
import { RegisterForm } from "@/features/auth/components/register-form";
import { AuthCard } from "@/features/auth/components/auth-card";

export default function RegisterPage() {
  return (
    <AuthCard
      title="Create your account"
      description="Create your CRYPTEX account and start exploring the crypto market."
    >
      <RegisterForm />
    </AuthCard>
  );
}