import { markets } from "../data/markets";

export function MarketTicker() {
  return (
    <section
      id="markets"
      className="border-y border-white/[0.08] bg-white/[0.015]"
    >
      <div className="mx-auto grid max-w-7xl grid-cols-2 md:grid-cols-4">
        {markets.map((market) => (
          <div
            key={market.symbol}
            className="border-r border-white/[0.08] p-6 last:border-r-0"
          >
            <div className="flex items-start justify-between gap-3">
              <div>
                <p className="font-semibold text-white">{market.symbol}</p>
                <p className="mt-1 text-xs text-zinc-500">{market.name}</p>
              </div>

              <span
                className={
                  market.positive
                    ? "text-sm text-emerald-400"
                    : "text-sm text-red-400"
                }
              >
                {market.change}
              </span>
            </div>

            <p className="mt-4 text-lg font-semibold text-white">
              {market.price}
            </p>
          </div>
        ))}
      </div>
    </section>
  );
}
