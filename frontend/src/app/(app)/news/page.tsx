export default function NewsPage() {
  return (
    <div className="flex min-h-[calc(100vh-5rem)] items-center justify-center px-6">
      <div className="max-w-md text-center">
        <div className="mx-auto mb-6 flex h-16 w-16 items-center justify-center rounded-2xl border border-emerald-400/20 bg-emerald-400/10">
          <span className="text-2xl">📰</span>
        </div>

        <p className="mb-2 text-sm font-medium uppercase tracking-widest text-emerald-400">
          Coming Soon
        </p>

        <h1 className="text-3xl font-semibold text-white">
          Crypto News
        </h1>

        <p className="mt-4 text-sm leading-6 text-zinc-500">
          Stay updated with global markets, crypto news, regulations,
          and important events. This section is currently under development.
        </p>

        <div className="mt-8 inline-flex items-center gap-2 rounded-full border border-white/[0.08] bg-white/[0.03] px-4 py-2 text-xs text-zinc-500">
          <span className="h-1.5 w-1.5 rounded-full bg-emerald-400" />
          News service coming soon
        </div>
      </div>
    </div>
  );
}