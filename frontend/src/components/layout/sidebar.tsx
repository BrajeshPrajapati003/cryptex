"use client";

import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import { useState } from "react";

const mainNavigation = [
  {
    name: "Dashboard",
    href: "/dashboard",
    icon: "▦",
  },
  {
    name: "Markets",
    href: "/markets",
    icon: "◈",
  },
  {
    name: "Trade",
    href: "/trade",
    icon: "↗",
  },
  {
    name: "Portfolio",
    href: "/portfolio",
    icon: "◒",
  },
  {
    name: "Orders",
    href: "/orders",
    icon: "≡",
  },
  {
    name: "News",
    href: "/news",
    icon: "▤"
  }
];

const accountNavigation = [
  {
    name: "Profile",
    href: "/profile",
    icon: "○",
  },
  {
    name: "Settings",
    href: "/settings",
    icon: "⚙",
  },
];

export function Sidebar() {
  const pathname = usePathname();
  const router = useRouter();

  const [isLoggingOut, setIsLoggingOut] = useState(false);

  function isActive(href: string) {
    return pathname === href || pathname.startsWith(`${href}/`);
  }

  async function handleLogout() {
    setIsLoggingOut(true);

    try {
      await fetch("/api/v1/auth/logout", {
        method: "POST",
      });

      router.replace("/login");
      router.refresh();
    } finally {
      setIsLoggingOut(false);
    }
  }

  return (
    <aside className="fixed inset-y-0 left-0 z-50 flex w-64 flex-col border-r border-white/[0.08] bg-[#090b0f] text-white">
      {/* Logo */}
      <div className="flex h-20 items-center border-b border-white/[0.08] px-6">
        <Link
          href="/dashboard"
          className="text-xl font-bold tracking-[0.2em]"
        >
          CRYPTEX<span className="text-emerald-400">.</span>
        </Link>
      </div>

      {/* Navigation */}
      <nav className="flex-1 px-3 py-6">
        <p className="mb-3 px-3 text-[10px] font-semibold uppercase tracking-[0.15em] text-zinc-600">
          Platform
        </p>

        <div className="space-y-1">
          {mainNavigation.map((item) => {
            const active = isActive(item.href);

            return (
              <Link
                key={item.href}
                href={item.href}
                className={`flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm transition ${
                  active
                    ? "bg-emerald-400/10 text-emerald-400"
                    : "text-zinc-400 hover:bg-white/[0.04] hover:text-white"
                }`}
              >
                <span className="flex w-5 justify-center text-base">
                  {item.icon}
                </span>

                <span>{item.name}</span>
              </Link>
            );
          })}
        </div>

        <p className="mb-3 mt-8 px-3 text-[10px] font-semibold uppercase tracking-[0.15em] text-zinc-600">
          Account
        </p>

        <div className="space-y-1">
          {accountNavigation.map((item) => {
            const active = isActive(item.href);

            return (
              <Link
                key={item.href}
                href={item.href}
                className={`flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm transition ${
                  active
                    ? "bg-emerald-400/10 text-emerald-400"
                    : "text-zinc-400 hover:bg-white/[0.04] hover:text-white"
                }`}
              >
                <span className="flex w-5 justify-center text-base">
                  {item.icon}
                </span>

                <span>{item.name}</span>
              </Link>
            );
          })}
        </div>
      </nav>

      {/* Logout */}
      <div className="border-t border-white/[0.08] p-3">
        <button
          type="button"
          onClick={handleLogout}
          disabled={isLoggingOut}
          className="flex w-full items-center gap-3 rounded-lg px-3 py-2.5 text-sm text-zinc-500 transition hover:bg-red-400/10 hover:text-red-400 disabled:cursor-not-allowed disabled:opacity-50"
        >
          <span className="w-5 text-center">↪</span>

          {isLoggingOut ? "Logging out..." : "Log out"}
        </button>
      </div>
    </aside>
  );
}