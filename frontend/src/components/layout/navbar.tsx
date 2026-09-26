import Link from "next/link";

export function Navbar() {
  return (
    <header className="flex h-20 items-center justify-between border-b border-white/[0.08] bg-[#07090d] px-8">
      <div>
        <p className="text-sm font-medium text-white">Trading Terminal</p>

        <p className="mt-0.5 text-xs text-zinc-600">
          Real-time crypto markets
        </p>
      </div>

      <div className="flex items-center gap-4">
        {/* Connection status */}
        <div className="hidden items-center gap-2 rounded-full border border-white/[0.08] px-3 py-1.5 sm:flex">
          <span className="h-1.5 w-1.5 rounded-full bg-emerald-400" />

          <span className="text-xs text-zinc-500">Live</span>
        </div>

        {/* Notifications */}
        <button
          type="button"
          className="relative flex h-9 w-9 items-center justify-center rounded-lg text-zinc-400 transition hover:bg-white/[0.05] hover:text-white"
          aria-label="Notifications"
        >
          <span className="text-lg">♢</span>

          <span className="absolute right-2 top-1.5 h-1.5 w-1.5 rounded-full bg-emerald-400" />
        </button>

        {/* User */}
        <Link
          href="/profile"
          className="flex items-center gap-3 rounded-lg px-2 py-1.5 transition hover:bg-white/[0.04]"
        >
          <div className="flex h-8 w-8 items-center justify-center rounded-full bg-emerald-400/10 text-sm font-semibold text-emerald-400">
            B
          </div>

          <div className="hidden text-left sm:block">
            <p className="text-sm font-medium text-white">Brajesh</p>
            <p className="text-[11px] text-zinc-600">Account</p>
          </div>
        </Link>
      </div>
    </header>
  );
}