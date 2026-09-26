import { PublicNavbar } from "@/components/layout/public-navbar";
import { Hero } from "@/features/home/components/hero";
import { MarketTicker } from "@/features/home/components/market-ticker";

export default function HomePage() {
  return (
    <main className="min-h-screen bg-[#07090d] text-white">
      <PublicNavbar />

      <Hero />

      <MarketTicker />
    </main>
  );
}
