import Link from "next/link";

export function PublicNavbar() {
  return (
    <header className="border-b border-white/[0.08] bg-[#07090d]">
      <nav className="mx-auto flex h-20 max-w-7xl items-center justify-between px-6">
        <Link
          href="/"
          className="text-xl font-bold tracking-[0.2em] text-white"
        >
          CRYPTEX<span className="text-emerald-400">.</span>
        </Link>

        <div className="hidden items-center gap-8 md:flex">
          <Link
            href="#markets"
            className="text-sm text-zinc-400 hover:text-white"
          >
            Markets
          </Link>

          <Link
            href="#features"
            className="text-sm text-zinc-400 hover:text-white"
          >
            Features
          </Link>

          <Link
            href="#security"
            className="text-sm text-zinc-400 hover:text-white"
          >
            Security
          </Link>
        </div>

        <div className="flex items-center gap-3">
          <Link
            href="/login"
            className="px-4 py-2 text-sm text-zinc-300 hover:text-white"
          >
            Log in
          </Link>

          <Link
            href="/register"
            className="rounded-lg bg-emerald-400 px-5 py-2.5 text-sm font-semibold text-black hover:bg-emerald-300"
          >
            Get Started
          </Link>
        </div>
      </nav>
    </header>
  );
}
