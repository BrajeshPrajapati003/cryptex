import Link from "next/link";
import type { ReactNode } from "react";

interface AuthCardProps {
  children: ReactNode;
  title: string;
  description: string;
  footer?: ReactNode;
}

export function AuthCard({
  children,
  title,
  description,
  footer,
}: AuthCardProps) {
  return (
    <main className="relative flex min-h-screen items-center justify-center overflow-hidden bg-[#07090d] px-4 py-12 text-white">
      {/* Background glow */}
      <div className="pointer-events-none absolute left-1/2 top-1/2 -z-0 h-[500px] w-[500px] -translate-x-1/2 -translate-y-1/2 rounded-full bg-emerald-500/[0.06] blur-[140px]" />

      <div className="relative z-10 w-full max-w-md">
        {/* Logo */}
        <div className="mb-8 text-center">
          <Link
            href="/"
            className="text-2xl font-bold tracking-[0.25em] text-white"
          >
            CRYPTEX<span className="text-emerald-400">.</span>
          </Link>
        </div>

        {/* Card */}
        <div className="rounded-2xl border border-white/[0.08] bg-[#0c1016]/95 p-7 shadow-2xl shadow-black/20 backdrop-blur-xl sm:p-9">
          <div className="mb-8">
            <h1 className="text-2xl font-semibold tracking-tight">
              {title}
            </h1>

            <p className="mt-2 text-sm leading-6 text-zinc-500">
              {description}
            </p>
          </div>

          {children}
        </div>

        {/* Footer */}
        {footer && (
          <div className="mt-6 text-center text-sm text-zinc-500">
            {footer}
          </div>
        )}

        <p className="mt-8 text-center text-xs text-zinc-700">
          © {new Date().getFullYear()} CRYPTEX
        </p>
      </div>
    </main>
  );
}