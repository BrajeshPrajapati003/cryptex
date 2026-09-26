import Link from "next/link";

export function Hero() {
  return (
    <section className="relative overflow-hidden">
      <div className="pointer-events-none absolute left-1/2 top-0 -z-10 h-[500px] w-[700px] -translate-x-1/2 rounded-full bg-emerald-500/[0.07] blur-[140px]" />

      <div className="mx-auto max-w-5xl px-6 pb-24 pt-28 text-center">
        <div className="mx-auto mb-7 flex w-fit items-center gap-2 rounded-full border border-white/[0.08] bg-white/[0.03] px-4 py-2 text-xs text-zinc-400">
          <span className="h-1.5 w-1.5 rounded-full bg-emerald-400" />
          Real-time crypto markets
        </div>

        <h1 className="text-5xl font-bold tracking-tight text-white sm:text-6xl lg:text-7xl">
          Trade crypto.
          <br />
          <span className="text-emerald-400">In real time.</span>
        </h1>

        <p className="mx-auto mt-7 max-w-2xl text-base leading-7 text-zinc-400 sm:text-lg">
          Track live markets, manage your digital assets, and trade crypto
          through one powerful platform.
        </p>

        <div className="mt-9 flex flex-col justify-center gap-3 sm:flex-row">
          <Link
            href="/register"
            className="rounded-lg bg-emerald-400 px-7 py-3.5 text-sm font-semibold text-black transition hover:bg-emerald-300"
          >
            Start Trading
          </Link>

          <Link
            href="#markets"
            className="rounded-lg border border-white/[0.1] bg-white/[0.03] px-7 py-3.5 text-sm font-semibold text-white transition hover:bg-white/[0.06]"
          >
            Explore Markets
          </Link>
        </div>
      </div>
    </section>
  );
}
