import { redirect } from "next/navigation";
import { getCurrentUser } from "@/lib/auth/session";

const assets = [
  {
    symbol: "BTC",
    name: "Bitcoin",
    price: "$67,421.32",
    change: "+2.41%",
    positive: true,
  },
  {
    symbol: "ETH",
    name: "Ethereum",
    price: "$3,842.19",
    change: "+1.82%",
    positive: true,
  },
  {
    symbol: "SOL",
    name: "Solana",
    price: "$182.44",
    change: "-0.73%",
    positive: false,
  },
];

export default async function DashboardPage() {
  const user = await getCurrentUser();

  if (!user) {
    redirect("/login");
  }

  return (
    <div className="mx-auto max-w-[1600px] p-6 lg:p-8">
      {/* Header */}
      <div className="flex flex-col justify-between gap-4 sm:flex-row sm:items-end">
        <div>
          <p className="text-sm text-zinc-500">Overview</p>

          <h1 className="mt-1 text-3xl font-semibold tracking-tight">
            Welcome back, {user.firstName}
          </h1>

          <p className="mt-2 text-sm text-zinc-500">
            Here's what's happening across your account.
          </p>
        </div>

        <div className="flex items-center gap-2 rounded-lg border border-white/[0.08] bg-white/[0.02] px-3 py-2">
          <span className="h-2 w-2 rounded-full bg-emerald-400" />

          <span className="text-xs text-zinc-400">
            Markets live
          </span>
        </div>
      </div>

      {/* Portfolio overview */}
      <section className="mt-8">
        <div className="rounded-2xl border border-white/[0.08] bg-[#0c1016] p-6">
          <div className="flex flex-col justify-between gap-6 sm:flex-row">
            <div>
              <p className="text-sm text-zinc-500">
                Total portfolio value
              </p>

              <div className="mt-3 flex items-end gap-3">
                <h2 className="text-4xl font-semibold tracking-tight">
                  $12,482.42
                </h2>

                <span className="mb-1 rounded-md bg-emerald-400/10 px-2 py-1 text-xs font-medium text-emerald-400">
                  +4.02%
                </span>
              </div>

              <p className="mt-2 text-sm text-emerald-400">
                +$482.21 today
              </p>
            </div>

            <div className="flex gap-2">
              <button className="rounded-lg bg-emerald-400 px-4 py-2.5 text-sm font-semibold text-black transition hover:bg-emerald-300">
                Trade
              </button>

              <button className="rounded-lg border border-white/[0.08] px-4 py-2.5 text-sm text-zinc-300 transition hover:bg-white/[0.04] hover:text-white">
                Deposit
              </button>
            </div>
          </div>

          {/* Temporary chart area */}
          <div className="mt-8 h-56 rounded-xl border border-white/[0.05] bg-[#080b10]">
            <div className="flex h-full items-center justify-center">
              <p className="text-sm text-zinc-700">
                Portfolio performance
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* Assets */}
      <section className="mt-8">
        <div className="mb-4 flex items-center justify-between">
          <div>
            <h2 className="text-lg font-semibold">Market snapshot</h2>
            <p className="mt-1 text-xs text-zinc-600">
              Live assets will appear here.
            </p>
          </div>

          <a
            href="/markets"
            className="text-xs text-emerald-400 hover:text-emerald-300"
          >
            View markets →
          </a>
        </div>

        <div className="grid gap-4 md:grid-cols-3">
          {assets.map((asset) => (
            <div
              key={asset.symbol}
              className="rounded-xl border border-white/[0.08] bg-[#0c1016] p-5 transition hover:border-white/[0.15]"
            >
              <div className="flex items-center justify-between">
                <div>
                  <p className="font-semibold">{asset.symbol}</p>
                  <p className="mt-1 text-xs text-zinc-600">
                    {asset.name}
                  </p>
                </div>

                <span
                  className={
                    asset.positive
                      ? "text-xs text-emerald-400"
                      : "text-xs text-red-400"
                  }
                >
                  {asset.change}
                </span>
              </div>

              <p className="mt-6 text-xl font-semibold">
                {asset.price}
              </p>
            </div>
          ))}
        </div>
      </section>

      {/* Account information */}
      <section className="mt-8 grid gap-4 md:grid-cols-3">
        <InfoCard
          label="Account"
          value="Active"
        />

        <InfoCard
          label="Email"
          value={user.email}
        />

        <InfoCard
          label="Role"
          value={user.role}
        />
      </section>
    </div>
  );
}

function InfoCard({
  label,
  value,
}: {
  label: string;
  value: string;
}) {
  return (
    <div className="rounded-xl border border-white/[0.08] bg-[#0c1016] p-5">
      <p className="text-xs uppercase tracking-wider text-zinc-600">
        {label}
      </p>

      <p className="mt-3 truncate text-sm font-medium text-zinc-200">
        {value}
      </p>
    </div>
  );
}